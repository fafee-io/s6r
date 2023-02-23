package hu.braininghub.spring6r.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString(callSuper = true)
@Table(name = "demo_foo")
public class FooEntity extends BaseEntity {

    private String name;

    private Enumeration enumeration;

//    @OneToMany(mappedBy = "foo", fetch = FetchType.LAZY)
//    private Set<BarEntity> bars;

}
