package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import java.util.Arrays;
import java.util.Map;

@Getter
public class Payment {
    String id;
    String method;
    String status;
    Map<String, String> paymentData;
    Order order;

    public Payment(String id, String method, Map<String, String> paymentData, Order order) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.order = order;
        this.status = "PENDING";
    }

    public Payment(String id, String method, Map<String, String> paymentData, Order order, String status) {
        this(id, method, paymentData, order);
        this.setStatus(status);
    }

    public void setStatus(String status) {
        String[] validStatus = {"PENDING", "SUCCESS", "REJECTED"};
        if (Arrays.stream(validStatus).noneMatch(item -> item.equals(status))) {
            throw new IllegalArgumentException("Invalid payment status");
        }
        this.status = status;
    }
}