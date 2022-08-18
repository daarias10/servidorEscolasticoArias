package ec.edu.espe.servidorescolasticoarias.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "paralelo")
@TypeAlias("paralelo")
@CompoundIndex(name = "idxco_paralelo_nivelletra", def = "{'nivel': 1, 'paralelo': 1}")
public class Paralelo {
  @Id private String codigo;

  @Indexed(name = "idx_paralelo_codigoInterno", unique = true)
  private String codigoInterno;

  @Indexed(name = "idx_paralelo_nivel", unique = false)
  private Integer nivel;

  @Indexed(name = "idx_paralelo_paraleloNum", unique = false)
  private Integer paraleloNum;

  private List<Estudiante> estudiantes;

  private Integer conteoEstudiantes;
}
