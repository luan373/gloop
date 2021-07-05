package br.com.gloop.jpa.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Questao implements Serializable {

	private static final long serialVersionUID = 2654719145140306468L;

	@Id
	@GeneratedValue
	private Long id;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "idQuestao")
	private List<Alternativa> alternativas;

	@ManyToOne
	@JoinColumn(name = "idMateria")
	private Materia materia;

	@Enumerated(EnumType.STRING)
	@Column(name = "serie")
	private Serie serie;

	private String nomeQuestao;

	private String textoQuestao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public Serie getSerie() {
		return serie;
	}

	public void setSerie(Serie serie) {
		this.serie = serie;
	}

	public String getNomeQuestao() {
		return nomeQuestao;
	}

	public void setNomeQuestao(String nomeQuestao) {
		this.nomeQuestao = nomeQuestao;
	}

	public String getTextoQuestao() {
		return textoQuestao;
	}

	public void setTextoQuestao(String textoQuestao) {
		this.textoQuestao = textoQuestao;
	}

	public List<Alternativa> getAlternativas() {
		return alternativas;
	}

	public void setAlternativas(List<Alternativa> alternativas) {
		this.alternativas = alternativas;
	}

}
