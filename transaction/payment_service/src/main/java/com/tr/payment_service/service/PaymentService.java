package com.tr.payment_service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tr.payment_service.model.Payment;
import com.tr.payment_service.model.PaymentStatus;
import com.tr.payment_service.repository.PaymentRepository;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(String id) {
        return paymentRepository.findById(id).orElse(null);
    }

    public Payment createPayment(Payment payment) {

        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaidAt(null);

        return paymentRepository.save(payment);
    }

    public Payment updatePayment(String id, Payment payment) {

        Payment existing = paymentRepository.findById(id).orElse(null);

        if (existing == null) {
            return null;
        }

        existing.setStatus(payment.getStatus());

        if (payment.getStatus() == PaymentStatus.PAID) {
            existing.setPaidAt(LocalDateTime.now());
        }

        return paymentRepository.save(existing);
    }

    public void deletePayment(String id) {
        paymentRepository.deleteById(id);
    }
}
