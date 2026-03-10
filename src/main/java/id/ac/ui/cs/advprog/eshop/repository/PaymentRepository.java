package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentRepository {

    public Payment save(Payment payment) {
        return null; // Skeleton to fail tests
    }

    public Payment findById(String id) {
        return null; // Skeleton to fail tests
    }

    public List<Payment> findAll() {
        return new ArrayList<>(); // Skeleton to fail tests
    }
}