/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.karaf.jpa.blueprint.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import br.com.gloop.jpa.model.Credentials;
import br.com.gloop.jpa.service.CredentialsService;

@Transactional
public class CredentialsServiceImpl implements CredentialsService {

	@PersistenceContext(unitName = "gloop-hibernate")
	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional(Transactional.TxType.SUPPORTS)
	@Override
	public List<Credentials> list() {
		return entityManager.createQuery("SELECT c FROM Credentials c", Credentials.class).getResultList();
	}

	@Transactional(Transactional.TxType.REQUIRES_NEW)
	@Override
	public void remove(Long id) {
		Credentials credentials = entityManager.find(Credentials.class, id);
		if (credentials != null) {
			entityManager.remove(credentials);
		}
	}

	@Transactional(Transactional.TxType.SUPPORTS)
	@Override
	public Credentials get(Long id) {
		return entityManager.find(Credentials.class, id);
	}

	@Transactional(Transactional.TxType.REQUIRES_NEW)
	@Override
	public void add(Credentials credentials) {
		entityManager.persist(credentials);
	}

	@Transactional(Transactional.TxType.REQUIRES_NEW)
	@Override
	public void add(String username, String password) {
		Credentials credentials = new Credentials();
		credentials.setUsername(username);
		credentials.setPassword(password);

		entityManager.persist(credentials);
	}

	@Transactional(Transactional.TxType.SUPPORTS)
	@Override
	public Credentials get(String username, String password) {
		return entityManager
				.createQuery("SELECT c FROM Credentials c WHERE c.username =:username AND c.password =:password ",
						Credentials.class)
				.setParameter("username", username).setParameter("password", password).getSingleResult();
	}

	@Transactional(Transactional.TxType.SUPPORTS)
	@Override
	public Credentials get(String username) {
		return entityManager.createQuery("SELECT c FROM Credentials c WHERE c.username =:username ", Credentials.class)
				.setParameter("username", username).getSingleResult();
	}

}
