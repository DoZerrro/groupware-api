package com.dq.aquaranth.commons.config;


import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@Log4j2
@MapperScan(basePackages = {"com.dq.aquaranth.**.mapper"})
public class RootConfig {
}
