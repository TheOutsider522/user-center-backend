package com.song.usercenter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * 自定义Swagger接口文档的配置
 * @author TheOutsider
 */
@Configuration
@EnableSwagger2WebMvc
@Profile("dev")
public class SwaggerConfig {

    /**
     * 创建API
     */
    @Bean(value = "defaultApi2")
    public Docket createRestApi() {   // 创建Docket对象
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 标注控制器的位置 com.song.usercenter.controller
                .apis(RequestHandlerSelectors.basePackage("com.song.usercenter.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 添加摘要信息 api信息
     */
    private ApiInfo apiInfo() {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                // 标题
                .title("用户管理中心")
                // 描述
                .description("用户管理中心接口文档")
                .termsOfServiceUrl("")
                // 作者信息
                .contact(new Contact("宋学亮", null, null))
                // 版本
                .version("版本号:1.0" )
                .build();
    }
}
