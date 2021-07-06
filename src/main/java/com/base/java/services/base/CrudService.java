package com.base.java.services.base;

import java.util.List;

public interface CrudService<Dto, UpdateDto> {

	Dto create(Dto dto);
	Dto getById(Long id);
	List<Dto> getAll();
	Dto update(UpdateDto dto);
	void delete(Long id);
}
