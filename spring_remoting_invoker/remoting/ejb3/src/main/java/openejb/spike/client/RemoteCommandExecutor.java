package openejb.spike.client;

import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationResult;

public interface RemoteCommandExecutor {
	/**
	 * Execute a request to send the given remote invocation.
	 * @param invocation the RemoteInvocation to execute
	 * @return the RemoteInvocationResult object
	 * @throws java.io.IOException if thrown by I/O operations
	 * @throws ClassNotFoundException if thrown during deserialization
	 * @throws Exception in case of general errors
	 */
	RemoteInvocationResult execute(RemoteInvocation invocation) throws Exception;
}
