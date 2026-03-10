package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product; // This was the missing import!
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    private Order mockOrder;

    @BeforeEach
    void setUp() {
        // Create a product list with at least one item to satisfy Order validation
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        mockOrder = new Order("order-123", products, 123456789L, "Safira");
    }

    @Test
    void testCreateOrderPage() throws Exception {
        mockMvc.perform(get("/order/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/createOrder"));
    }

    @Test
    void testCreateOrderPost() throws Exception {
        when(orderService.createOrder(any(Order.class))).thenReturn(mockOrder);

        mockMvc.perform(post("/order/create")
                        .param("author", "Safira"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/order/history"));
    }

    @Test
    void testHistoryOrderPage() throws Exception {
        mockMvc.perform(get("/order/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/historyOrder"));
    }

    @Test
    void testHistoryOrderPost() throws Exception {
        List<Order> orderList = new ArrayList<>();
        orderList.add(mockOrder);
        when(orderService.findAllByAuthor("Safira")).thenReturn(orderList);

        mockMvc.perform(post("/order/history")
                        .param("author", "Safira"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/listOrder"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attribute("author", "Safira"));
    }

    @Test
    void testPayOrderPage() throws Exception {
        when(orderService.findById("order-123")).thenReturn(mockOrder);

        mockMvc.perform(get("/order/pay/order-123"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/payOrder"))
                .andExpect(model().attributeExists("order"));
    }

    @Test
    void testPayOrderPost() throws Exception {
        mockMvc.perform(post("/order/pay/order-123")
                        .param("method", "VOUCHER_CODE")
                        .param("voucherCode", "ESHOP1234ABC5678"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/paymentResult"))
                .andExpect(model().attributeExists("paymentId"));
    }
}