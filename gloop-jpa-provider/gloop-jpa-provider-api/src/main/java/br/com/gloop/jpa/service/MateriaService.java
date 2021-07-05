package br.com.gloop.jpa.service;

import java.util.List;

import br.com.gloop.jpa.model.Materia;

public interface MateriaService {

	List<Materia> list();

	Materia get(Long id);

    void add(Materia materia);
    
    void edit(Materia materia);

    void remove(Long id);
	
}
