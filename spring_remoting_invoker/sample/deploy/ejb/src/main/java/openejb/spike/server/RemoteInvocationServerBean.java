package openejb.spike.server;

import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

/**
 * Websphere 6.1 requires annotated EJBs to be defined in the EJB assembly.
 * It also requires the annotations to be defined on this bean in this assembly.
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class RemoteInvocationServerBean
        extends AbstractEJB3RemoteInvocationServerBean
        implements RemoteInvocationServerRemote {
}
