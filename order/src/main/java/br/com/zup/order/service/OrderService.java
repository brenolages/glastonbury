package br.com.zup.order.service;

import java.util.List;

import br.com.zup.order.controller.request.CreateOrderRequest;
import br.com.zup.order.controller.response.OrderResponse;
import br.com.zup.order.event.PurchaseCreatedEvent;

public interface OrderService {

    String save(CreateOrderRequest request);

    List<OrderResponse> findAll();

    void successPaymentOrders(PurchaseCreatedEvent purchaseCreatedEvent);

}
