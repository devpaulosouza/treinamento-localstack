package dev.paulosouza.productproducer.dto.request;

import lombok.Data;

@Data
public class ProductRequest {

    private long id;

    private String sku;

    private String name;

}
