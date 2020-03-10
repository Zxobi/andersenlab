package com.andernsenlab.bookstore.paymentservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/payment")
@Slf4j
public class PaymentController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic.orderPaid}")
    private String orderPaidTopic;

    public PaymentController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public ResponseEntity<String> payOrder(@RequestParam Integer orderId) {
        try {
            SendResult<String, String> sendResult = kafkaTemplate.send(orderPaidTopic, orderId.toString()).get();
            log.info("Result sent:'{}'", sendResult);
        } catch (ExecutionException | InterruptedException ex) {
            log.error("Could not send kafka message:'{}'", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to post message");
        }

        return ResponseEntity.ok("MessageSent");
    }

}
