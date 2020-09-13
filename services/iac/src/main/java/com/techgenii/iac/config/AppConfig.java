package com.techgenii.iac.config;

import com.techgenii.iac.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
public class AppConfig {

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

}
