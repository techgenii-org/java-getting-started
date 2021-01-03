package com.techgenii.auditor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Audit {

    ActorType actorType() default ActorType.SYSTEM;

    String actor() default "";

    AuditTargetType auditTargetType() default AuditTargetType.METHOD_RESPONSE;

    String  auditTarget() default "";

    AuditAction auditActionType() default AuditAction.METHOD_NAME;

    String auditAction() default "";
}
