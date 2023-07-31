package com.trivago.hotelmanagement.config.multitenancy;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public interface ITenantAwareEntity {
    String getTenantId();
    void setTenantId(String tenantId);
}
