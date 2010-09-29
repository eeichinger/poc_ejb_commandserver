package openejb.spike.client;

import openejb.spike.RemoteInvocationServer;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationResult;

public class RemoteInvokerProxyFactoryBeanTest {

    private interface EchoService {
        String echo(String argument);
    }

    private class StubEchoServiceRemoteCommandServer implements RemoteInvocationServer {

        public RemoteInvocation lastRemoteInvocation;

        public RemoteInvocationResult invoke(RemoteInvocation remoteInvocation) {
            this.lastRemoteInvocation = remoteInvocation;
            return new RemoteInvocationResult("TEST-OUTPUT");
        }
    }

    @Test
    public void intercepts_and_calls_server() {

        StubEchoServiceRemoteCommandServer stubRemoteCommandServer = new StubEchoServiceRemoteCommandServer();

        RemoteCommandProxyFactoryBean proxyFactory = new RemoteCommandProxyFactoryBean(
                "serviceUrl"
                , new RemoteCommandServerRemoteCommandExecutor(stubRemoteCommandServer)
                , EchoService.class
        );
        proxyFactory.afterPropertiesSet();

        EchoService echoService = (EchoService) proxyFactory.getObject();
        String result = echoService.echo("TEST-INPUT");

        Assert.assertEquals("TEST-OUTPUT", result);
    }
}
