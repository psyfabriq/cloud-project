package ru.podstavkov.utils;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

@Component
public class Messages {
	
    @Autowired
    private MessageSource messageSource;
    
    public String get(String code) {
        try {
            MessageSourceAccessor accessor = new MessageSourceAccessor(messageSource, LocaleContextHolder.getLocale());
            return accessor.getMessage(code);
        } catch (NoSuchMessageException nsme) {
            return code;
        }
    }
}
