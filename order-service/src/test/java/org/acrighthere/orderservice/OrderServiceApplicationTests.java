package org.acrighthere.orderservice;

import org.acrighthere.orderservice.dto.OrderLineItemsDto;
import org.acrighthere.orderservice.dto.OrderRequest;
import org.acrighthere.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class OrderServiceApplicationTests {
    @Container
    static MySQLContainer mysql = new MySQLContainer("mysql:8.0");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrderRepository orderRepository;
    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @BeforeEach
    void cleanDb() {
        orderRepository.deleteAll();
    }
    @Test
    @DisplayName("Должен создать заказ")
    void shouldPlaceOrder() throws Exception {
        OrderRequest order = getOrderRequest();
        String orderRequestJson = objectMapper.writeValueAsString(order);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderRequestJson))
                .andExpect(status().isCreated());
        Assertions.assertEquals(1, orderRepository.count());
    }

    private OrderRequest getOrderRequest() {
        OrderLineItemsDto item1 = new OrderLineItemsDto();
        item1.setSkuCode("iphone_15");
        item1.setPrice(new BigDecimal("999.99"));
        item1.setQuantity(2);

        OrderLineItemsDto item2 = new OrderLineItemsDto();
        item2.setSkuCode("airpods_pro");
        item2.setPrice(new BigDecimal("249.99"));
        item2.setQuantity(1);

        OrderRequest order = new OrderRequest();
        order.setOrderLineItemsDtoList(List.of(item1, item2));

        return order;
    }



}
