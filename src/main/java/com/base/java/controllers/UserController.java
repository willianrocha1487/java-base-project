package com.base.java.controllers;

import com.base.java.models.user.UserDTO;
import com.base.java.models.user.UserUpdateDTO;
import com.base.java.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserController {

	private final UserService userService;

	@GetMapping
	public ResponseEntity<List<UserDTO>> get() {
		List<UserDTO> list = userService.getAll();

		if (list.isEmpty()) return ResponseEntity.noContent().build();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> getById(@PathVariable("id") Long id) {
		UserDTO dto = userService.getById(id);
		return ResponseEntity.ok().body(dto);
	}

	@PostMapping
	public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDTO) {
		UserDTO response = userService.create(userDTO);
		return ResponseEntity.status(CREATED).body(response);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable("id") Long id, @RequestBody UserUpdateDTO userUpdateDTO) {
		userUpdateDTO.setId(id);
		UserDTO response = userService.update(userUpdateDTO);
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<UserDTO> delete(@PathVariable("id") Long id) {
		userService.delete(id);
		return ResponseEntity.ok().build();
	}
}
