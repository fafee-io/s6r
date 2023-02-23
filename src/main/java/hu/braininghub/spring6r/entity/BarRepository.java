package hu.braininghub.spring6r.entity;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BarRepository extends ReactiveCrudRepository<BarEntity, Long> {

    Flux<BarEntity> findAllByFooId(Long fooId);

}
