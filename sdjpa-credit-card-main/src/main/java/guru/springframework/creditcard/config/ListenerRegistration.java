package guru.springframework.creditcard.config;

import guru.springframework.creditcard.listeners.PostLoadListener;
import guru.springframework.creditcard.listeners.PreInsertListener;
import guru.springframework.creditcard.listeners.PreUpdateListener;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class ListenerRegistration implements BeanPostProcessor {

    private final PostLoadListener postLoadListener;

    private final PreInsertListener preInsertListener;

    private final PreUpdateListener preUpdateListener;

    public ListenerRegistration(PostLoadListener postLoadListener,
                                PreInsertListener preInsertListener,
                                PreUpdateListener preUpdateListener) {
        this.postLoadListener = postLoadListener;
        this.preInsertListener = preInsertListener;
        this.preUpdateListener = preUpdateListener;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        /*
            This line was replaced with a pattern variable:

                LocalContainerEntityManagerFactoryBean localEntityManagerFactory =
                    (LocalContainerEntityManagerFactoryBean) bean;
        */
        if (bean instanceof LocalContainerEntityManagerFactoryBean localEntityManagerFactory) {
            SessionFactoryImpl sessionFactory = (SessionFactoryImpl) localEntityManagerFactory.getNativeEntityManagerFactory();
            EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
            registry.appendListeners(EventType.POST_LOAD, postLoadListener);
            registry.appendListeners(EventType.PRE_INSERT, preInsertListener);
            registry.appendListeners(EventType.PRE_UPDATE, preUpdateListener);
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

}
