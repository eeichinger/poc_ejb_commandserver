package openejb.spike.client;

import openejb.spike.RemoteCommandInvocation;
import openejb.spike.server.RemoteCommandInvocationServer;
import openejb.spike.server.RemoteInvocationTargetResolver;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.remoting.support.RemoteInvocationExecutor;
import org.springframework.remoting.support.RemoteInvocationResult;
import org.springframework.security.core.context.SecurityContextImpl;

public class RemoteCommandInvocationServerTest {
    @Test
    public void resolves_and_delegates_methodcall_to_target() throws Exception {
        RemoteInvocationTargetResolver ritr = Mockito.mock(RemoteInvocationTargetResolver.class);
        RemoteInvocationExecutor rie = Mockito.mock(RemoteInvocationExecutor.class);

        RemoteCommandInvocationServer server = new RemoteCommandInvocationServer(ritr, rie);

        final RemoteCommandInvocation invocation = new RemoteCommandInvocation("targetName", "typeName", "methodName", new Class[0], new Object[0], new SecurityContextImpl());
        final Object expectedTarget = new Object();
        final Object expectedResult = new Object();

        Mockito.when(ritr.getTarget(invocation)).thenReturn(expectedTarget);
        Mockito.when(rie.invoke(invocation, expectedTarget)).thenReturn(expectedResult);

        RemoteInvocationResult invocationResult = server.invoke(invocation);

        Assert.assertSame(expectedResult, invocationResult.getValue());
    }
}
