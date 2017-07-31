package info.matsumana.prometheus.exporter;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import info.matsumana.prometheus.exporter.resource.MetricsResource;
import info.matsumana.prometheus.exporter.resource.RootResource;

@ApplicationPath("/")
public class MyApp extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> classes = new HashSet<>();

        // JAX-RS resources
        classes.add(RootResource.class);
        classes.add(MetricsResource.class);

        return classes;
    }
}
