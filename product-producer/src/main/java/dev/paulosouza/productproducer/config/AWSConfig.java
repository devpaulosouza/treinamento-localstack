package dev.paulosouza.productproducer.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public AmazonSNS amazonSNS() {
        AWSStaticCredentialsProvider credentials =
                new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));

        AmazonSNSClientBuilder amazonSNSClientBuilder = AmazonSNSClientBuilder.standard()
                .withCredentials(credentials);

        if (url != null) {
            AwsClientBuilder.EndpointConfiguration endpointConfiguration
                    = new AwsClientBuilder.EndpointConfiguration(url, region);
            amazonSNSClientBuilder.withEndpointConfiguration(endpointConfiguration);
        } else {
            amazonSNSClientBuilder.withRegion(region);
        }

        return amazonSNSClientBuilder.build();
    }

}
