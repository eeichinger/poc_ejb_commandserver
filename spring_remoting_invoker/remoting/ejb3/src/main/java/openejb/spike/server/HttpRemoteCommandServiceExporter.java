package openejb.spike.server;

import openejb.spike.RemoteInvocationServer;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationResult;
import org.springframework.util.Assert;

public class HttpRemoteCommandServiceExporter extends HttpInvokerServiceExporter {

    public HttpRemoteCommandServiceExporter(RemoteInvocationServer remoteInvocationServer) {
        Assert.notNull(remoteInvocationServer);
        super.setService(remoteInvocationServer);
        super.setServiceInterface(RemoteInvocationServer.class);
    }

    @Override
    protected RemoteInvocationResult invokeAndCreateResult(RemoteInvocation invocation, Object targetObject) {
        return ((RemoteInvocationServer)targetObject).invoke(invocation);
    }
}
