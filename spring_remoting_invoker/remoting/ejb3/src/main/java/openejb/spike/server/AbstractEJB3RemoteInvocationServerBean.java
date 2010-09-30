/**
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package openejb.spike.server;

import openejb.spike.RemoteInvocationServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationResult;
import org.springframework.util.Assert;

import javax.interceptor.Interceptors;

@Interceptors(SpringBeanAutowiringInterceptor.class)
public abstract class AbstractEJB3RemoteInvocationServerBean implements RemoteInvocationServer {

    @Autowired
    @Qualifier("remoteInvocationServerDelegate")
    private RemoteInvocationServer remoteInvocationServerDelegate;

    public RemoteInvocationResult invoke(RemoteInvocation remoteInvocation) {
        Assert.notNull(remoteInvocation, "remoteInvocation is mandatory");

        RemoteInvocationResult result = remoteInvocationServerDelegate.invoke(remoteInvocation);
        return result;
    }
}
