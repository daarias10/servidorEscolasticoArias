package ec.edu.espe.servidorescolasticoarias.resource;

import ec.edu.espe.servidorescolasticoarias.model.Estudiante;
import ec.edu.espe.servidorescolasticoarias.service.EstudianteService;
import java.util.List;
import javax.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/estudiante")
@RequiredArgsConstructor
public class EstudianteResource {
  private final EstudianteService service;

  @GetMapping("/estudiantesNivel")
  public ResponseEntity<List<Estudiante>> estudiantesPorNivel(@PathParam("nivel") Integer nivel) {
    try {
      List<Estudiante> estudiantes = this.service.obtenerPorNivel(nivel);
      return ResponseEntity.ok(estudiantes);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/estudiantesNivelParalelo")
  public ResponseEntity<List<Estudiante>> estudiantesPorNivelYParalelo(
      @PathParam("nivel") Integer nivel, @PathParam("paralelo") Integer paralelo) {
    try {
      List<Estudiante> estudiantes =
          this.service.obtenerEstudiantePorNivelYParalelo(nivel, paralelo);
      return ResponseEntity.ok(estudiantes);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/asignar")
  public ResponseEntity<String> asignarEstudianteParalelo(
      @PathVariable String cedula, @PathVariable Integer nivel) {
    try {
      this.service.asignarEstudianteParalelo(cedula, nivel);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("/crear")
  public ResponseEntity<String> crearEstudiante(@RequestBody Estudiante estudiante) {
    try {
      this.service.crearEstudiante(estudiante);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().build();
    }
  }
}
