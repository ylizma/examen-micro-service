package com.ylizma.accountservice.services;

import com.ylizma.accountservice.models.Operation;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class OperationProducer {

    private final KafkaTemplate<String, Operation> kafkaTemplate;
    private static final String TOPIC = "operations";


    public OperationProducer(KafkaTemplate<String, Operation> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void operationSupplier(Operation operation) {
        kafkaTemplate.send(TOPIC, operation);
    }
}
