package org.acrighthere.order;

import io.restassured.RestAssured;
import org.acrighthere.order.stubs.InventoryClientStub;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;
import org.wiremock.spring.EnableWireMock;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock
@Testcontainers
class OrderServiceApplicationTests {
    @Container
    @ServiceConnection
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }
    @Test
    void shouldPlaceOrder() {
        String requestBody = """
                {
                    "skuCode":"iphone_15",
                    "price": 1000,
                    "quantity":1
                }
                """;
        InventoryClientStub.stubInventoryCall("iphone_15", 1);
        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("api/order")
                .then()
                .statusCode(201)
                .body("skuCode", Matchers.is("iphone_15"))
                .body("price", Matchers.is(1000))
                .body("quantity", Matchers.is(1));
    }

}
