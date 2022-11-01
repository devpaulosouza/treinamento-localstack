package dev.paulosouza.productb2b.dto.request;

import lombok.Data;

@Data
public class ProductRequest {

    private long id;

    private String sku;

    private String name;

}
