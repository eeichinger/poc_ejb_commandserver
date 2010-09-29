package openejb.spike.client;

import org.springframework.remoting.httpinvoker.HttpInvokerClientConfiguration;
import org.springframework.remoting.httpinvoker.HttpInvokerRequestExecutor;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationResult;
import org.springframework.util.Assert;

public class HttpRemoteCommandExecutor implements HttpInvokerClientConfiguration, RemoteCommandExecutor {

    private final String serviceUrl;
    private final String codebaseUrl;
    private final HttpInvokerRequestExecutor requestExecutor;

    public HttpRemoteCommandExecutor(String serviceUrl, String codebaseUrl, HttpInvokerRequestExecutor requestExecutor) {
        this.serviceUrl = serviceUrl;
        this.codebaseUrl = codebaseUrl;
        this.requestExecutor = requestExecutor;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public String getCodebaseUrl() {
        return codebaseUrl;
    }

    public RemoteInvocationResult execute(RemoteInvocation invocation) throws Exception {
        return requestExecutor.executeRequest(this, invocation);
    }
}
