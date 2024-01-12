package test.spring.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(
		classes = { DemoApplication.class},
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
// TODO: enabling this configuration will properly load application context and pass the tests
//@ContextConfiguration(classes = {NoneSecurityConfiguration.class})
@TestPropertySource("classpath:application-none.properties")
class NoneDemoApplicationTests {

	@Autowired
	private WebTestClient client;

	@Test
	void test_actuatorInfo_shouldReturnOk() {
		client.get()
				.uri("/actuator/info")
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	void test_actuatorHealth_shouldReturnOk() {
		client.get()
				.uri("/actuator/health")
				.exchange()
				.expectStatus().isOk();
	}

}
