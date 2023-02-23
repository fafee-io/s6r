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
@Table(name = "demo_bar")
public class BarEntity extends BaseEntity {

    private String identifier;

    private Double coordsX;
    private Double coordsY;
    private Double coordsZ;

//    @ManyToOne
//    @JoinColumn(name = "foo_id")
    private Long fooId;

    public CoordsEmbeddable getCoords() {
        return CoordsEmbeddable.builder().x(coordsX).y(coordsY).z(coordsZ).build();
    }

    public void setCoords(CoordsEmbeddable coords) {
        this.coordsX = coords.getX();
        this.coordsY = coords.getY();
        this.coordsZ = coords.getZ();
    }
}
