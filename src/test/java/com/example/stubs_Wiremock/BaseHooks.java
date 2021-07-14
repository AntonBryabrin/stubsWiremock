package com.example.stubs_Wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;

public class BaseHooks {
    private static WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.options().port(5050));

    @BeforeAll
    public static  void setUpMockServer() {
        wireMockServer.start();

        WireMock.configureFor("localhost", 5050);
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/api/users/2"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withBody("{\n" +
                                "    \"data\": {\n" +
                                "        \"id\": 2,\n" +
                                "        \"email\": \"janet.weaver@reqres.in\",\n" +
                                "        \"first_name\": \"Janet\",\n" +
                                "        \"last_name\": \"Weaver\",\n" +
                                "        \"avatar\": \"https://reqres.in/img/faces/2-image.jpg\"\n" +
                                "    },\n" +
                                "    \"support\": {\n" +
                                "        \"url\": \"https://reqres.in/#support-heading\",\n" +
                                "        \"text\": \"To keep ReqRes free, contributions towards server costs are appreciated!\"\n" +
                                "    }\n" +
                                "}")));

        WireMock.configureFor("localhost", 5050);
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/api/users?page=2"))
                .willReturn(WireMock.aResponse()
                        .withStatus(404)
                        .withBody("{}")));

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/api/unknown/2"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withBody("{\n" +
                                "    \"data\": {\n" +
                                "        \"id\": 2,\n" +
                                "        \"name\": \"fuchsia rose\",\n" +
                                "        \"year\": 2001,\n" +
                                "        \"color\": \"#C74375\",\n" +
                                "        \"pantone_value\": \"17-2031\"\n" +
                                "    },\n" +
                                "    \"support\": {\n" +
                                "        \"url\": \"https://reqres.in/#support-heading\",\n" +
                                "        \"text\": \"To keep ReqRes free, contributions towards server costs are appreciated!\"\n" +
                                "    }\n" +
                                "}")));

        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/api/users"))
                .withRequestBody(equalToJson("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}"))
                .willReturn(WireMock.aResponse()
                        .withStatus(201)
                        .withBody("{\n" +
                                "    \"name\": \"morpheus\",\n" +
                                "    \"job\": \"leader\",\n" +
                                "}")
                )
        );
    }

    @AfterAll
    public static void tearDownMockServer() {
        wireMockServer.stop();
    }


}




