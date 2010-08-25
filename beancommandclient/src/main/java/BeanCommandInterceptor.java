import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Erich Eichinger
 * @date 2010-08-17
 */
public class BeanCommandInterceptor implements MethodInterceptor {

    private String targetBeanName;
    private BeanCommandServer beanCommandServer;

    public BeanCommandInterceptor(BeanCommandServer beanCommandServer, String targetBeanName) {
        this.beanCommandServer = beanCommandServer;
        this.targetBeanName = targetBeanName;
    }

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        BeanCommand cmd = new BeanCommand(targetBeanName, methodInvocation.getMethod().getName(), methodInvocation.getArguments());
        return beanCommandServer.invoke(cmd);
    }
}
