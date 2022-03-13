#!/bin/sh

export AWS_ACCESS_KEY_ID=FAKE
export AWS_SECRET_ACCESS_KEY=FAKE

alias awsl='aws --endpoint-url=http://localstack:4566 --region us-east-1'

awsl sns create-topic --name item-updates