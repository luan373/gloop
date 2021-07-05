package br.com.gloop.jpa.service;

import java.util.List;

import br.com.gloop.jpa.model.Alternativa;

public interface AlternativaService {

	List<Alternativa> list();

	Alternativa get(Long id);

	void add(Alternativa alternativa);
	
	void edit(Alternativa alternativa);

    void remove(Long id);
	
}
