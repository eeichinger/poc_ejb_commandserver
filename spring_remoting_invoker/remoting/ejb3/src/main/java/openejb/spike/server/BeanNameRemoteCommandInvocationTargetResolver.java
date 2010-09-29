package openejb.spike.server;

import openejb.spike.RemoteCommandInvocation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.util.Assert;

public class BeanNameRemoteCommandInvocationTargetResolver implements RemoteInvocationTargetResolver, BeanFactoryAware, InitializingBean {

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

        String targetName = ((RemoteCommandInvocation)remoteInvocation).getTargetName();
        Object target = this.beanFactory.getBean(targetName);
        return target;
    }
}
