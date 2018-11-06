package ru.psyfabriq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import ru.psyfabriq.entity.Client;
import ru.psyfabriq.repository.ClientRepository;
import ru.psyfabriq.utils.annotation.LogAfter;
import ru.psyfabriq.utils.annotation.LogBefore;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

@Service
public class CustomClientDetailsService implements ClientDetailsService {
    private final ClientRepository clientRepository;
    private final LoadBalancerClient loadBalancerClient;

    @Autowired
    public CustomClientDetailsService(ClientRepository clientRepository, LoadBalancerClient loadBalancerClient) {
        this.clientRepository = clientRepository;
        this.loadBalancerClient = loadBalancerClient;
    }

    @Override
    @Transactional
    @LogBefore
    @LogAfter
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        Client client = clientRepository.findByClientId(clientId)
                .orElseThrow(() -> new ClientRegistrationException(String.format("no client %s registered", clientId))
                );
        BaseClientDetails details = new BaseClientDetails(
                client.getClientId(),
                null,
                client.getScope(),
                client.getAuthorizedGrandTypes(),
                client.getAuthorities());
        details.setClientSecret(client.getSecret());
        String greetingsClientRedirectUri = Optional
                .ofNullable(this.loadBalancerClient.choose("greetings-client"))
                .map(si -> String.format("http://%s:%s/", si.getHost(), si.getPort()))
                .orElseThrow(() -> new ClientRegistrationException(
                        String.format("coudn`t find and bind a greetings-client IP", clientId)
                ));
        details.setRegisteredRedirectUri(Collections.singleton(greetingsClientRedirectUri));
        return details;
    }
}
