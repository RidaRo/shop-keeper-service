#!/bin/sh

export AWS_ACCESS_KEY_ID=FAKE
export AWS_SECRET_ACCESS_KEY=FAKE

alias awsl='aws --endpoint-url=http://localstack:4566 --region us-east-1'

awsl sqs create-queue --queue-name item-queue.fifo --attributes FifoQueue=true
awsl sns create-topic --name item-updates
awsl sns subscribe --topic-arn arn:aws:sns:us-east-1:000000000000:item-updates \
                   --protocol sqs \
                   --notification-endpoint http://localhost:4566/000000000000/item-queue.fifo