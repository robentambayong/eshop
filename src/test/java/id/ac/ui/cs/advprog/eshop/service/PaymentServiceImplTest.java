package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    Order order;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("product-1");
        product.setProductName("Test Product");
        product.setProductQuantity(1);
        products.add(product);

        order = new Order("order-1", products, 123456789L, "Safira");
    }

    @Test
    void testAddPaymentVoucherSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        doAnswer(invocation -> invocation.getArgument(0)).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "VOUCHER_CODE", paymentData);
        assertEquals("SUCCESS", result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentVoucherRejectedInvalidLength() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP123"); // Too short
        doAnswer(invocation -> invocation.getArgument(0)).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "VOUCHER_CODE", paymentData);
        assertEquals("REJECTED", result.getStatus());
    }

    @Test
    void testAddPaymentBankTransferSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123456");
        doAnswer(invocation -> invocation.getArgument(0)).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "BANK_TRANSFER", paymentData);
        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    void testAddPaymentBankTransferRejectedEmptyBankName() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", ""); // Empty string
        paymentData.put("referenceCode", "REF123456");
        doAnswer(invocation -> invocation.getArgument(0)).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "BANK_TRANSFER", paymentData);
        assertEquals("REJECTED", result.getStatus());
    }

    @Test
    void testSetStatusSuccessUpdatesOrder() {
        Map<String, String> paymentData = new HashMap<>();
        Payment payment = new Payment("payment-1", "VOUCHER_CODE", paymentData, order);

        doReturn(payment).when(paymentRepository).findById("payment-1");
        doAnswer(invocation -> invocation.getArgument(0)).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.setStatus(payment, "SUCCESS");
        assertEquals("SUCCESS", result.getStatus());
        assertEquals("SUCCESS", result.getOrder().getStatus());
    }

    @Test
    void testSetStatusRejectedUpdatesOrder() {
        Map<String, String> paymentData = new HashMap<>();
        Payment payment = new Payment("payment-1", "BANK_TRANSFER", paymentData, order);

        doReturn(payment).when(paymentRepository).findById("payment-1");
        doAnswer(invocation -> invocation.getArgument(0)).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.setStatus(payment, "REJECTED");
        assertEquals("REJECTED", result.getStatus());
        assertEquals("FAILED", result.getOrder().getStatus());
    }

    @Test
    void testGetPayment() {
        Payment payment = new Payment("payment-1", "BANK_TRANSFER", new HashMap<>(), order);
        doReturn(payment).when(paymentRepository).findById("payment-1");

        Payment result = paymentService.getPayment("payment-1");
        assertEquals("payment-1", result.getId());
    }

    @Test
    void testGetAllPayments() {
        List<Payment> paymentList = new ArrayList<>();
        doReturn(paymentList).when(paymentRepository).findAll();

        List<Payment> result = paymentService.getAllPayments();
        assertSame(paymentList, result);
    }
}