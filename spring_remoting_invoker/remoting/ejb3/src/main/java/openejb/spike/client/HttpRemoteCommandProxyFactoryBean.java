package openejb.spike.client;

import org.springframework.remoting.httpinvoker.SimpleHttpInvokerRequestExecutor;

public class HttpRemoteCommandProxyFactoryBean extends RemoteCommandProxyFactoryBean {
    public HttpRemoteCommandProxyFactoryBean(String remoteCommandServerUrl, String serviceUrl, String codebaseUrl, Class serviceInterface) {
        super(serviceUrl, new HttpRemoteCommandExecutor(remoteCommandServerUrl, codebaseUrl, new SimpleHttpInvokerRequestExecutor()), serviceInterface);
    }
}
