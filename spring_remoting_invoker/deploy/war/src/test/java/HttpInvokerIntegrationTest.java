import openejb.spike.client.HttpRemoteCommandProxyFactoryBean;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.Assert.assertEquals;

public class HttpInvokerIntegrationTest {
    @Test
    public void can_connect() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("USER", "PASSWORD"));
        HttpRemoteCommandProxyFactoryBean proxyFactory = new HttpRemoteCommandProxyFactoryBean(
                "http://ubuntu:9085/demo-war/remoting/remoteCommandServer",
                "echoService",
                "http://ubuntu:9085/demo-war/remoting/remoteCommandServer",
                EchoService.class
        );

        proxyFactory.afterPropertiesSet();
        EchoService echoService = (EchoService) proxyFactory.getObject();

        String echo = echoService.echo("INPUT");
        assertEquals("", echo);
    }
}
