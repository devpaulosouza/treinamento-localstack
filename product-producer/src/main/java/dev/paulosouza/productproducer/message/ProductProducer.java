package dev.paulosouza.productproducer.message;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.paulosouza.productproducer.dto.request.Filter;
import dev.paulosouza.productproducer.dto.request.ProductRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ProductProducer {

    public static final String FILTER = "filter";

    @Value("${cloud.aws.sns.topic-arn}")
    private String topicArn;

    @Autowired
    private AmazonSNS snsClient;

    @Autowired
    private ObjectMapper mapper;

    public void send(Filter filter, ProductRequest request) throws JsonProcessingException {
        PublishRequest publishRequest = this.buildPublishRequest(filter, request);

        log.info(
                "Sending message to topic {}. payload = {}",
                this.topicArn,
                request
        );

        this.snsClient.publish(publishRequest);
    }

    private PublishRequest buildPublishRequest(Filter filter, ProductRequest request) throws JsonProcessingException {
        PublishRequest publishRequest = new PublishRequest(this.topicArn, mapper.writeValueAsString(request));

        publishRequest.setMessageAttributes(this.getMessageAttributes(filter));

        return publishRequest;
    }

    private Map<String, MessageAttributeValue> getMessageAttributes(Filter filter) {
        MessageAttributeValue messageAttributeValue = new MessageAttributeValue();
        Map<String, MessageAttributeValue> attributes = new HashMap<>();

        messageAttributeValue.setDataType("String.Array");
        messageAttributeValue.setStringValue("[\"" + filter.name().trim() + "\"]");

        attributes.put(FILTER, messageAttributeValue);

        return attributes;
    }

}
