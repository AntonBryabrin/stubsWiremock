package com.example.stubs_Wiremock;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest
class StubsWiremockApplicationTests extends BaseHooks{

	@Test
	void singleUser() {
				Response response = given()
				.contentType(ContentType.JSON)
				.when()
				.get("http://localhost:5050/api/users/2")
				.then()
				.extract().response();

		Assertions.assertEquals(200, response.statusCode());
		System.out.println(response.getBody().prettyPrint());
		Assertions.assertEquals("Janet", response.jsonPath().getString("data.first_name"));
		Assertions.assertEquals("Weaver", response.jsonPath().getString("data.last_name"));
	}

	@Test
	void userNotFound() {
		Response response = given()
				.contentType(ContentType.JSON)
				.when()
				.get("http://localhost:5050/api/users?page=2")
				.then()
				.extract().response();

		Assertions.assertEquals(404, response.statusCode());
		System.out.println(response.getBody().prettyPrint());

	}

	@Test
	void singleResource() {
		Response response = given()
				.contentType(ContentType.JSON)
				.when()
				.get("http://localhost:5050/api/unknown/2")
				.then()
				.extract().response();

		Assertions.assertEquals(200, response.statusCode());
		Assertions.assertEquals("fuchsia rose", response.jsonPath().getString("data.name"));
		Assertions.assertEquals("#C74375", response.jsonPath().getString("data.color"));
		System.out.println(response.getBody().prettyPrint());

	}

	@Test
	void createUser() {

		Response response = given()
				.contentType(ContentType.JSON)
				.when()
				.body("{\n" +
						"    \"name\": \"morpheus\",\n" +
						"    \"job\": \"leader\"\n" +
						"}")
				.post("http://localhost:5050/api/users")
				.then()
				.extract().response();

		Assertions.assertEquals(201, response.statusCode());
		System.out.println(response.getBody().prettyPrint());
		Assertions.assertEquals("morpheus", response.jsonPath().getString("name"));
		Assertions.assertEquals("leader", response.jsonPath().getString("job"));


	}

}


