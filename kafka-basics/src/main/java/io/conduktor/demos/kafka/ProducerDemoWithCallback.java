package io.conduktor.demos.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoWithCallback {

    private static final Logger log = LoggerFactory.getLogger(ProducerDemoWithCallback.class.getSimpleName());

    public static void main(String[] args) {
        log.info("I am Kafka Producer");

        //Create Properties
        Properties properties = new Properties();

        //Connect Local Kafka
        properties.setProperty("bootstrap.servers", "127.0.0.1:19092");
        properties.setProperty("batch.size", "1");
        properties.setProperty("partitioner.class", RoundRobinPartitioner.class.getName());

        //Serializer
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        //Create Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        for (int i=0; i<30; i++) {
            //Create ProducerRecord
            ProducerRecord<String, String> producerrecord = new ProducerRecord<>("demo_java", "hello world!" + i);

            //Send Data
            producer.send(producerrecord, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e == null) {
                        log.info("Received Metadata: \n" +
                                "Topic: " + recordMetadata.topic() + "\n" +
                                "Partition: " + recordMetadata.partition() + "\n" +
                                "Offset: " + recordMetadata.offset() + "\n" +
                                "Timestamp: " + recordMetadata.timestamp() + "\n"
                        );
                    } else {
                        log.error("Error while producing", e);
                    }
                }
            });

        };

        //Tell producer to send and block until the end - synchronous
        producer.flush();

        //Flush and close producer
        producer.close();

    }
}
