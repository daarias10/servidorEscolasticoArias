package ec.edu.espe.servidorescolasticoarias.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "estudiante")
@TypeAlias("estudiante")
public class Estudiante {
  @Id private String codigo;

  @Indexed(name = "idx_estudiante_codigoInterno", unique = true)
  private String codigoInterno;

  @Indexed(name = "idx_estudiante_cedula", unique = true)
  private String cedula;

  private String apellidos;

  private String nombres;

  private Integer nivel;
}
