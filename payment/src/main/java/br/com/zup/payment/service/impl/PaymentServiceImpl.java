package br.com.zup.payment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import br.com.zup.payment.entity.Payment;
import br.com.zup.payment.event.PurchaseCreatedEvent;
import br.com.zup.payment.repository.PaymentRepository;
import br.com.zup.payment.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;
    private KafkaTemplate<String, PurchaseCreatedEvent> template;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, KafkaTemplate<String, PurchaseCreatedEvent> template) {
        this.paymentRepository = paymentRepository;
        this.template = template;
    }

	@Override
	public void bookedOrder(PurchaseCreatedEvent purchaseCreatedEvent) {

		String paymentId = this.paymentRepository.save(new Payment(null, purchaseCreatedEvent.getOrderEvent().getCustomerId(), purchaseCreatedEvent.getTotalAmount(), purchaseCreatedEvent.getQuantityPurchase())).getId();

		if("e081ad0f-f83e-4f81-b2d7-23655ac04b8a".equals(purchaseCreatedEvent.getOrderEvent().getCustomerId()))
		{
			purchaseCreatedEvent.setDescription(paymentId);
			this.template.send("success-payment-orders", purchaseCreatedEvent);
		}else
		{
			purchaseCreatedEvent.setDescription(paymentId);
			this.template.send("failure-payment", purchaseCreatedEvent);
		}
	}
}