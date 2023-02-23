package hu.braininghub.spring6r.dto;

import hu.braininghub.spring6r.entity.Enumeration;

import java.time.LocalDateTime;

public record Foo (
        Long id,
        String name,
        Enumeration enumeration,
        LocalDateTime created
) {}
