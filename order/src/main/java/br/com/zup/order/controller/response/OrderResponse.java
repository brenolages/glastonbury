package br.com.zup.order.controller.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.zup.order.entity.Order;

public class OrderResponse {

    private String id;

    private String customerId;

    private BigDecimal amount;

    private List<OrderItemResponse> items;

    private String status;

    private String timeStr;

    public OrderResponse() {
    }

    public OrderResponse(String id, String customerId, BigDecimal amount, List<OrderItemResponse> items, String status, String timeStr) {
        this.id = id;
        this.customerId = customerId;
        this.amount = amount;
        this.items = items;
        this.status = status;
        this.timeStr = timeStr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static OrderResponse fromEntity(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getAmount(),
                order.getItems().stream().map(OrderItemResponse::fromEntity).collect(Collectors.toList()),
                order.getStatus(),
                dateAt(order.getTime())
        );
    }

    private static String dateAt(LocalDateTime date) {
		final DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
				.appendPattern("dd ")
				.appendText(ChronoField.MONTH_OF_YEAR, MONTH_ABBREVIATIONS_UPPERCASE)
				.appendPattern(" yyyy").appendPattern(" - 'ÀS' HH:mm:ss")
				.toFormatter(new Locale("pt", "BR"));
		return date.format(formatter);
	}

 	private static  Map<Long, String> MONTH_ABBREVIATIONS_UPPERCASE = Stream.of(Month.values())
 			.collect(Collectors.toMap(m -> Long.valueOf(m.getValue()), m -> {
 				String abbrev = m.getDisplayName(TextStyle.SHORT, new Locale("pt", "BR"));
 				return abbrev.toUpperCase(new Locale("pt", "BR"));
 			}));

	public String getTimeStr() {
		return timeStr;
	}

	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}
}
