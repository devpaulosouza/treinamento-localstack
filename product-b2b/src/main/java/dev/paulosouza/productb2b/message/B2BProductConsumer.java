package dev.paulosouza.productb2b.message;

import dev.paulosouza.productb2b.dto.request.ProductRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class B2BProductConsumer {

    @SqsListener("${cloud.aws.sqs.product.queue}")
    public void receiveMessage(ProductRequest request) {
        log.info("B2B -  {}", request);
    }

}
