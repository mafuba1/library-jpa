package ru.nasrulaev.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("ru.nasrulaev.spring")
@EnableWebMvc
@EnableTransactionManagement
@EnableJpaRepositories("ru.nasrulaev.spring.repositories")
public class SpringConfig implements WebMvcConfigurer {
}
