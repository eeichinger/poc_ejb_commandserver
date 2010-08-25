import java.io.Serializable;

/**
 * @author Erich Eichinger
 * @date 2010-08-17
 */
public class BeanCommand implements Serializable {
    private String targetBeanName;
    private String targetMethodName;
    private Object[] args;

    public String getTargetBeanName() {
        return targetBeanName;
    }

    public String getTargetMethodName() {
        return targetMethodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public BeanCommand(String targetBeanName, String targetMethodName, Object[] args) {
        this.targetBeanName = targetBeanName;
        this.targetMethodName = targetMethodName;
        this.args = args;
    }
}
