package com.techgenii.postman;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Slf4j
public class EmailService {


    public static final String MAIL_TEMPLATES_PREFIX = "classpath:/mail-templates/";
    public static final String MAIL_TEMPLATES_SUFFIX = ".html";
    public static final String TEMPLATE_MODE = "HTML";
    public static final String CHARACTER_ENCODING = "UTF-8";
    private static final String FROM_EMAIL = "techgenii.org@gmail.com";

    @Autowired
    private SpringTemplateEngine templateEngine;

    private AmazonSimpleEmailService sesService;

    public void sendEmail(String to, String subject, String templateName, HashMap<String, Object> templateVariables) {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateVariables);
        String processedHtml = templateEngine.process(templateName, thymeleafContext);

        AmazonSimpleEmailService sesService = AmazonSimpleEmailServiceClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .build();

        SendEmailRequest emailRequest = new SendEmailRequest()
                .withDestination(new Destination()
                        .withToAddresses(to))
                .withMessage(new Message()
                        .withBody(new Body()
                                .withHtml(new Content()
                                        .withCharset("UTF-8").withData(processedHtml)))
                        .withSubject(new Content()
                                .withCharset("UTF-8").withData(subject)))
                .withSource(FROM_EMAIL);

        sesService.sendEmail(emailRequest);

        log.info("!!Mail Sent!!");
    }

    public void sendEmail(String to, String subject, String text){

        SendEmailRequest emailRequest = new SendEmailRequest()
                .withDestination(new Destination()
                        .withToAddresses(to))
                .withMessage(new Message()
                        .withBody(new Body()
                                .withText(new Content()
                                        .withCharset("UTF-8").withData(text)))
                        .withSubject(new Content()
                                .withCharset("UTF-8").withData(subject)))
                .withSource(FROM_EMAIL);

        sesService.sendEmail(emailRequest);

        log.info("!!Mail Sent!!");
    }

    @PostConstruct
    private void init() {
        sesService = AmazonSimpleEmailServiceClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .build();
    }
}