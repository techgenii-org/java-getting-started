package com.techgenii.postman;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

@Configuration
@Slf4j
public class MessengerAutoconfiguration {

    @Bean
    public SpringResourceTemplateResolver getResourceTemplateResolver(){
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix(Messenger.MAIL_TEMPLATES_PREFIX);
        templateResolver.setSuffix(Messenger.MAIL_TEMPLATES_SUFFIX);
        templateResolver.setTemplateMode(Messenger.TEMPLATE_MODE);
        templateResolver.setCharacterEncoding(Messenger.CHARACTER_ENCODING);
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine springTemplateEngine() {
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.setTemplateResolver(getResourceTemplateResolver());
        return springTemplateEngine;
    }

    @Bean
    public Messenger messenger() {
        return new Messenger();
    }
}
