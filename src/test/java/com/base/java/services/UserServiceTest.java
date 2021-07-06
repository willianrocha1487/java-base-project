package com.base.java.services;

import com.base.java.exceptions.ResourceNotFoundException;
import com.base.java.exceptions.ServiceErrorException;
import com.base.java.mappers.user.UserMapper;
import com.base.java.mappers.user.UserMapperImpl;
import com.base.java.models.user.UserDTO;
import com.base.java.models.user.UserEntity;
import com.base.java.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.base.java.messages.MessageEnum.*;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

	@InjectMocks
	UserService userService;

	@Mock
	UserRepository userRepository;

	UserMapper userMapper = new UserMapperImpl();

	UserEntity userEntity;
	UserDTO userDTO;


	@BeforeEach
	void init() {
		userDTO = buildUserDTO();
		userEntity = buildUserEntity();
		userService = new UserService(userRepository, userMapper);
	}

	@Test
	void shouldSaveUserSuccessfully() {
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

		UserDTO response = userService.create(userDTO);

		assertNotNull(response);
		assertEquals(1L, response.getId());
		assertNull(response.getPassword());

		verify(userRepository).findByEmail(anyString());
		verify(userRepository).save(any(UserEntity.class));
	}

	@Test
	void shouldThrowExceptionWhyUserAlreadyExists() {
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new UserEntity()));

		try {
			userService.create(userDTO);
		} catch (Exception ex) {
			assertEquals(ServiceErrorException.class, ex.getClass());
			assertEquals(MSG(USUARIO_JA_CADASTRADO), ex.getMessage());
		}

		verify(userRepository).findByEmail(anyString());
		verifyNoMoreInteractions(userRepository);
	}

	@Test
	void shouldReturnAListWithItems() {
		List<UserEntity> list = new ArrayList<>();
		list.add(userEntity);

		when(userRepository.findAll()).thenReturn(list);

		List<UserDTO> dtoList = userService.getAll();

		assertEquals(1, dtoList.size());
		verify(userRepository).findAll();
		verifyNoMoreInteractions(userRepository);
	}

	@Test
	void shouldReturnAnEmptylist() {
		when(userRepository.findAll()).thenReturn(new ArrayList<>());

		List<UserDTO> dtoList = userService.getAll();

		assertEquals(TRUE, dtoList.isEmpty());
		verify(userRepository).findAll();
		verifyNoMoreInteractions(userRepository);
	}

	@Test
	void shouldRetunUserSuccessfully() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));

		UserDTO response = userService.getById(anyLong());

		assertNotNull(response);
		assertEquals(1L, response.getId());
		assertNull(response.getPassword());

		verify(userRepository).findById(anyLong());
		verifyNoMoreInteractions(userRepository);
	}

	@Test
	void shouldThrowExceptionWhyUserNotExists() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
		UserDTO response = null;

		try {
			response = userService.getById(anyLong());
		} catch (Exception ex) {
			assertEquals(ResourceNotFoundException.class, ex.getClass());
			assertEquals(MSG(USUARIO_NAO_ENCONTRADO), ex.getMessage());
		}

		assertNull(response);
		verify(userRepository).findById(anyLong());
		verifyNoMoreInteractions(userRepository);
	}

	private UserEntity buildUserEntity() {
		return userMapper.toEntity(buildUserDTO());
	}

	private UserDTO buildUserDTO() {
		return UserDTO.builder()
				.id(1L)
				.name("name")
				.email("email@email.com")
				.password("password")
				.build();
	}
}
