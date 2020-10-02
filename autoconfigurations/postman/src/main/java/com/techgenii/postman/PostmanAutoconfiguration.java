package com.techgenii.postman;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

@Configuration
@Slf4j
public class PostmanAutoconfiguration {

    @Bean
    public SpringResourceTemplateResolver getResourceTemplateResolver(){
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix(EmailService.MAIL_TEMPLATES_PREFIX);
        templateResolver.setSuffix(EmailService.MAIL_TEMPLATES_SUFFIX);
        templateResolver.setTemplateMode(EmailService.TEMPLATE_MODE);
        templateResolver.setCharacterEncoding(EmailService.CHARACTER_ENCODING);
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine springTemplateEngine() {
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.setTemplateResolver(getResourceTemplateResolver());
        return springTemplateEngine;
    }

    @Bean
    public EmailService emailService() {
        return new EmailService();
    }
}
