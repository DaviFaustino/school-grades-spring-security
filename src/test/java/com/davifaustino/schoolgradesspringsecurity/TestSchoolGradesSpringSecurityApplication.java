package com.davifaustino.schoolgradesspringsecurity;

import org.springframework.boot.SpringApplication;

public class TestSchoolGradesSpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.from(SchoolGradesSpringSecurityApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
