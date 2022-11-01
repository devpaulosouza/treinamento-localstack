package dev.paulosouza.productb2c.message;

import dev.paulosouza.productb2c.request.ProductRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class B2BProductConsumer {

    @SqsListener("${cloud.aws.sqs.product.queue}")
    public void receiveMessage(ProductRequest request) {
        log.info("B2C -  {}", request);
    }

}
