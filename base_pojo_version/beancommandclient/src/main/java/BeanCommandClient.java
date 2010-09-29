import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.Assert;

/**
 * @author Erich Eichinger
 * @date 2010-08-17
 */
public class BeanCommandClient implements FactoryBean //, BeanNameAware
{

    private String beanName;
    private BeanCommandServer server;
    private Class interfaceType;

    public void setBeanName(String name) {
        this.beanName = name;
    }

    public BeanCommandClient(BeanCommandServer server, Class interfaceType) {
        Assert.notNull(server);
        Assert.notNull(interfaceType);
        this.server = server;
        this.interfaceType = interfaceType;
    }

    public Object getObject() throws Exception {
        ProxyFactory pf = new ProxyFactory(interfaceType,
                new BeanCommandInterceptor(server, beanName));
        return pf.getProxy();
    }

    public Class getObjectType() {
        return interfaceType;
    }

    public boolean isSingleton() {
        return true;
    }
}
