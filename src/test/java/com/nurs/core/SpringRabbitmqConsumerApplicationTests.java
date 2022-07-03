package com.nurs.core;

import com.nurs.core.dao.OrderRepository;
import com.nurs.core.dao.PaymentRepository;
import com.nurs.core.dto.OrderRequest;
import com.nurs.core.entity.Order;
import lombok.extern.slf4j.Slf4j;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringRabbitmqConsumerApplicationTests {

	@Autowired
	private WebTestClient webClient;

	private static MockWebServer mockWebServer;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@DynamicPropertySource
	static void registerProperties(DynamicPropertyRegistry registry) {
		registry.add("exchange-rate-api.base-url", () -> mockWebServer.url("/").url().toString());
	}

	@BeforeAll
	static void setupMockWebServer() throws IOException {
		mockWebServer = new MockWebServer();
		mockWebServer.start();
	}
	@AfterEach
	void deleteEntities() {
		paymentRepository.deleteAll();
		orderRepository.deleteAll();
	}
	@Test
	void createOrder() {
		BigDecimal amount = new BigDecimal("1000.00");
		String email = "n.suleev@yandex.ru";
		OrderRequest orderRequest = new OrderRequest(amount,email);
		webClient.post().uri("localhost:8000/order")
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(orderRequest), OrderRequest.class)
				.exchange()
				.expectStatus().isOk();
		Order actualOrder = orderRepository.findByEmail(email);
		assertThat(email).isEqualTo(actualOrder.getEmail());
		assertThat(amount).isEqualTo(actualOrder.getAmount());
	}

	@Test
	void deleteOrder() {
		createOrder();
		int id = 1;
		webClient.delete().uri("localhost:8000/order/" + id )
				.exchange()
				.expectStatus().isOk();
		Long orderId = 1L;
		Order actualOrder = orderRepository.findById(orderId).orElse(null);
		assertThat(actualOrder).isEqualTo(null);
	}



}
