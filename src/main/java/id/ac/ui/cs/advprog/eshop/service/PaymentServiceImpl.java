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
            payment.setStatus(validateVoucher(paymentData) ? "SUCCESS" : "REJECTED");
        } else if ("BANK_TRANSFER".equals(method)) {
            payment.setStatus(validateBankTransfer(paymentData) ? "SUCCESS" : "REJECTED");
        }

        return paymentRepository.save(payment);
    }

    private boolean validateVoucher(Map<String, String> paymentData) {
        String voucher = paymentData.get("voucherCode");
        if (voucher == null || voucher.length() != 16 || !voucher.startsWith("ESHOP")) {
            return false;
        }

        long numericCount = voucher.chars().filter(Character::isDigit).count();
        return numericCount == 8;
    }

    private boolean validateBankTransfer(Map<String, String> paymentData) {
        String bankName = paymentData.get("bankName");
        String refCode = paymentData.get("referenceCode");

        return bankName != null && !bankName.trim().isEmpty() &&
                refCode != null && !refCode.trim().isEmpty();
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