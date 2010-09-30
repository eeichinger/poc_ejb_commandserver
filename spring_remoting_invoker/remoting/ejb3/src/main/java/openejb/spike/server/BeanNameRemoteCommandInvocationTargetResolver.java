package openejb.spike.server;

import openejb.spike.RemoteCommandInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.util.Assert;

public class BeanNameRemoteCommandInvocationTargetResolver implements RemoteInvocationTargetResolver, BeanFactoryAware, InitializingBean {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private BeanFactory beanFactory;

    public BeanNameRemoteCommandInvocationTargetResolver() {
    }

//    public BeanNameRemoteCommandInvocationTargetResolver(BeanFactory beanFactory) {
//        Assert.notNull(this.beanFactory, "beanFactory must not be null");
//        this.beanFactory = beanFactory;
//    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.beanFactory, "beanFactory has not been set");
    }

    public Object getTarget(RemoteInvocation remoteInvocation) {
        Assert.notNull(this.beanFactory, "beanFactory has not been set");
        Assert.isTrue(RemoteCommandInvocation.class.isInstance(remoteInvocation), "remoteInvocation must be an instance of " + RemoteCommandInvocation.class.getName());

        final RemoteCommandInvocation remoteCommandInvocation = (RemoteCommandInvocation) remoteInvocation;

        String targetName = remoteCommandInvocation.getTargetName();
        log.debug("looking up " + targetName + " in beanFactory");
        if (this.beanFactory.containsBean(targetName)) {
            Object target = this.beanFactory.getBean(targetName);
            log.info("resolved target " + targetName + " from beanFactory");
            return target;
        }

        log.debug("looking up " + targetName + " in JNDI context");
        Object target = lookupJndiObject(targetName, remoteCommandInvocation.getTypeName());
        Assert.notNull(target, "target " + targetName + " could not be resolved, neither from BeanFactory nor JNDI");
        log.info("resolved target " + targetName + " from beanFactory");
        return target;
    }

    private Object lookupJndiObject(String targetName, String targetTypeName) {
        try {
            JndiObjectFactoryBean targetFactory = new JndiObjectFactoryBean();
            targetFactory.setBeanClassLoader(this.getClass().getClassLoader());
            targetFactory.setJndiName(targetName);
            targetFactory.setCache(true);
            final Class<?> clazz = this.getClass().getClassLoader().loadClass(targetTypeName);
            Assert.notNull(clazz, "JNDI businessInterface " + targetTypeName + " could not be loaded");
            targetFactory.setProxyInterface(clazz);
            targetFactory.afterPropertiesSet();
            Object target = targetFactory.getObject();
            ((ConfigurableBeanFactory)this.beanFactory).registerSingleton(targetName, target);
            return target;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
