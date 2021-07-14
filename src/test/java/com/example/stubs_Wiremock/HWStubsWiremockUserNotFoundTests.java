package com.example.stubs_Wiremock;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@SpringBootTest
class HWStubsWiremockUserNotFoundTests {

	private static WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.options().port(5050));

	@BeforeAll
	public static  void setUpMockServer() {
		wireMockServer.start();

		WireMock.configureFor("localhost", 5050);
		WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/api/users?page=2"))
				.willReturn(WireMock.aResponse()
						.withStatus(404)
						.withBody("{}")));

	}

	@Test
	void userNotFound() {
		Response response = given()
				.contentType(ContentType.JSON)
				.when()
//				.get("https://reqres.in/api/users/2")
				.get("http://localhost:5050/api/users?page=2")
				.then()
				.extract().response();

		Assertions.assertEquals(404, response.statusCode());
		System.out.println(response.getBody().prettyPrint());

	}

	@AfterAll
		public static void tearDownMockServer() {
			wireMockServer.stop();
		}


	}


