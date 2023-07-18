package org.sky.base.common.swagger.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * Spring Doc 配置
 * </p>
 *
 * @author liusongling
 * @since 2023-07-15 17:02:27
 */
@Configuration
@EnableAutoConfiguration
@ConditionalOnProperty(name = "swagger.enabled")
public class SwaggerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SwaggerProperties swaggerProperties() {
        return new SwaggerProperties();
    }

    @Bean
    public OpenAPI api(SwaggerProperties swaggerProperties) {
        final String loginToken = "BearerAuth";
        return new OpenAPI().info(new Info().title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .version(swaggerProperties.getVersion())
                .license(new License().name(swaggerProperties.getLicense()))
                .contact(new Contact().name("刘颂陵").email("15879144378@163.com")))
                .components(new Components().addSecuritySchemes(loginToken, new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER)
                        .name(loginToken)))
                .addSecurityItem(new SecurityRequirement().addList(loginToken));
    }
}
