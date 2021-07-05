package org.apache.karaf.jpa.blueprint.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import br.com.gloop.jpa.model.Questao;
import br.com.gloop.jpa.service.QuestaoService;

@Transactional
public class QuestaoServiceImpl implements QuestaoService {

	@PersistenceContext(unitName = "gloop-hibernate")
	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional(Transactional.TxType.REQUIRES_NEW)
	@Override
	public void add(Questao questao) {
		entityManager.persist(questao);
	}
	
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	@Override
	public void edit(Questao questao) {
		entityManager.merge(questao);
	}

	@Transactional(Transactional.TxType.SUPPORTS)
	@Override
	public List<Questao> list() {
		return entityManager.createQuery("SELECT q FROM Questao q", Questao.class).getResultList();
	}

	@Transactional(Transactional.TxType.SUPPORTS)
	@Override
	public Questao get(Long id) {
		return entityManager.find(Questao.class, id);
	}

	@Transactional(Transactional.TxType.REQUIRES_NEW)
	@Override
	public void remove(Long id) {
		Questao questao = entityManager.find(Questao.class, id);
		if (questao != null) {
			entityManager.remove(questao);
		}
	}

}