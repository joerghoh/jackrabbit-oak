/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.jackrabbit.oak.run;

import java.io.InputStream;
import java.util.Properties;

import javax.servlet.Servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {

    public static void main(String[] args) throws Exception {
        printProductInfo();

        ServletContextHandler context =
                new ServletContextHandler(ServletContextHandler.SECURITY);
        context.setContextPath("/");

        if (args.length == 0) {
            System.out.println("Starting an in-memory repository");
            System.out.println("http://localhost:8080/ -> [memory]");
            Servlet servlet = new RepositoryServlet(null);
            context.addServlet(new ServletHolder(servlet),"/*");
        } else if (args.length == 1) {
            System.out.println("Starting a standalone repository");
            System.out.println("http://localhost:8080/ -> " + args[0]);
            Servlet servlet = new RepositoryServlet(args[0]);
            context.addServlet(new ServletHolder(servlet), "/*");
        } else {
            System.out.println("Starting a clustered repository");
            for (int i = 0; i < args.length; i++) {
                // FIXME: Use a clustered MicroKernel implementation
                System.out.println(
                        "http://localhost:8080/node" + i + "/ -> " + args[i]);
                Servlet servlet = new RepositoryServlet(args[i]);
                context.addServlet(
                        new ServletHolder(servlet), "/node" + i + "/*");
            }
        }

        Server server = new Server(8080);
        server.setHandler(context);
        server.start();
        server.join();
    }

    private static void printProductInfo() {
        String version = null;

        try {
            InputStream stream = Main.class.getResourceAsStream(
                    "/META-INF/maven/org.apache.jackrabbit/oak-run/pom.properties");
            if (stream != null) {
                try {
                    Properties properties = new Properties();
                    properties.load(stream);
                    version = properties.getProperty("version");
                } finally {
                    stream.close();
                }
            }
        } catch (Exception ignore) {
        }

        String product;
        if (version != null) {
            product = "Apache Jackrabbit Oak " + version;
        } else {
            product = "Apache Jackrabbit Oak";
        }

        System.out.println(product);
    }

}
