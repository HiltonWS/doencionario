package com.github.hiltonws.doencionario.security;

import com.github.hiltonws.doencionario.security.jwt.JWTAuthenticationFilter;
import com.github.hiltonws.doencionario.security.jwt.JWTLoginFilter;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Configurando o JWT e acesso fora do browser
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/rest/v1/login",
                        "/v3/api-docs",
                        "/swagger-ui.html",
                        "/swagger-ui/*")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .addFilterBefore(new JWTLoginFilter("/rest/v1/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Usuario padrão, não haverá cadastro de usuário
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("{noop}admin")
                .roles("ADMIN");
    }


    //Configuração usada para a documentação do rest
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"))
                        .addSchemas("AccountCredentials", new Schema()
                                .type("object")
                                .addProperties("username", new Schema()
                                        .type("string"))
                                .addProperties("password", new Schema()
                                        .type("string")))
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .path("/rest/v1/login", new PathItem()
                        .post(new Operation()
                                .addTagsItem("login")
                                .description("Adquire um bearer token")
                                .requestBody(new RequestBody()
                                        .content(new Content()
                                                .addMediaType("application/json", new MediaType()
                                                        .schema(new Schema()
                                                                .$ref("#/components/schemas/AccountCredentials")))))
                                .responses(new ApiResponses()
                                        .addApiResponse("200", new ApiResponse().description("Retorna no header o bearer token usado para autenticação")))))

                .info(new Info()
                        .title("Doencionario")
                        .description("Aqui você pode vincular uma doença ao beneficiário :)")
                        .version("v1")
                        .contact(new Contact()
                                .email("hws689#gmail.com")
                                .name("Hilton W. Silva")));
    }
}
