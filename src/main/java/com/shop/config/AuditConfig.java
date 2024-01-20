package com.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@Configuration //빈 정의할 떄 쓰임
@EnableJpaAuditing //JPA의 Auditing 기능을 활성화, 기본적으로 이것만 있어도 되지만 AuditorAare인터페이스를 확장한 AuditorAareImpl로 기능을 확장 시킨듯?
public class AuditConfig {

    @Bean
    public AuditorAware<String> auditorProvider(){
        return new AuditorAwareImpl();
    }
}
