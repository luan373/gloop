package org.apache.karaf.jpa.blueprint.hibernate;

import br.com.gloop.jpa.model.Materia;
import br.com.gloop.jpa.service.MateriaService;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class MateriaServiceImpl implements MateriaService {

	@PersistenceContext(unitName = "gloop-hibernate")
	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional(Transactional.TxType.REQUIRES_NEW)
	@Override
	public void add(Materia materia) {
		entityManager.persist(materia);
	}
	
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	@Override
	public void edit(Materia materia) {
		entityManager.merge(materia);
		
	}

	@Transactional(Transactional.TxType.SUPPORTS)
	@Override
	public List<Materia> list() {
		return entityManager.createQuery("SELECT m FROM Materia m", Materia.class).getResultList();
	}

	@Transactional(Transactional.TxType.SUPPORTS)
	@Override
	public Materia get(Long id) {
		return entityManager.find(Materia.class, id);
	}

	@Transactional(Transactional.TxType.REQUIRES_NEW)
	@Override
	public void remove(Long id) {
		Materia materia = entityManager.find(Materia.class, id);
		if (materia != null) {
			entityManager.remove(materia);
		}
	}

}
