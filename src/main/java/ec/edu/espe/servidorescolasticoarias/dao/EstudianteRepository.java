package ec.edu.espe.servidorescolasticoarias.dao;

import ec.edu.espe.servidorescolasticoarias.model.Estudiante;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EstudianteRepository extends MongoRepository<Estudiante, String> {
  Optional<Estudiante> findByCodigoInterno(String codigoInterno);

  Optional<Estudiante> findByCedula(String cedula);

  List<Estudiante> findByNivel(Integer nivel);
}
