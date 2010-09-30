package openejb.spike.server;

import openejb.spike.RemoteInvocationServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationResult;
import org.springframework.util.Assert;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class RemoteInvocationServerBean implements RemoteInvocationServer, RemoteInvocationServerRemote {

    @Autowired
    @Qualifier("remoteInvocationServerDelegate")
    private RemoteInvocationServer remoteInvocationServerDelegate;

    public RemoteInvocationResult invoke(RemoteInvocation remoteInvocation) {
        Assert.notNull(remoteInvocation, "remoteInvocation is mandatory");

        RemoteInvocationResult result = remoteInvocationServerDelegate.invoke(remoteInvocation);
        return result;
    }
}
