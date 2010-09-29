package openejb.spike.client;

import openejb.spike.RemoteInvocationServer;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationResult;
import org.springframework.util.Assert;

public class RemoteCommandServerRemoteCommandExecutor implements RemoteCommandExecutor {

    private final RemoteInvocationServer remoteCommandServer;

    public RemoteCommandServerRemoteCommandExecutor(RemoteInvocationServer remoteCommandServer) {
        Assert.notNull(remoteCommandServer);
        this.remoteCommandServer = remoteCommandServer;
    }

    public RemoteInvocationResult execute(RemoteInvocation invocation) throws Exception {
        return remoteCommandServer.invoke(invocation);
    }
}
