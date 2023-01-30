package com.microservicesdemo.updatesservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicesdemo.updatesservice.Employee;
import com.microservicesdemo.updatesservice.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @KafkaListener(topics = "app_updates", groupId = "groupId")
    public void consume(@Payload String employee) throws JsonProcessingException {
        if (AppUtils.validate(new ObjectMapper().readValue(employee, Employee.class))) {
            System.out.println(employee);
            kafkaTemplate.send("employee_updates", employee);
        } else {
            kafkaTemplate.send("employee_DLQ", employee);
        }
    }
}
