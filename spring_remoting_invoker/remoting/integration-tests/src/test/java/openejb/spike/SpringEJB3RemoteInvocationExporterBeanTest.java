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
package openejb.spike;

import openejb.spike.client.RemoteCommandProxyFactoryBean;
import openejb.spike.client.RemoteCommandServerRemoteCommandExecutor;
import openejb.spike.server.SpringEJB3RemoteInvocationServerBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import testclasses.EchoService;

import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

public class SpringEJB3RemoteInvocationExporterBeanTest {

    @Stateless
    public static class MyEJB3RemoteInvocationBean extends SpringEJB3RemoteInvocationServerBean {
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
        RemoteInvocationServer invocationServer = (RemoteInvocationServer) initialContext.lookup(MyEJB3RemoteInvocationBean.class.getSimpleName() + "Remote");

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
