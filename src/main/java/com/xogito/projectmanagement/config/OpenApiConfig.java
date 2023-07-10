package com.xogito.projectmanagement.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@OpenAPIDefinition(info = @Info(title = "User and Project Management API", version = "1.0.0", description = "Xogito Project Management API"))
public class OpenApiConfig {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Bean
    public OpenAPI projectApi(){
        final List<Server> serverList = new ArrayList<>();
        Server server = new Server();
        server.setUrl(contextPath);
        server.description("Development Server");

        serverList.add(server);

        return new OpenAPI().servers(serverList);

    }

}
