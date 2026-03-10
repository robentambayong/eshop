package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    OrderRepository orderRepository;

    List<Order> orders;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        orders = new ArrayList<>();
        Order order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
        orders.add(order1);
        Order order2 = new Order("7f9e15bb-4b15-42f4-aebc-c3af385fb078",
                products, 1708570000L, "Safira Sudrajat");
        orders.add(order2);
    }

    @Test
    void testCreateOrder() {
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).save(order);
        Order result = orderService.createOrder(order);
        verify(orderRepository, times(1)).save(order);
        assertEquals(order.getId(), result.getId());
    }

    @Test
    void testCreateOrderIfAlreadyExists() {
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(order.getId());
        assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(order));
    }

    @Test
    void testUpdateStatus() {
        Order order = orders.get(0);
        Order newOrder = new Order(order.getId(), order.getProducts(), order.getOrderTime(), order.getAuthor(), "SUCCESS");
        doReturn(order).when(orderRepository).findById(order.getId());
        doReturn(newOrder).when(orderRepository).save(any(Order.class));

        Order result = orderService.updateStatus(order.getId(), "SUCCESS");
        assertEquals(order.getId(), result.getId());
        assertEquals("SUCCESS", result.getStatus());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testUpdateStatusInvalidStatus() {
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(order.getId());
        assertThrows(IllegalArgumentException.class,
                () -> orderService.updateStatus(order.getId(), "MEOW"));
    }

    @Test
    void testUpdateStatusInvalidOrderId() {
        doReturn(null).when(orderRepository).findById("invalid-id");
        assertThrows(NoSuchElementException.class,
                () -> orderService.updateStatus("invalid-id", "SUCCESS"));
    }

    @Test
    void testFindByIdIfIdFound() {
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(order.getId());
        Order result = orderService.findById(order.getId());
        assertEquals(order.getId(), result.getId());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        doReturn(null).when(orderRepository).findById("invalid-id");
        Order result = orderService.findById("invalid-id");
        assertNull(result);
    }

    @Test
    void testFindAllByAuthorIfAuthorCorrect() {
        doReturn(orders).when(orderRepository).findAllByAuthor("Safira Sudrajat");
        List<Order> result = orderService.findAllByAuthor("Safira Sudrajat");
        assertEquals(2, result.size());
    }

    @Test
    void testFindAllByAuthorIfAuthorAllLowercase() {
        doReturn(new ArrayList<Order>()).when(orderRepository).findAllByAuthor("safira sudrajat");
        List<Order> result = orderService.findAllByAuthor("safira sudrajat");
        assertTrue(result.isEmpty());
    }
}