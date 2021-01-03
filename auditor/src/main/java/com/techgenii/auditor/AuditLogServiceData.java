package com.techgenii.auditor;


import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.List;

public class AuditLogServiceData {

    public static final String HIBERNATE_EVENTS = "hibernateEvents";
    public static final String AUDIT_LOG_ACTOR = "auditLogActor";

    public static List<Object> getHibernateEvents() {
        if (!TransactionSynchronizationManager.hasResource(HIBERNATE_EVENTS)) {
            TransactionSynchronizationManager.bindResource(HIBERNATE_EVENTS,new ArrayList<>());
        }

        //noinspection unchecked
        return (List<Object>) TransactionSynchronizationManager.getResource(HIBERNATE_EVENTS);
    }

    public static Long getActorId() {
        return (Long) TransactionSynchronizationManager.getResource(AUDIT_LOG_ACTOR);
    }

    public static void setActor(Long value) {
        if (value != null) {
            TransactionSynchronizationManager.bindResource(AUDIT_LOG_ACTOR,value);
        }
    }
}
