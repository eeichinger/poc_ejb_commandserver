// This program may be used, executed, copied, modified and distributed
// without royalty for the purpose of developing, using, marketing, or distributing.

package com.ibm.websphere.ejb3sample.counter;


import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;


public class Audit implements Serializable {

    private static final long serialVersionUID = 4267181799103606230L;

    @AroundInvoke

    public Object methodChecker (InvocationContext ic)
    throws Exception
    {
        System.out.println("Audit:methodChecker - About to execute method: " + ic.getMethod());
        Object result = ic.proceed();
        return result;
    }
}
