package com.shop.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${uploadPath}")
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){  // /images/**로 시작하는 URL에 접근하면 실제 서버의 파일 시스템에서 uploadPath에 해당하는 경로에 있는 파일을 찾아 응답하도록 해줍니다.
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);
    }


}
