package com.riwi.filtroSpringBoot.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Filtro",
        version = "1.0",
        description = "",
       
        license = @License(
            name = "JSFM"
        ),
        contact = @Contact(
                        name = "Juan Sebastián Fernández Montoya",
                        url = "https://juansefdz.com/"
                        
        )
       
        
    ) 
)
public class SwaggerConfig {
    
}
