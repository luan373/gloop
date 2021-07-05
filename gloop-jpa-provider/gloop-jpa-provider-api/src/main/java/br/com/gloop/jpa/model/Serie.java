package br.com.gloop.jpa.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Serie {
	
	PRIMEIRO_ANO("1º ANO"),  
	SEGUNDO_ANO("2º ANO"), 
	TERCEIRO_ANO("3º ANO"), 
	QUARTO_ANO("4º ANO"), 
	QUINTO_ANO("5º ANO"), 
	SEXTO_ANO("6º ANO"), 
	SETIMO_ANO("7º ANO"), 
	OITAVO_ANO("8º ANO"), 
	NONO_ANO("9º ANO");
	
	private String value;

	private Serie(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
	
	@JsonValue
	public String stringValue() {
		return value;
	}
	
}
