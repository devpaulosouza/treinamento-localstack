#!/usr/bin/env bash

export SQS_ENDPOINT=http://localhost:4566

aws --endpoint-url $SQS_ENDPOINT sqs create-queue --queue-name b2c-products --region us-east-1
aws --endpoint-url $SQS_ENDPOINT sns create-topic --name products --region us-east-1

SUBSCRIPTION_ARN=$(aws --endpoint-url $SQS_ENDPOINT sns subscribe \
   --topic-arn arn:aws:sns:us-east-1:000000000000:products\
   --protocol sqs \
   --attributes=RawMessageDelivery=true \
   --notification-endpoint http://localstack:4566/000000000000/b2c-products\
   | grep -o -P '(?<="SubscriptionArn": ").*(?=")')

aws --endpoint-url $SQS_ENDPOINT sns set-subscription-attributes \
   --subscription-arn $SUBSCRIPTION_ARN \
   --attribute-name FilterPolicy \
   --attribute-value '{ "filter": [ "B2C", "ALL" ] }'


aws --endpoint-url $SQS_ENDPOINT sqs create-queue --queue-name b2b-products --region us-east-1

SUBSCRIPTION_ARN=$(aws --endpoint-url $SQS_ENDPOINT sns subscribe \
   --topic-arn arn:aws:sns:us-east-1:000000000000:products\
   --protocol sqs \
   --attributes=RawMessageDelivery=true \
   --notification-endpoint http://localstack:4566/000000000000/b2b-products\
   | grep -o -P '(?<="SubscriptionArn": ").*(?=")')
aws --endpoint-url $SQS_ENDPOINT sns set-subscription-attributes \
   --subscription-arn $SUBSCRIPTION_ARN \
   --attribute-name FilterPolicy \
   --attribute-value '{ "filter": [ "B2B", "ALL" ] }'

