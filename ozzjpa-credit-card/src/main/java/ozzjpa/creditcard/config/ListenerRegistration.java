package ozzjpa.creditcard.config;

import ozzjpa.creditcard.listeners.PostLoadListener;
import ozzjpa.creditcard.listeners.PreInsertListener;
import ozzjpa.creditcard.listeners.PreUpdateListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

//@Component
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
        /*if (bean instanceof LocalContainerEntityManagerFactoryBean localEntityManagerFactory) {
            SessionFactoryImpl sessionFactory = (SessionFactoryImpl) localEntityManagerFactory.getNativeEntityManagerFactory();
            EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
            registry.appendListeners(EventType.POST_LOAD, postLoadListener);
            registry.appendListeners(EventType.PRE_INSERT, preInsertListener);
            registry.appendListeners(EventType.PRE_UPDATE, preUpdateListener);
        }*/
        return bean;
    }

}
