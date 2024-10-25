package com.daw.web.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daw.persistence.entities.Tarea;
import com.daw.services.TareaService;

@RestController
@RequestMapping("/tareas")
public class TareaController {

	@Autowired
	private TareaService tareaService;

	@GetMapping
	public ResponseEntity<List<Tarea>> getAll() {
		List<Tarea> tareas = this.tareaService.getTareas();

		if (tareas.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(tareas);
	}

	@GetMapping("/{idTarea}")
	public ResponseEntity<Tarea> getById(@PathVariable("idTarea") int idTarea) {
		Optional<Tarea> tarea = this.tareaService.getTarea(idTarea);

		if (tarea.isPresent()) {
			return ResponseEntity.ok(tarea.get());
		}

		else {
			return ResponseEntity.notFound().build();
		}

	}

	@PostMapping
	public ResponseEntity<Tarea> create(@RequestBody Tarea tarea) {
		return new ResponseEntity<Tarea>(this.tareaService.addTarea(tarea), HttpStatus.CREATED);
	}

	@PutMapping("/{idTarea}")
	public ResponseEntity<Tarea> update(@PathVariable("idTarea") int idTarea, @RequestBody Tarea tarea) {
		if (idTarea != tarea.getId()) {
			return ResponseEntity.badRequest().build();
		}
		if (this.tareaService.getTarea(idTarea).isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(this.tareaService.saveTarea(tarea));
	}

	@DeleteMapping("/{idTarea}")
	public ResponseEntity<Tarea> delete(@PathVariable("idTarea") int idTarea) {
		if (this.tareaService.deleteById(idTarea)) {
			return new ResponseEntity<Tarea>(HttpStatus.OK);
		}

		else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/iniciar/{idTarea}")
	public ResponseEntity<Tarea> iniciar(@PathVariable("idTarea") int idTarea) {
		if (this.tareaService.getTarea(idTarea).isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		if (this.tareaService.iniciarTarea(idTarea)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping("/completar/{idTarea}")
	public ResponseEntity<Tarea> completar(@PathVariable("idTarea") int idTarea) {
		if (this.tareaService.getTarea(idTarea).isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		if (this.tareaService.completarTarea(idTarea)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/pendientes")
	public ResponseEntity<List<Tarea>> pendientes() {
		List<Tarea> tareas = this.tareaService.getPendientes();

		if (tareas.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(tareas);
	}

	@GetMapping("/proceso")
	public ResponseEntity<List<Tarea>> proceso() {
		List<Tarea> tareas = this.tareaService.getProceso();

		if (tareas.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(tareas);
	}

	@GetMapping("/completadas")
	public ResponseEntity<List<Tarea>> completadas() {
		List<Tarea> tareas = this.tareaService.getCompletadas();

		if (tareas.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(tareas);
	}

	@GetMapping("/vencidas")
	public ResponseEntity<List<Tarea>> vencidas() {
		return ResponseEntity.ok(this.tareaService.getVencidas());
	}
	
	@GetMapping("/noVencidas")
	public ResponseEntity<List<Tarea>> noVencidas() {
		return ResponseEntity.ok(this.tareaService.getNoVencidas());
	}
	
	@GetMapping("/titulo")
	public ResponseEntity<List<Tarea>> buscarPorTitulo(@RequestParam("titulo") String titulo) {
		return ResponseEntity.ok(this.tareaService.getByTitulo(titulo));
	}
	
	@GetMapping("/tituloLike")
	public ResponseEntity<List<Tarea>> buscarPorTituloLike(@RequestParam String titulo) {
		return ResponseEntity.ok(this.tareaService.getByTitulo2(titulo));
	}
}