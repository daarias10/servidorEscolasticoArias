package ec.edu.espe.servidorescolasticoarias.service;

import ec.edu.espe.servidorescolasticoarias.dao.EstudianteRepository;
import ec.edu.espe.servidorescolasticoarias.dao.ParaleloRepository;
import ec.edu.espe.servidorescolasticoarias.exception.EstudianteException;
import ec.edu.espe.servidorescolasticoarias.model.Estudiante;
import ec.edu.espe.servidorescolasticoarias.model.Paralelo;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstudianteService {
  private final EstudianteRepository estudianteRepository;
  private final ParaleloRepository paraleloRepository;

  public static int randInt(int min, int max) {
    Random rand = new Random();
    int randomNum = rand.nextInt((max - min) + 1) + min;
    return randomNum;
  }

  public List<Estudiante> obtenerPorNivel(Integer nivel) {
    return this.estudianteRepository.findByNivel(nivel);
  }

  public List<Estudiante> obtenerEstudiantePorNivelYParalelo(Integer nivel, Integer paralelo) {
    Optional<Paralelo> paraleloOpt =
        this.paraleloRepository.findByNivelAndParaleloNum(nivel, paralelo);
    if (paraleloOpt.isPresent()) {
      return paraleloOpt.get().getEstudiantes();
    } else {
      return null;
    }
  }

  public Paralelo obtenerPorNivelYParalelo(Integer nivel, Integer paralelo) {
    Optional<Paralelo> paraleloOpt =
        this.paraleloRepository.findByNivelAndParaleloNum(nivel, paralelo);
    return paraleloOpt.orElse(null);
  }

  public Estudiante obtenerPorCedula(String cedula) {
    Optional<Estudiante> estudianteOpt = this.estudianteRepository.findByCedula(cedula);
    return estudianteOpt.orElse(null);
  }

  public void crearEstudiante(Estudiante estudiante) throws EstudianteException {
    if (this.obtenerPorCedula(estudiante.getCedula()) != null) {
      log.info("El estudiante ya existe");
      throw new EstudianteException("El estudiante ya existe");
    }
    if (estudiante.getNivel() > 10 && estudiante.getNivel() < 1) {
      log.info("El nivel no es valido");
      throw new EstudianteException("El nivel no es valido");
    }
    this.estudianteRepository.save(estudiante);
  }

  public void asignarEstudianteParalelo(String cedula, Integer nivel) throws EstudianteException {
    Estudiante estudianteOpt = this.obtenerPorCedula(cedula);
    Integer aux = 0;
    Boolean bandera = false;
    Paralelo paraleloDb = null;
    if (estudianteOpt != null) {
      for (Paralelo paralelo : this.paraleloRepository.findAll()) {
        if (paralelo.getEstudiantes().size() == 0) {
          aux++;
        }
      }
      if (aux == 3) {
        bandera = true;
      }
      if (!bandera) {
        bandera = false;
        List<Paralelo> paralelos = this.paraleloRepository.findByNivel(nivel);
        for (Paralelo paralelo : paralelos) {
          if (bandera) {
            if (paralelo.getEstudiantes().size() < aux) {
              aux = paralelo.getEstudiantes().size();
              paraleloDb = paralelo;
            }
            return;
          } else {
            paraleloDb = paralelo;
            aux = paralelo.getEstudiantes().size();
            bandera = true;
          }
        }
      } else {
        aux = this.randInt(1, 3);
        paraleloDb = this.obtenerPorNivelYParalelo(nivel, aux);
      }
      if (paraleloDb != null) {
        List<Estudiante> estudiantes = paraleloDb.getEstudiantes();
        estudiantes.add(estudianteOpt);
        paraleloDb.setEstudiantes(estudiantes);
        this.paraleloRepository.save(paraleloDb);
      } else {
        throw new EstudianteException("No hay paralelos disponibles");
      }
    } else {
      log.error("No se encontró el estudiante con cédula {}", cedula);
      throw new EstudianteException("No se encontró el estudiante con cédula " + cedula);
    }
  }
}
