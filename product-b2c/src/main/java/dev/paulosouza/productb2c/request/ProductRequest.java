package dev.paulosouza.productb2c.request;

import lombok.Data;

@Data
public class ProductRequest {

    private long id;

    private String sku;

    private String name;

}
