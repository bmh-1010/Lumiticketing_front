package com.care.boot;

import org.mybatis.spring.annotation.MapperScan; // ✅ 이거 빠졌음!!
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@MapperScan("com.care.boot.ticket")  // ✅ TicketMapper 있는 패키지
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BootApplication.class);
    }
}
