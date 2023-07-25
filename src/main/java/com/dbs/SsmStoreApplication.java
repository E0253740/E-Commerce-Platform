package com.dbs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
// MapperScan注解 指定当前项目中Mapper接口路径的位置，项目启动时会自动加载所有的接口
@MapperScan("com.dbs.mapper")
@Configuration
public class SsmStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsmStoreApplication.class, args);
    }

    @Bean
    public MultipartConfigElement getMultipartConfigElement() {

        MultipartConfigFactory factory = new MultipartConfigFactory();

        factory.setMaxFileSize(DataSize.of(10, DataUnit.MEGABYTES));
        factory.setMaxRequestSize(DataSize.of(15, DataUnit.MEGABYTES));

        return factory.createMultipartConfig();
    }
}
