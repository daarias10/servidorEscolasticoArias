package ec.edu.espe.servidorescolasticoarias.dao;

import ec.edu.espe.servidorescolasticoarias.model.Paralelo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParaleloRepository extends MongoRepository<Paralelo, String> {
  Optional<Paralelo> findByCodigoInterno(String codigoInterno);

  List<Paralelo> findByNivel(Integer nivel);

  Optional<Paralelo> findByNivelAndParaleloNum(Integer nivel, Integer paraleloNum);
}
