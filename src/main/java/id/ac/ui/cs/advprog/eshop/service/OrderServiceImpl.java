package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public Order createOrder(Order order) {
        return null; // Skeleton return to fail the test
    }

    @Override
    public Order updateStatus(String orderId, String status) {
        return null; // Skeleton return to fail the test
    }

    @Override
    public Order findById(String orderId) {
        return null; // Skeleton return to fail the test
    }

    @Override
    public List<Order> findAllByAuthor(String author) {
        return new ArrayList<>(); // Skeleton return to fail the test
    }
}