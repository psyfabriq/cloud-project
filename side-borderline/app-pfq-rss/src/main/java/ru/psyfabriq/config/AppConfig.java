package ru.psyfabriq.config;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import ru.psyfabriq.model.Feed;
import ru.psyfabriq.model.FeedItem;
import ru.psyfabriq.model.Subscription;
import ru.psyfabriq.model.SyndFeedWrapper;
import ru.psyfabriq.repository.FeedItemRepository;
import ru.psyfabriq.repository.SubscriptionRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableIntegration
public class AppConfig {

    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Autowired
    private RssFeedMessageSource rssFeedMessageSource;


    @Bean
    @InboundChannelAdapter(value = "feedChannel", poller = @Poller(maxMessagesPerPoll = "1", fixedDelay = "10000"))
    public MessageSource<List<SyndFeedWrapper>> feedAdapter() {
        return rssFeedMessageSource;
    }

    @Bean(name = "feedChannel")
    public PollableChannel feedChannel() {
        return new QueueChannel();
    }


    @MessageEndpoint
    public static class Endpoint {

        @Autowired
        private FeedItemRepository feedItemRepository;

        //@Autowired
        //private FeedRepository feedRepository;

        @Autowired
        private SubscriptionRepository subscriptionRepository;

        @ServiceActivator(inputChannel = "feedChannel", poller = @Poller(maxMessagesPerPoll = "1", fixedDelay = "10000"))
        public void handleFeeds(Message<List<SyndFeedWrapper>> message) {
            List<SyndFeedWrapper> syndFeeds = message.getPayload();

            logger.error("List<SyndFeedWrapper> size " + syndFeeds.size());

            for (SyndFeedWrapper syndFeedWrapper : syndFeeds) {
                SyndFeed syndFeed = syndFeedWrapper.getSyndFeed();
                String feedUrl = syndFeed.getLink();
                if (feedUrl == null) {
                    logger.warn("Feed Link undefined for FEED[title]: {}", syndFeed.getTitle());
                    continue;
                }

                Subscription s = subscriptionRepository.findByRssUrl(syndFeedWrapper.getRssUrs());
                if (s == null) {
                    logger.error("No subscription found for URL: " + syndFeedWrapper.getRssUrs());
                    continue;
                }

                Feed feed = s.getFeed();

                Timestamp syndFeedTimestamp = new Timestamp(syndFeed.getPublishedDate().getTime());
                if (feed != null && new Timestamp(feed.getLastBuildDate().getTime()).equals(syndFeedTimestamp)) {
                    logger.info("Found Feed {} - has actual date and will not be replaced with a new", feedUrl);
                    continue;
                } else {
                    feed = new Feed(feedUrl, syndFeed.getTitle(), syndFeed.getPublishedDate());
                    s.setFeed(feed);
                    subscriptionRepository.saveAndFlush(s);
                }

                List<FeedItem> items = new ArrayList<>();

                logger.info("Rss feed Title: {}, URL: {}\n", syndFeed.getTitle(), syndFeed.getUri());

                for (SyndEntry syndEntry : syndFeed.getEntries()) {
                    logger.info("\trss feed item infomation. author={}, title={}, link={}", syndEntry.getAuthor(),
                            syndEntry.getTitle(), syndEntry.getLink());

                    String guid = syndEntry.getUri();
                    if (StringUtils.isBlank(guid)) {
                        guid = syndEntry.getLink();
                    }
                    if (StringUtils.isBlank(guid)) {
                        continue;
                    }
                    FeedItem feedItem = new FeedItem(s.getId(), guid, syndEntry.getTitle(), syndEntry.getDescription().getValue(), syndEntry.getAuthor(), syndEntry.getPublishedDate());
                    items.add(feedItem);
                }
                feedItemRepository.saveAll(items);
            }
        }

    }

}
