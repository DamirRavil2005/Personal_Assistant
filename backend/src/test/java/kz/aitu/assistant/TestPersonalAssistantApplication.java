package kz.aitu.assistant;

import org.springframework.boot.SpringApplication;

public class TestPersonalAssistantApplication {

	public static void main(String[] args) {
		SpringApplication.from(PersonalAssistantApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
