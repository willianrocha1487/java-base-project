package com.base.java.messages;

public enum MessageEnum {

	USUARIO_NAO_ENCONTRADO("Usuário não encontrado!"),
	USUARIO_JA_CADASTRADO("Usuário já cadastrado");

	private final String value;

	MessageEnum(String value) {
		this.value = value;
	}

	public static String MSG(MessageEnum messageEnum) {
		return messageEnum.getValue();
	}

	private String getValue() {
		return value;
	}
}
