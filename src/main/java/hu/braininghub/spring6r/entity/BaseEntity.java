package hu.braininghub.spring6r.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {

    @Id
    private Long id;
    @Version
    private Long version;
    @CreatedDate
    private LocalDateTime created;
}
