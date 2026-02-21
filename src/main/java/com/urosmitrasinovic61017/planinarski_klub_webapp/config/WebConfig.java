package com.urosmitrasinovic61017.planinarski_klub_webapp.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;


@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Bean
//    public Validator validator (final AutowireCapableBeanFactory autowireCapableBeanFactory) {
//
//        ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
//                .configure().constraintValidatorFactory(new SpringConstraintValidatorFactory(autowireCapableBeanFactory))
//                .buildValidatorFactory();
//        Validator validator = validatorFactory.getValidator();
//
//        return validator;
//    }

//    @Bean(name = "multipartResolver")
//    public CommonsMultipartResolver multipartResolver() {
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        multipartResolver.setMaxUploadSize(100000);
//        return multipartResolver;
//    }


    //We need to expose the directory containing the uploaded files so the clients (web browsers) can access
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        exposeDirectory("slikeAranzmani", registry);
        exposeDirectory("vrstePutovanja", registry);
        exposeDirectory("slikeProizvodi", registry);
    }

    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        if (dirName.startsWith("../")) {
            dirName = dirName.replace("../", "");
        }

        registry.addResourceHandler("/" + dirName + "/**")
                .addResourceLocations("file:/"+ uploadPath + "/");
    }


}
