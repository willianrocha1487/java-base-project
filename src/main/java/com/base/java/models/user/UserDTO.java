package com.base.java.models.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private Long id;

	@Email(message = "Formato de email inválido")
	@NotBlank(message = "Email é obrigatório")
	private String email;
	@NotBlank(message = "Name é obrigatório")
	private String name;
	@NotBlank(message = "Password é obrigatório")
	private String password;
}
