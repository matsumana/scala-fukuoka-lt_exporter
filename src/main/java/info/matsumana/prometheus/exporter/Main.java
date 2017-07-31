package info.matsumana.prometheus.exporter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.matsumana.prometheus.exporter.collector.KafkaConsumeWorker;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) {
        UndertowJaxrsServer server = new UndertowJaxrsServer().start();
        server.deploy(MyApp.class);

        // start KafkaConsumer
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(KafkaConsumeWorker.getInstance());

        log.info("exporter started.");
    }
}
