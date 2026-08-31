package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:context-test-db",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.defer-datasource-initialization=true",
    "spring.sql.init.mode=always"
})

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

}
