package com.poscodx.mysite.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.poscodx.mysite.config.app.DBConfig;
import com.poscodx.mysite.config.app.MyBatisConfig;

@Configuration
@Import({DBConfig.class,MyBatisConfig.class})
public class AppConfig {

}
