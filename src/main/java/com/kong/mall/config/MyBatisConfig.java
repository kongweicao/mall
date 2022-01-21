package com.kong.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.kong.mall.mapper")
public class MyBatisConfig {
}
