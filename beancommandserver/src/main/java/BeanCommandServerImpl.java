import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Method;

/**
 * @author Erich Eichinger
 * @date 2010-08-17
 */
public class BeanCommandServerImpl implements BeanCommandServer, BeanFactoryAware {

    private BeanFactory beanFactory;

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    protected BeanFactory getBeanFactory() {
        if (beanFactory == null) {
            beanFactory = new ClassPathXmlApplicationContext("classpath:/applicationContext.xml");
        }
        return beanFactory;
    }

    public Object invoke(BeanCommand command) {
        try {
            Object targetBean = beanFactory.getBean( command.getTargetBeanName() );
            return findMethod(targetBean, command.getTargetMethodName()).invoke(targetBean,command.getArgs());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected Method findMethod(Object targetBean, String methodName) throws Exception {
        for(Method method: targetBean.getClass().getMethods()) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        };
        throw new Exception("method " + methodName + " is not implemented by " + targetBean);
    }
}
