package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        Payment payment = new Payment(UUID.randomUUID().toString(), method, paymentData, order);

        if ("VOUCHER_CODE".equals(method)) {
            String voucher = paymentData.get("voucherCode");
            boolean isValid = false;
            if (voucher != null && voucher.length() == 16 && voucher.startsWith("ESHOP")) {
                int numericCount = 0;
                for (char c : voucher.toCharArray()) {
                    if (Character.isDigit(c)) numericCount++;
                }
                if (numericCount == 8) {
                    isValid = true;
                }
            }
            payment.setStatus(isValid ? "SUCCESS" : "REJECTED");

        } else if ("BANK_TRANSFER".equals(method)) {
            String bankName = paymentData.get("bankName");
            String refCode = paymentData.get("referenceCode");

            boolean isValid = bankName != null && !bankName.trim().isEmpty() &&
                    refCode != null && !refCode.trim().isEmpty();

            payment.setStatus(isValid ? "SUCCESS" : "REJECTED");
        }

        return paymentRepository.save(payment);
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        payment.setStatus(status);

        if ("SUCCESS".equals(status)) {
            payment.getOrder().setStatus("SUCCESS");
        } else if ("REJECTED".equals(status)) {
            payment.getOrder().setStatus("FAILED");
        }

        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}