package co.com.bancolombia.model.order;


import lombok.Builder;

@Builder(toBuilder = true)
public record Client(String id, String name) {}
