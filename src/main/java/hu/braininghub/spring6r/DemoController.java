package hu.braininghub.spring6r;

import hu.braininghub.spring6r.dto.Calculation;
import hu.braininghub.spring6r.dto.Foo;
import hu.braininghub.spring6r.dto.FooBar;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/things")
@RequiredArgsConstructor
@Slf4j
public class DemoController {

    private final DemoService demoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<URI> create(Foo foo) {
        return demoService.create(foo).map(id -> URI.create("/things/".concat(String.valueOf(id))));
    }

    @PostMapping("/calculate")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Calculation> expensive(@RequestParam Long id) {
        return demoService.calculate(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id, @RequestBody Foo foo) {
        demoService.update(id, foo);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<Foo> list() {
        return demoService.list();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<FooBar> get(@PathVariable Long id) {
        return demoService.get(id);
    }


//    @GetMapping("/observed/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Mono<FooBar> getObserved(@PathVariable Long id) {
//        return demoService.getObserved(id);
//    }
}
