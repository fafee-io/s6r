package hu.braininghub.spring6r.entity;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FooRepository extends ReactiveCrudRepository<FooEntity, Long> {

    @Query("SELECT foo FROM FooEntity foo JOIN FETCH foo.bars")
    Optional<FooEntity> loadById(Long id);
}
