package br.com.gloop.jpa.service;

import java.util.List;

import br.com.gloop.jpa.model.Questao;

public interface QuestaoService {

	List<Questao> list();

	Questao get(Long id);

    void add(Questao questao);
    
    void edit(Questao questao);

    void remove(Long id);
	
}
