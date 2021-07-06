package com.base.java.mappers;

public interface BaseMapper <A, B> {
	B toDto(A a);
	A toEntity(B b);
}
