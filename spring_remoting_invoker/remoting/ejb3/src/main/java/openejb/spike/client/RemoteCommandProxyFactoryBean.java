package openejb.spike.client;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;

public class RemoteCommandProxyFactoryBean extends RemoteCommandClientInterceptor implements FactoryBean {

    private Object serviceProxy;

    public RemoteCommandProxyFactoryBean(String serviceUrl, RemoteCommandExecutor requestExecutor, Class serviceInterface) {
        super(serviceUrl, requestExecutor, serviceInterface);
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        this.serviceProxy = new ProxyFactory(getServiceInterface(), this).getProxy(getBeanClassLoader());
    }

    public Object getObject() {
        return this.serviceProxy;
    }

    public Class<?> getObjectType() {
        return getServiceInterface();
    }

    public boolean isSingleton() {
        return true;
    }

}
