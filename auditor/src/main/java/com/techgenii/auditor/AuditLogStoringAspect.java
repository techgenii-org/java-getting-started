package com.techgenii.auditor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.lang.reflect.Parameter;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class AuditLogStoringAspect extends TransactionSynchronizationAdapter {

    @Autowired
    private ApplicationContext ctx;

    @Before("execution(* *.*(..)) && @annotation(audit)")
    public void registerTransactionSynchronization(JoinPoint jp, Audit audit) {
        log.info("Registering Audit log tx callback");
        TransactionSynchronizationManager.registerSynchronization(this);
        final MethodSignature signature = (MethodSignature) jp.getSignature();
        if (signature.getMethod().isAnnotationPresent(Audit.class)) {
            final Audit annotation = signature.getMethod().getAnnotation(Audit.class);
            final Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(annotation);
            annotationAttributes.forEach((key, value) -> {

            });
            log.info("Annotation attributes : {}",annotationAttributes);
            log.info("Auditing Method : {}",signature.getMethod().getName());
        }
        int paramIdx = 0;
        for (Parameter param: signature.getMethod().getParameters()){
            if (param.isAnnotationPresent(AuditLogActor.class)) {
                AuditLogServiceData.setActor((Long)jp.getArgs()[paramIdx]);
            }
            paramIdx++;
        }
    }

    @Override
    public void beforeCommit(boolean readOnly) {
        log.debug("tx callback invoked. Readonly = " + readOnly);
        if (readOnly) {
            return;
        }

        AuditLogServiceData.getHibernateEvents().forEach(event -> {
            log.info("Logging event : {}",event);
        });
    }

    @PostConstruct
    private void init() {
        log.info("Saifullah Khan");
    }
}
