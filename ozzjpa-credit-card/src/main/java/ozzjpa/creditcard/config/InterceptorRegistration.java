package ozzjpa.creditcard.config;

import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;

import java.util.Map;

//@Configuration
public class InterceptorRegistration implements HibernatePropertiesCustomizer {

    //@Autowired
    //EncryptionInterceptor encryptionInterceptor;

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        //hibernateProperties.put("hibernate.session_factory.interceptor", encryptionInterceptor);
    }

}
