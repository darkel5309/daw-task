package com.daw.persistence.crud;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.daw.persistence.entities.Tarea;
import com.daw.persistence.entities.enums.Estado;

public interface TareaCrudRepository extends CrudRepository<Tarea, Integer> {

	List<Tarea> findByEstado(Estado estado);
	List<Tarea> findByTituloStartingWith(String cadena);
	List<Tarea> findByFechaVencimientoBefore(LocalDateTime fecha);
	List<Tarea> findByFechaVencimientoAfter(LocalDateTime fecha);
	List<Tarea> findByTituloContaining(String titulo);
	List<Tarea> findByTituloLike(String titulo);
}
