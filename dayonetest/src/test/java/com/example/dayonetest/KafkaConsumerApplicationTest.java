package com.example.dayonetest;

import static org.junit.jupiter.api.Assertions.*;

import com.example.dayonetest.service.KafkaConsumerService;
import com.example.dayonetest.service.KafkaProducerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class KafkaConsumerApplicationTest extends IntegrationTest{

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @MockBean //스프링 빈상태의 객체를 모킹
    private KafkaConsumerService kafkaConsumerService;

    @Test
    public void kafkaSendAndConsumeTest() {
        //given
        String topic = "test-topic";
        String expectValue = "expect-value";

        //when
        kafkaProducerService.send(topic, expectValue);

        //then
        var stringCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(kafkaConsumerService, Mockito.timeout(5000).times(1))
                .process(stringCaptor.capture());

        Assertions.assertEquals(expectValue, stringCaptor.getValue());
    }

}