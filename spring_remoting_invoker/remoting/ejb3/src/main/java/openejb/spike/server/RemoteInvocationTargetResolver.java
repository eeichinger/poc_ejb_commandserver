package openejb.spike.server;

import org.springframework.remoting.support.RemoteInvocation;

public interface RemoteInvocationTargetResolver {
    Object getTarget(RemoteInvocation remoteInvocation);
}
