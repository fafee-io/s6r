package hu.braininghub.spring6r;

import hu.braininghub.spring6r.entity.BarEntity;
import hu.braininghub.spring6r.entity.BarRepository;
import hu.braininghub.spring6r.entity.CoordsEmbeddable;
import hu.braininghub.spring6r.entity.Enumeration;
import hu.braininghub.spring6r.entity.FooEntity;
import hu.braininghub.spring6r.entity.FooRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GeneratorService {

    private final SecureRandom random = new SecureRandom();
    private final FooRepository fooRepository;
    private final BarRepository barRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void fillDatabase() {
        if (fooRepository.count().block() < 100) {
            for (int i = 0; i < 50; ++i) {
                FooEntity fooEntity = new FooEntity();
                fooEntity.setName(generateLetters(24));
                fooEntity.setEnumeration(Enumeration.values()[nextInt(3)]);
                fooRepository.save(fooEntity).subscribe();
            }
            Flux<FooEntity> foos = fooRepository.findAll();
            foos.subscribe(foo -> {
//                System.out.println(foo);
                List<BarEntity> bars = new ArrayList<>();
                for (int j = 0; j < nextInt(100); ++j) {
                    BarEntity bar = new BarEntity();
                    bar.setIdentifier(generateLetters(10));
                    CoordsEmbeddable coords = new CoordsEmbeddable(nextDouble(100.0, 5000.0), nextDouble(100.0, 5000.0), nextDouble(100.0, 5000.0));
                    bar.setCoords(coords);
                    bar.setFooId(foo.getId());
                    bars.add(bar);
                }
                barRepository.saveAll(bars).subscribe();
            });
        }
    }

    public int nextInt(int bound) {
        return random.nextInt(bound);
    }

    public double nextDouble(double min, double max) {
        return random.nextDouble(min, max);
    }

    public String generateLetters(int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        String generatedString =
                random
                        .ints(leftLimit, rightLimit + 1)
                        .limit(length)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();

        return generatedString;
    }
}
