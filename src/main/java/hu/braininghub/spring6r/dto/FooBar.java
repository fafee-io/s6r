package hu.braininghub.spring6r.dto;

import hu.braininghub.spring6r.entity.Enumeration;
import lombok.Data;

import java.util.List;

@Data
public class FooBar {

    private Long id;
    private String name;
    private Enumeration enumeration;
    private List<Bar> bars;

}
