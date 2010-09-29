package openejb.spike.client;

import openejb.spike.RemoteCommandInvocation;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class RemoteCommandInvocationFactory implements RemoteInvocationFactory {

    private final String targetName;

    public RemoteCommandInvocationFactory(String targetName) {
        this.targetName = targetName;
    }

    public RemoteInvocation createRemoteInvocation(MethodInvocation methodInvocation) {
        SecurityContext context = SecurityContextHolder.getContext();
        return new RemoteCommandInvocation(targetName, methodInvocation, context);
    }
}
