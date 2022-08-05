package br.com.mateus.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

import br.com.mateus.crud.endpoint.filter.AuthenticationFilter;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@ComponentScan(basePackages = "br.com.mateus.crud.*")
@ServletComponentScan(basePackageClasses = { AuthenticationFilter.class })
@OpenAPIDefinition(info = @Info(title = "Spring", version = "1.0", description = "Spring Study"))
public class CrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudApplication.class, args);
    }

}
