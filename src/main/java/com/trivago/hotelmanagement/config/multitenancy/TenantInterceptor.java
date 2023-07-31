package com.trivago.hotelmanagement.config.multitenancy;


import com.trivago.hotelmanagement.config.security.CustomUserDetail;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.IntStream;

@Component
public class TenantInterceptor extends EmptyInterceptor {
    private final Logger LOGGER = LoggerFactory.getLogger(TenantInterceptor.class);
//    @Override
//    public boolean onLoad(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
//        LOGGER.info("loading an entity");
//        return true;
//    }

    @Override
    public boolean onSave(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        LOGGER.info("saving an entity");
        return addTenantIdIfRequired(entity, propertyNames, state);
    }

//    @Override
//    public void onDelete(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
//        LOGGER.info("deleting an entity");
//    }
//
//    @Override
//    public boolean onFlushDirty(Object entity, Object id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) throws CallbackException {
//        LOGGER.info("flushing an entity");
//        return true;
//    }

    private boolean addTenantIdIfRequired(Object entity, String[] propertyNames, Object[] state) {
        if (entity instanceof ITenantAwareEntity) {
            OptionalInt index = IntStream.range(0, propertyNames.length).filter(nameInt -> propertyNames[nameInt].equals("tenantId")).findFirst();
            if (index.isPresent()) {
                CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                state[index.getAsInt()] = userDetail.getTenantId();
            }
        }
        return true;
    }
}
