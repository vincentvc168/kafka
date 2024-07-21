package io.conduktor.demos.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import java.util.Properties;

public class ProducerDemo {

    private static final Logger log = LoggerFactory.getLogger(ProducerDemo.class.getSimpleName());

    public static void main(String[] args) {
        log.info("I am Kafka Producer");

        //Create Properties
        Properties properties = new Properties();

        //Connect Local Kafka
        properties.setProperty("bootstrap.servers", "127.0.0.1:19092");

        //Serializer
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        //Create Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        //Create ProducerRecord
        ProducerRecord<String, String> producerrecord = new ProducerRecord<>("demo_java", "hello world!");

        //Send Data
        producer.send(producerrecord);

        //Tell producer to send and block until the end - synchronous
        producer.flush();

        //Flush and close producer
        producer.close();

    }
}
