package dev.paulosouza.productproducer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.paulosouza.productproducer.dto.request.Filter;
import dev.paulosouza.productproducer.dto.request.ProductRequest;
import dev.paulosouza.productproducer.message.ProductProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class ProductController {

    @Autowired
    private ProductProducer productProducer;

    @PostMapping
    public ResponseEntity<Void> create(
            @RequestParam Filter filter,
            @RequestBody ProductRequest request
    ) throws JsonProcessingException {
        this.productProducer.send(filter, request);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
