package test.spring.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(
		classes = { DemoApplication.class},
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class DemoApplicationTests {

	@Autowired
	private WebTestClient client;

	@Test
	void test_actuatorInfo_shouldReturnUnauthorized() {
		client.get()
				.uri("/actuator/info")
				.exchange()
				.expectStatus().isUnauthorized();
	}

	@Test
	void test_actuatorHealth_shouldReturnOk() {
		client.get()
				.uri("/actuator/health")
				.exchange()
				.expectStatus().isOk();
	}

}
