package info.matsumana.prometheus.exporter.collector;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

public class KafkaConsumeWorker implements Runnable {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ObjectReader reader = new ObjectMapper()
            .readerFor(new TypeReference<Map<String, Integer>>() {});

    private static KafkaConsumeWorker singleton = new KafkaConsumeWorker();
    private int count;

    // constructor
    private KafkaConsumeWorker() {
    }

    // getter
    public int getCount() {
        return count;
    }

    // methods
    public static KafkaConsumeWorker getInstance() {
        return singleton;
    }

    @Override
    public void run() {
        try {
            KafkaConsumer<String, String> consumer = createKafkaConsumer();
            consumer.subscribe(Collections.singletonList("twitterAggregated"));

            while (true) {
                // milliseconds
                ConsumerRecords<String, String> records = consumer.poll(1000);

                for (ConsumerRecord<String, String> record : records) {
                    String value = record.value();

                    log.debug("Thread: [{}] Offset: [{}] Consumed message: [key={} value={}]",
                              Thread.currentThread().getName(), record.offset(), record.key(), value);

                    Map<String, Integer> row = reader.readValue(value);
                    count = row.get("count");
                }
            }
        } catch (Exception e) {
            // ignore
            count = 0;
            log.error("exception = {}", e);
        }
    }

    private KafkaConsumer<String, String> createKafkaConsumer() {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                               "localhost:9092");
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG,
                               "scala-fukuoka-2017-exporter");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                               "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                               "org.apache.kafka.common.serialization.StringDeserializer");

        return new KafkaConsumer<>(properties);
    }
}
