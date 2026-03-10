package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/admin/list")
    public String listPage(Model model) {
        List<Payment> payments = paymentService.getAllPayments();
        model.addAttribute("payments", payments);
        return "payment/listPayment";
    }

    @GetMapping("/admin/detail/{paymentId}")
    public String adminDetail(@PathVariable String paymentId, Model model) {
        Payment payment = paymentService.getPayment(paymentId);
        model.addAttribute("payment", payment);
        return "payment/adminDetail";
    }

    @PostMapping("/admin/set-status/{paymentId}")
    public String setStatus(@PathVariable String paymentId, @RequestParam String status) {
        Payment payment = paymentService.getPayment(paymentId);
        paymentService.setStatus(payment, status);
        return "redirect:/payment/admin/list";
    }

    @GetMapping("/detail")
    public String detailForm() {
        return "payment/detailForm";
    }

    @GetMapping("/detail/{paymentId}")
    public String userDetail(@PathVariable String paymentId, Model model) {
        Payment payment = paymentService.getPayment(paymentId);
        model.addAttribute("payment", payment);
        return "payment/userDetail";
    }
}