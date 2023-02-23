package hu.braininghub.spring6r;

import hu.braininghub.spring6r.dto.Bar;
import hu.braininghub.spring6r.dto.BarMapper;
import hu.braininghub.spring6r.dto.Calculation;
import hu.braininghub.spring6r.dto.Foo;
import hu.braininghub.spring6r.dto.FooBar;
import hu.braininghub.spring6r.dto.FooMapper;
import hu.braininghub.spring6r.entity.BarEntity;
import hu.braininghub.spring6r.entity.BarRepository;
import hu.braininghub.spring6r.entity.FooEntity;
import hu.braininghub.spring6r.entity.FooRepository;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.reactive.ServerHttpObservationFilter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Observed(name = "demo")
public class DemoService {

    private final FooRepository fooRepository;
    private final BarRepository barRepository;
    private final FooMapper fooMapper;
    private final BarMapper barMapper;
    private ObservationRegistry observationRegistry;

    public Mono<Long> create(Foo dto) {
        FooEntity entity = fooMapper.dtoToEntity(dto);
        Mono<FooEntity> mono = fooRepository.save(entity);

        return mono.map(FooEntity::getId);
    }

    public void update(Long id, Foo dto) {
        Mono<FooEntity> mono = fooRepository.findById(id);
        FooEntity entity = mono.block();
        fooMapper.updateFromDto(dto, entity);
        fooRepository.save(entity);
    }

    public Flux<Foo> list() {
        Flux<FooEntity> entities = fooRepository.findAll();
        return entities.map(fooMapper::entityToDto);
    }

    public Mono<FooBar> get(Long id) {
        Mono<FooEntity> monoFoo = fooRepository.findById(id);
        Mono<List<Bar>> monBars = barRepository.findAllByFooId(id).map(barMapper::entityToDto).collectList();

        return Mono.zip(monoFoo, monBars, (foo, bars) -> {
            FooBar response = new FooBar();
            response.setId(foo.getId());
            response.setEnumeration(foo.getEnumeration());
            response.setName(foo.getName());
            response.setBars(bars);

            return response;
        });
    }

    public Mono<Calculation> calculate(Long id) {
        Mono<FooEntity> mono = fooRepository.findById(id);
        FooEntity foo = mono.block();
        Flux<BarEntity> bars = barRepository.findAllByFooId(foo.getId());
        Double sum = bars.collectList().map(bar ->
                bar.stream().map(b -> Math.sqrt(
                b.getCoordsX() * b.getCoordsX() +
                b.getCoordsY() * b.getCoordsY() +
                b.getCoordsZ() * b.getCoordsZ()
        )).reduce(0.0d, Double::sum)).block();

        return Mono.just(new Calculation(sum / bars.count().block()));
    }


//    public Mono<FooBar> getObserved(Long id) {
////        ServerHttpObservationFilter
//        log.info("Observing..");
//        return Observation
//                .createNotStarted("demoService", observationRegistry)
//                .observe(() -> get(id));
//    }

}
