package kz.aitu.assistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "kz.aitu.assistant.repository")
@EnableRedisRepositories(basePackages = "kz.aitu.assistant.repository.redis")
public class PersonalAssistantApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalAssistantApplication.class, args);
    }
} 