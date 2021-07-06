package com.base.java.models.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class UserUpdateDTO {

	private Long id;

	@NotBlank(message = "Name é obrigatório")
	private String name;
}
