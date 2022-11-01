package dev.paulosouza.productb2b.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.QueueMessageHandlerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver;

import java.util.Collections;
import java.util.Objects;

@Configuration
public class AWSConfig {

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.endpoint.uri}")
    private String url;

    @Bean
    @Primary
    public AmazonSQSAsync amazonSQSAsync() {
        AWSStaticCredentialsProvider credentials =
                new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));

        AmazonSQSAsyncClientBuilder amazonSQSAsyncClientBuilder = AmazonSQSAsyncClientBuilder
                .standard()
                .withCredentials(credentials);

        if (url != null) {
            AwsClientBuilder.EndpointConfiguration endpointConfiguration
                    = new AwsClientBuilder.EndpointConfiguration(url, region);

            amazonSQSAsyncClientBuilder.withEndpointConfiguration(endpointConfiguration);
        } else {
            amazonSQSAsyncClientBuilder.withRegion(region);
        }

        return amazonSQSAsyncClientBuilder.build();
    }

    @Bean
    public QueueMessageHandlerFactory queueMessageHandlerFactory() {
        QueueMessageHandlerFactory factory = new QueueMessageHandlerFactory();
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();

        messageConverter.setStrictContentTypeMatch(false);
        factory.setArgumentResolvers(Collections.singletonList(new PayloadMethodArgumentResolver(messageConverter)));
        return factory;
    }

}
