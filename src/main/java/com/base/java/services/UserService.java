package com.base.java.services;

import com.base.java.exceptions.ResourceNotFoundException;
import com.base.java.exceptions.ServiceErrorException;
import com.base.java.mappers.user.UserMapper;
import com.base.java.models.user.UserDTO;
import com.base.java.models.user.UserEntity;
import com.base.java.models.user.UserUpdateDTO;
import com.base.java.repositories.UserRepository;
import com.base.java.services.base.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.base.java.messages.MessageEnum.*;

@Service
@RequiredArgsConstructor
public class UserService implements CrudService<UserDTO, UserUpdateDTO> {

	private final UserRepository repository;

	private final UserMapper mapper;

	public UserDTO create(UserDTO userDTO) {
		getByEmail(userDTO.getEmail()).ifPresent(x -> {throw new ServiceErrorException(MSG(USUARIO_JA_CADASTRADO));});

		return Optional.of(userDTO)
					.map(mapper::toEntity)
					.map(repository::save)
					.map(mapper::toDto)
					.get();
	}

	public UserDTO getById(Long id) {
		return repository.findById(id)
				.map(mapper::toDto)
				.orElseThrow(() -> new ResourceNotFoundException(MSG(USUARIO_NAO_ENCONTRADO)));
	}

	public List<UserDTO> getAll() {
		return repository.findAll()
				.stream()
				.map(mapper::toDto)
				.collect(Collectors.toList());
	}

	public UserDTO update(UserUpdateDTO userUpdateDTO) {
		return repository.findById(userUpdateDTO.getId())
				.map(userEntity -> {
					userEntity.setName(userUpdateDTO.getName());
					repository.save(userEntity);
					return userEntity;
				})
				.map(mapper::toDto)
				.orElseThrow(() -> new ResourceNotFoundException(MSG(USUARIO_NAO_ENCONTRADO)));
	}

	public void delete(Long id) {
		repository.findById(id)
				.ifPresentOrElse(repository::delete,
						() -> {throw new ResourceNotFoundException(MSG(USUARIO_NAO_ENCONTRADO));});
	}

	private Optional<UserEntity> getByEmail(String email) {
		return repository.findByEmail(email.trim());
	}
}
