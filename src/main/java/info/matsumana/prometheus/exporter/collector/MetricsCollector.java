package info.matsumana.prometheus.exporter.collector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.prometheus.client.Collector;
import io.prometheus.client.Collector.MetricFamilySamples.Sample;

public class MetricsCollector extends Collector {

    private static final String NAMESPACE = "scala_fukuoka";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public List<MetricFamilySamples> collect() {
        log.debug("MetricsCollector#collect");

        List<MetricFamilySamples> metrics = new ArrayList<>();

        // labels
        List<String> labelNames = Collections.emptyList();
        List<String> labelValues = Collections.emptyList();

        double metric = KafkaConsumeWorker.getInstance().getCount();

        List<MetricFamilySamples.Sample> samples = Collections.singletonList(
                new Sample(NAMESPACE + "_tweets", labelNames, labelValues, metric));
        metrics.add(new MetricFamilySamples(NAMESPACE + "_tweets", Type.GAUGE, "", samples));

        return metrics;
    }
}
