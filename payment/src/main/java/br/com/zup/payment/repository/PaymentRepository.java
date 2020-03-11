package br.com.zup.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}
