package com.kaz.passm.config

import org.glassfish.jersey.internal.guava.Predicates
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.Contact
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun api(): Docket = Docket(DocumentationType.SWAGGER_2)
        .select()
//        .apis(RequestHandlerSelectors.any())
        .apis(RequestHandlerSelectors.basePackage("com.kaz.passm.resource"))
        .paths(Predicates.not(PathSelectors.regex("/error")))
        .build()
        .apiInfo(metadata())
        .useDefaultResponseMessages(false)
        .securitySchemes(listOf(apiKey()))
//        .securityContexts(listOf(securityContext()))
//        .tags(Tag("users", "Operations about users"))
        .genericModelSubstitutes(Optional::class.java)
        .consumes(setOf("application/json"))
        .produces(setOf("application/json"))

    private fun metadata() = ApiInfoBuilder() //
        .title("Password manager") //
        .description("hihi") //
        .version("1.0.0") //
        .license("MIT License").licenseUrl("http://opensource.org/licenses/MIT")
        .contact(Contact("Cuong", null, "cuongnv219@gmail.com"))
        .build()

    private fun apiKey() = ApiKey("Authorization", "Authorization", "header")

    private fun securityContext() = SecurityContext.builder()
        .securityReferences(defaultAuth())
        .forPaths(PathSelectors.any())
        .build()

    private fun defaultAuth(): List<SecurityReference> {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes = arrayOfNulls<AuthorizationScope>(1)
        authorizationScopes[0] = authorizationScope
        return listOf(SecurityReference("Authorization", authorizationScopes))
    }

//    final val CONTACT = Contact(
//        "Murali", "http://muralitechblog.com/",
//        "muralitechblog@gmail.com"
//    )
//    val DEFAULT_API = ApiInfo(
//        "swagger", "Swagger Documentation", "1.0", "urn:tos", CONTACT,
//        "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", ArrayList()
//    )
//    val consumes: Set<String> = HashSet(listOf("application/json"))
//    val produces: Set<String> = HashSet(listOf("application/json"))
//
//    @Bean
//    fun api(): Docket? {
//        return Docket(DocumentationType.SWAGGER_2).apiInfo(DEFAULT_API).consumes(consumes).produces(produces)
//    }
}
