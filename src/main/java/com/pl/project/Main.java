package com.pl.project;

import com.pl.project.controllers.Grades;
import com.pl.project.controllers.Students;
import com.pl.project.controllers.Subjects;
import com.pl.project.models.DateParamConverterProvider;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;


import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

public class Main {
    private static int getPort(int defaultPort) {
        String httpPort = System.getProperty("jersey.test.port");
        if (null != httpPort) {
            try {
                return Integer.parseInt(httpPort);
            } catch (NumberFormatException e) {
            }
        }
        return defaultPort;
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost/").port(getPort(9080)).build();
    }

    public static final URI BASE_URI = getBaseURI();

    protected static HttpServer startServer() throws IOException {
        ResourceConfig resourceConfig = new ResourceConfig(Grades.class,Students.class,Subjects.class);
        resourceConfig.register(new DateParamConverterProvider("yyyy-MM-dd"));
        System.out.println("Starting grizzly2...");
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig);
    }

    public static void main(String[] args) throws IOException {
        // Grizzly 2 initialization
        HttpServer httpServer = startServer();
        httpServer.getServerConfiguration().addHttpHandler(new StaticHttpHandler("C:\\Users\\Filip Sosnowski\\Desktop\\Moje\\WSI_Project\\src\\main\\webapp\\WEB-INF\\templates\\"),"/main");
        System.out.println(String.format("Jersey app started with WADL available at "
                        + "%sapplication.wadl\nHit enter to stop it...",
                BASE_URI));
        System.in.read();
        httpServer.shutdown();
    }
}
