package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    Map<String, String> paymentData;
    Order order;

    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<>();
        this.paymentData.put("voucherCode", "ESHOP1234ABC5678");

        // Create a valid product list to satisfy Order validation
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("product-1");
        product.setProductName("Test Product");
        product.setProductQuantity(1);
        products.add(product);

        this.order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testCreatePaymentDefaultStatus() {
        Payment payment = new Payment("payment-1", "VOUCHER_CODE", this.paymentData, this.order);
        assertEquals("payment-1", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals(this.paymentData, payment.getPaymentData());
        assertEquals(this.order, payment.getOrder());
        assertEquals("PENDING", payment.getStatus());
    }

    @Test
    void testCreatePaymentWithStatus() {
        Payment payment = new Payment("payment-1", "VOUCHER_CODE", this.paymentData, this.order, "SUCCESS");
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("payment-1", "VOUCHER_CODE", this.paymentData, this.order, "INVALID");
        });
    }

    @Test
    void testSetStatusToSuccess() {
        Payment payment = new Payment("payment-1", "VOUCHER_CODE", this.paymentData, this.order);
        payment.setStatus("SUCCESS");
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testSetStatusToRejected() {
        Payment payment = new Payment("payment-1", "VOUCHER_CODE", this.paymentData, this.order);
        payment.setStatus("REJECTED");
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testSetStatusToInvalid() {
        Payment payment = new Payment("payment-1", "VOUCHER_CODE", this.paymentData, this.order);
        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("INVALID"));
    }
}