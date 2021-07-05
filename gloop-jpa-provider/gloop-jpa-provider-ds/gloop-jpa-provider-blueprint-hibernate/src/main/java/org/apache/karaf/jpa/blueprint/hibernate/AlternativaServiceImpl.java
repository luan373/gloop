package org.apache.karaf.jpa.blueprint.hibernate;

import br.com.gloop.jpa.model.Alternativa;
import br.com.gloop.jpa.service.AlternativaService;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class AlternativaServiceImpl implements AlternativaService {

	@PersistenceContext(unitName = "gloop-hibernate")
	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional(Transactional.TxType.REQUIRES_NEW)
	@Override
	public void add(Alternativa alternativa) {
		entityManager.persist(alternativa);
	}
	
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	@Override
	public void edit(Alternativa alternativa) {
		entityManager.merge(alternativa);
	}

	@Transactional(Transactional.TxType.SUPPORTS)
	@Override
	public List<Alternativa> list() {
		return entityManager.createQuery("SELECT a FROM Alternativa a", Alternativa.class).getResultList();
	}

	@Transactional(Transactional.TxType.SUPPORTS)
	@Override
	public Alternativa get(Long id) {
		return entityManager.find(Alternativa.class, id);
	}

	@Transactional(Transactional.TxType.REQUIRES_NEW)
	@Override
	public void remove(Long id) {
		Alternativa alternativa = entityManager.find(Alternativa.class, id);
		if (alternativa != null) {
			entityManager.remove(alternativa);
		}
	}
}
