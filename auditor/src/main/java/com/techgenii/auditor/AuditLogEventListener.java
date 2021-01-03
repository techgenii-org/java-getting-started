package com.techgenii.auditor;

import org.hibernate.event.spi.*;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;

@Component
public class AuditLogEventListener implements PostUpdateEventListener, PostInsertEventListener, PostDeleteEventListener, PostLoadEventListener {
    @Override
    public void onPostDelete(PostDeleteEvent event) {
        final AuditedEntity auditedEntity = event.getEntity().getClass().getAnnotation(AuditedEntity.class);
        if (auditedEntity != null) {
            AuditLogServiceData.getHibernateEvents().add(event);
        }

    }

    @Override
    public void onPostInsert(PostInsertEvent event) {
        final AuditedEntity auditedEntity = event.getEntity().getClass().getAnnotation(AuditedEntity.class);
        if (auditedEntity != null) {
            AuditLogServiceData.getHibernateEvents().add(event);
        }
    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        final AuditedEntity auditedEntity = event.getEntity().getClass().getAnnotation(AuditedEntity.class);
        if (auditedEntity != null) {
            AuditLogServiceData.getHibernateEvents().add(event);
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister entityPersister) {
        return true;
    }

    @Override
    public void onPostLoad(PostLoadEvent event) {
        final AuditedEntity auditedEntity = event.getEntity().getClass().getAnnotation(AuditedEntity.class);
        if (auditedEntity != null) {
            AuditLogServiceData.getHibernateEvents().add(event);
        }
    }
}
