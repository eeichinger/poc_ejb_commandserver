package openejb.spike.client;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.remoting.RemoteInvocationFailureException;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationBasedAccessor;
import org.springframework.remoting.support.RemoteInvocationResult;

public class RemoteCommandClientInterceptor extends RemoteInvocationBasedAccessor implements MethodInterceptor {

    private RemoteCommandExecutor requestExecutor;

    public RemoteCommandExecutor getRequestExecutor() {
        return requestExecutor;
    }

    public RemoteCommandClientInterceptor(String serviceUrl, RemoteCommandExecutor requestExecutor, Class<?> serviceInterface) {
        this.requestExecutor = requestExecutor;
        super.setServiceUrl(serviceUrl);
        super.setRemoteInvocationFactory(new RemoteCommandInvocationFactory(serviceUrl));
        super.setServiceInterface(serviceInterface);
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        if (getServiceInterface() == null) {
            throw new IllegalArgumentException("Property 'serviceInterface' is required");
        }
        if (getRequestExecutor() == null) {
            throw new IllegalArgumentException("Property 'requestExecutor' is required");
        }
    }

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        if (AopUtils.isToStringMethod(methodInvocation.getMethod())) {
            return "RemoteCommand invoker proxy for service URL [" + getServiceUrl() + "]";
        }

        RemoteInvocation invocation = createRemoteInvocation(methodInvocation);
        RemoteInvocationResult result = null;
        try {
            result = executeRequest(invocation, methodInvocation);
        }
        catch (Throwable ex) {
            throw convertException(ex);
        }
        try {
            return recreateRemoteInvocationResult(result);
        }
        catch (Throwable ex) {
            if (result.hasInvocationTargetException()) {
                throw ex;
            }
            else {
                throw new RemoteInvocationFailureException("Invocation of method [" + methodInvocation.getMethod() +
                        "] failed in RemoteCommand invoker remote service at [" + getServiceUrl() + "]", ex);
            }
        }
    }

    protected Throwable convertException(Throwable ex) {
        return ex;
    }

    /**
     * Execute the given remote invocation via the HttpInvokerRequestExecutor.
     * Can be overridden to react to the specific original MethodInvocation.
     * @param invocation the RemoteInvocation to execute
     * @param originalInvocation the original MethodInvocation (can e.g. be cast
     * to the ProxyMethodInvocation interface for accessing user attributes)
     * @return the RemoteInvocationResult object
     * @throws Exception in case of errors
     */
    protected RemoteInvocationResult executeRequest(RemoteInvocation invocation, MethodInvocation originalInvocation) throws Exception {
        RemoteInvocationResult result = requestExecutor.execute(invocation);
        return result;
    }
}
