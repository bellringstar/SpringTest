package com.example.dayonetest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class KafkaConsumerService {

    public void process(String message) {
        System.out.println("processing..." + message);
    }

}
