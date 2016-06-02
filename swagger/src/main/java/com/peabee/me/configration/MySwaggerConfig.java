package com.peabee.me.configration;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by pengbo on 16-4-28.
 */
@Configuration
@EnableSwagger
@EnableWebMvc
public class MySwaggerConfig {

    private SpringSwaggerConfig springSwaggerConfig;
    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    @Bean
    public SwaggerSpringMvcPlugin customImplementation()
    {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(apiInfo()).includePatterns(
                ".*?");
    }

    private ApiInfo apiInfo(){
        ApiInfo apiInfo = new ApiInfo(
                "Swagger restfull api test",
                "Swagger restfull api test",
                "Swagger restfull api test",
                "pengbowo@126.com",
                "My Apps API Licence Type",
                "My Apps API License URL");
        return apiInfo;
    }
}
