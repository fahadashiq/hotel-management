package com.trivago.hotelmanagement.config.multitenancy;

import com.trivago.hotelmanagement.config.security.CustomUserDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Session;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TenantAspect {

    @PersistenceContext
    private EntityManager entityManager;
    @Pointcut("@within(com.trivago.hotelmanagement.config.multitenancy.TenantAwareService)")
    public void tenantAwareRepository() {

    }

    @Around("tenantAwareRepository()")
    public Object applyTenantFilter(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        entityManager.unwrap(Session.class).enableFilter("TenantFilter").setParameter("tenantId", userDetail.getTenantId());
        return proceedingJoinPoint.proceed();
    }
}
