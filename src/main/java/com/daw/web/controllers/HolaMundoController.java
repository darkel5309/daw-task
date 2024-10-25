package com.daw.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/holamundo")
public class HolaMundoController {

	@GetMapping("/saludar")
	public ResponseEntity<String> saludar() {

		return ResponseEntity.ok("Hola mundo");
	}

}