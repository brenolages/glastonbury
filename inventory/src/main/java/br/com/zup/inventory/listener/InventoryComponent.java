package br.com.zup.inventory.listener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.zup.inventory.event.OrderCreatedEvent;
import br.com.zup.inventory.event.PurchaseCreatedEvent;
import br.com.zup.inventory.service.InventoryService;

@Configuration
public class InventoryComponent {

    private InventoryService inventoryService;
    private ObjectMapper objectMapper;

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrap;

    @Autowired
    public InventoryComponent(InventoryService inventoryService, ObjectMapper objectMapper) {
        this.inventoryService = inventoryService;
        this.objectMapper = objectMapper;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "inventory-group-id");
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @KafkaListener(topics = "created-orders", groupId = "inventory-group-id")
    public void listen(String message) throws IOException {
        OrderCreatedEvent event = this.objectMapper.readValue(message, OrderCreatedEvent.class);
        System.out.println("Received event: " + event.getCustomerId());

        this.inventoryService.purchaseOrder(event);
    }

    @KafkaListener(topics = "failure-payment", groupId = "inventory-group-id")
    public void listenPayment(String message) throws IOException {
    	PurchaseCreatedEvent event = this.objectMapper.readValue(message, PurchaseCreatedEvent.class);
        System.out.println("Payment Failure Received event: " + event.getOrderEvent().getCustomerId());
        this.inventoryService.failurePayment(event);
    }

    @KafkaListener(topics = "success-payment-orders", groupId = "inventory-group-id")
    public void sucessPayment(String message) throws IOException {
    	PurchaseCreatedEvent event = this.objectMapper.readValue(message, PurchaseCreatedEvent.class);
        System.out.println("Payment Success Received event: " + event.getOrderEvent().getCustomerId());
        this.inventoryService.successPaymentOrders(event);
    }
}
