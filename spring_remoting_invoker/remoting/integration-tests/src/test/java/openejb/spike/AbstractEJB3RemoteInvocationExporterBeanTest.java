package openejb.spike;

import openejb.spike.client.RemoteCommandProxyFactoryBean;
import openejb.spike.client.RemoteCommandServerRemoteCommandExecutor;
import openejb.spike.server.AbstractEJB3RemoteInvocationServerBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import testclasses.EchoService;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

public class AbstractEJB3RemoteInvocationExporterBeanTest {

    @Remote
    public interface MyEJB3RemoteInvocationServerRemote
            extends RemoteInvocationServer {
    }

    @Stateless
    public static class MyEJB3RemoteInvocationServerBean
            extends AbstractEJB3RemoteInvocationServerBean
            implements MyEJB3RemoteInvocationServerRemote {
    }

    private InitialContext initialContext;

    @Before
    public void setUp() throws Exception {
        Properties properties = new Properties();
        properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, org.apache.openejb.client.LocalInitialContextFactory.class.getName());

        initialContext = new InitialContext(properties);
    }

    @Test
    public void test() throws Exception {
        RemoteInvocationServer invocationServer = (RemoteInvocationServer) initialContext.lookup(MyEJB3RemoteInvocationServerBean.class.getSimpleName() + "Remote");

        RemoteCommandProxyFactoryBean proxyFactory = new RemoteCommandProxyFactoryBean(
                "echoService"
                , new RemoteCommandServerRemoteCommandExecutor(invocationServer)
                , EchoService.class
        );
        proxyFactory.afterPropertiesSet();
        EchoService echoService = (EchoService) proxyFactory.getObject();

        final String USERNAME = "USERNAME";
        final String MESSAGE = "MESSAGE";
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(USERNAME, "PASSWORD"));
        String response = echoService.echo(MESSAGE);
        SecurityContextHolder.clearContext();

        Assert.assertEquals(MESSAGE + "[" + USERNAME +"]", response);
    }
}
