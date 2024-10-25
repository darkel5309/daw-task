package com.daw.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.crud.TareaCrudRepository;
import com.daw.persistence.entities.Tarea;
import com.daw.persistence.entities.enums.Estado;

@Service
public class TareaService {
	// inyectar el tarearepository
	// añadir metodos para:
	/*
	 * obtener todas las tareas obtener una tarea guardar la tarea borrar una tarea
	 */

	@Autowired
	private TareaCrudRepository tareaCrudRepository;

	public List<Tarea> getTareas() {
		return (List<Tarea>) this.tareaCrudRepository.findAll();
	}

	public Optional<Tarea> getTarea(int idTarea) {
		return this.tareaCrudRepository.findById(idTarea);
	}

	public Tarea addTarea(Tarea tarea) {
		tarea.setFechaCreacion(LocalDate.now());
		tarea.setEstado(Estado.PENDIENTE);

		return saveTarea(tarea);
	}

	public Tarea saveTarea(Tarea tarea) {
		return this.tareaCrudRepository.save(tarea);
	}

	public boolean deleteById(int idTarea) {
		boolean result = false;

		if (this.getTarea(idTarea).isPresent()) {
			this.tareaCrudRepository.deleteById(idTarea);
			result = true;
		}
		return result;
	}

	public boolean iniciarTarea(int idTarea) {
		if (this.getTarea(idTarea).isPresent()) {

			Tarea tarea = this.getTarea(idTarea).get();

			if (tarea.getEstado() != Estado.PENDIENTE) {
				return false;
			}
			tarea.setEstado(Estado.EN_PROCESO);
			this.tareaCrudRepository.save(tarea);
			return true;
		}
		return false;
	}

	public boolean completarTarea(int idTarea) {
		if (this.getTarea(idTarea).isPresent()) {

			Tarea tarea = this.getTarea(idTarea).get();

			if (tarea.getEstado() != Estado.EN_PROCESO) {
				return false;
			}
			tarea.setEstado(Estado.COMPLETADA);
			this.tareaCrudRepository.save(tarea);
			return true;
		}
		return false;
	}

	public List<Tarea> getPendientes() {
		return this.tareaCrudRepository.findByEstado(Estado.PENDIENTE);
	}

	public List<Tarea> getProceso() {
		return this.tareaCrudRepository.findByEstado(Estado.EN_PROCESO);
	}

	public List<Tarea> getCompletadas() {
		return this.tareaCrudRepository.findByEstado(Estado.COMPLETADA);
	}
	
	public List<Tarea> getVencidas(){
		return this.tareaCrudRepository.findByFechaVencimientoBefore(LocalDateTime.now());
	}
	
	public List<Tarea> getNoVencidas(){
		return this.tareaCrudRepository.findByFechaVencimientoAfter(LocalDateTime.now());
	}
	
	public List<Tarea> getByTitulo(String titulo){
		return this.tareaCrudRepository.findByTituloContaining(titulo);
	}
	
	public List<Tarea> getByTitulo2(String titulo){
		return this.tareaCrudRepository.findByTituloLike(titulo);
	}

}
