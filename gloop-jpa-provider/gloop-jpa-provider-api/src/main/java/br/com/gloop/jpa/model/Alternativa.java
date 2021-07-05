package br.com.gloop.jpa.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Alternativa {

	@Id
	@GeneratedValue
	private Long id;

	private String textoAlternativa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTextoAlternativa() {
		return textoAlternativa;
	}

	public void setTextoAlternativa(String textoAlternativa) {
		this.textoAlternativa = textoAlternativa;
	}

}
