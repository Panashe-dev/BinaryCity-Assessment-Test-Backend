package com.binary.city.assignmenttest.config;



import com.binary.city.assignmenttest.utils.ReadJsonFileToJsonObject;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.io.IOException;

@OpenAPIDefinition
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() throws IOException {

        ReadJsonFileToJsonObject readJsonFileToJsonObject=new ReadJsonFileToJsonObject();

        ApiResponse BadRequestApi=new ApiResponse().content(
           new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                   new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                           new Example().value(readJsonFileToJsonObject.read().get("badRequestResponse").toString())))
        ).description("bad request!");

        ApiResponse InternalServerErrorAPI =new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value(readJsonFileToJsonObject.read().get("internalServerErrorResponse").toString())))
        ).description("internal sever error!");;


        Components components=new Components();
        components.addResponses("BadRequestAPI",BadRequestApi);
        components.addResponses("InternalServerErrorAPI",InternalServerErrorAPI);

        return new OpenAPI()
                .components(components)
                .info(new Info()
                        .title("Binary City  Application API")
                        .description("This is a sample  RESTful service using springdoc-openapi and OpenAPI 3.")
                        .termsOfService("terms")
                        .contact(new Contact().email("panashemugomba99@gmail.com"))
                        .license(new License().name("Binary City"))
                        .version("v1.0")
                );
    }
}