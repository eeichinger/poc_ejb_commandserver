package openejb.spike.server;

import openejb.spike.RemoteCommandInvocation;
import openejb.spike.RemoteInvocationServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.support.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;

public class RemoteCommandInvocationServer implements RemoteInvocationServer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RemoteInvocationExecutor remoteInvocationExecutor;
    private final RemoteInvocationTargetResolver targetResolver;

    /**
     * Return the RemoteInvocationExecutor used by this exporter.
     */
    public RemoteInvocationExecutor getRemoteInvocationExecutor() {
        return this.remoteInvocationExecutor;
    }

    public RemoteCommandInvocationServer(RemoteInvocationTargetResolver targetResolver) {
        this(targetResolver, new DefaultRemoteInvocationExecutor());
    }

    public RemoteCommandInvocationServer(RemoteInvocationTargetResolver targetResolver, RemoteInvocationExecutor remoteInvocationExecutor) {
        Assert.notNull(targetResolver);
        Assert.notNull(remoteInvocationExecutor);

        this.targetResolver = targetResolver;
        this.remoteInvocationExecutor = remoteInvocationExecutor;
    }

    public RemoteInvocationResult invoke(RemoteInvocation remoteInvocation) {
        Assert.isTrue(RemoteCommandInvocation.class.isInstance(remoteInvocation), "remoteInvocation must be an instance of " + RemoteCommandInvocation.class.getName());

        RemoteCommandInvocation remoteCommandInvocation = (RemoteCommandInvocation) remoteInvocation;

        SecurityContextHolder.setContext(remoteCommandInvocation.getSecurityContext());

        try {
            Object target = targetResolver.getTarget(remoteInvocation);
            Object value = invoke(remoteCommandInvocation, target);
            RemoteInvocationResult result = new RemoteInvocationResult(value);
            return result;
        } catch (Throwable ex) {
            return new RemoteInvocationResult(ex);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    /**
     * Apply the given remote invocation to the given target object.
     * The default implementation delegates to the RemoteInvocationExecutor.
     * <p>Can be overridden in subclasses for custom invocation behavior,
     * possibly for applying additional invocation parameters from a
     * custom RemoteInvocation subclass. Note that it is preferable to use
     * a custom RemoteInvocationExecutor which is a reusable strategy.
     * @param invocation the remote invocation
     * @param targetObject the target object to apply the invocation to
     * @return the invocation result
     * @throws NoSuchMethodException if the method name could not be resolved
     * @throws IllegalAccessException if the method could not be accessed
     * @throws java.lang.reflect.InvocationTargetException if the method invocation resulted in an exception
     * @see RemoteInvocationExecutor#invoke
     */
    protected Object invoke(RemoteInvocation invocation, Object targetObject) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (logger.isTraceEnabled()) {
            logger.trace("Executing " + invocation);
        }
        try {
            return getRemoteInvocationExecutor().invoke(invocation, targetObject);
        }
        catch (NoSuchMethodException ex) {
            if (logger.isDebugEnabled()) {
                logger.warn("Could not find target method for " + invocation, ex);
            }
            throw ex;
        }
        catch (IllegalAccessException ex) {
            if (logger.isDebugEnabled()) {
                logger.warn("Could not access target method for " + invocation, ex);
            }
            throw ex;
        }
        catch (InvocationTargetException ex) {
            if (logger.isDebugEnabled()) {
                logger.debug("Target method failed for " + invocation, ex.getTargetException());
            }
            throw ex;
        }
    }
}
