package hu.braininghub.spring6r;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.ObservationTextPublisher;
import io.micrometer.observation.aop.ObservedAspect;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
@EnableR2dbcRepositories(basePackages = {"hu.braininghub.spring6r"})
@EnableR2dbcAuditing
@EntityScan(basePackages = {"hu.braininghub.spring6r"})
public class ApplicationConfig {
    private static final Logger log = LoggerFactory.getLogger(ApplicationConfig.class);

    @Bean
    public Clock clock() {
        return Clock.system(ZoneId.of("Europe/Budapest"));
    }


//    @Bean
//    public ObservationRegistry observationRegistry(ObservationHandler<Observation.Context> observationTextPublisher) {
//        ObservationRegistry registry = ObservationRegistry.create();
//        registry.observationConfig()
//                .observationHandler(observationTextPublisher);
//        return registry;
//    }
    @Bean
    public ObservationHandler<Observation.Context> observationTextPublisher() {
        return new ObservationTextPublisher(log::info);
    }

    @Bean
    public ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        return new ObservedAspect(observationRegistry);
    }

}
