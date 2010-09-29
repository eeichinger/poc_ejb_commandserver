package openejb.spike;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.security.core.context.SecurityContext;

public class RemoteCommandInvocation extends RemoteInvocation {

    private static final long serialVersionUID = 6876024250231820554L;

    private final String targetName;
    private final String typeName;
    private final SecurityContext securityContext;

    public String getTargetName() {
        return targetName;
    }

    public String getTypeName() {
        return typeName;
    }

    public SecurityContext getSecurityContext() {
        return securityContext;
    }

    public RemoteCommandInvocation(String targetName, String typeName, String methodName, Class[] parameterTypes, Object[] arguments, SecurityContext securityContext) {
        super(methodName, parameterTypes, arguments);
        this.targetName = targetName;
        this.typeName = typeName;
        this.securityContext = securityContext;
    }

    public RemoteCommandInvocation(String targetName, MethodInvocation methodInvocation, SecurityContext securityContext) {
        super(methodInvocation);
        this.targetName = targetName;
        this.typeName = methodInvocation.getMethod().getDeclaringClass().getName();
        this.securityContext = securityContext;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RemoteCommandInvocation that = (RemoteCommandInvocation) o;

        return new EqualsBuilder()
                .append(this.securityContext, that.securityContext)
                .append(this.targetName, that.targetName)
                .append(super.getMethodName(), that.getMethodName())
                .append(super.getParameterTypes(), that.getParameterTypes())
                .append(super.getArguments(), that.getArguments())
                .append(super.getAttributes(), that.getAttributes())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(31, 19)
                .append(this.securityContext)
                .append(this.targetName)
                .append(super.getMethodName())
                .append(super.getParameterTypes())
                .append(super.getArguments())
                .append(super.getAttributes())
                .toHashCode();
    }
}
