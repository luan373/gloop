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
package org.apache.karaf.jpa.ds.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.aries.jpa.template.EmFunction;
import org.apache.aries.jpa.template.JpaTemplate;
import org.apache.aries.jpa.template.TransactionType;
import org.apache.karaf.jpa.model.Credentials;
import org.apache.karaf.jpa.service.CredentialsService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = CredentialsService.class, immediate = true)
public class CredentialsServiceImpl implements CredentialsService {

	@Reference(target = "(osgi.unit.name=gloop-hibernate)")
	private JpaTemplate jpaTemplate;

	@Override
	public List<Credentials> list() {
		return jpaTemplate.txExpr(TransactionType.Supports, new EmFunction<List<Credentials>>() {
			@Override
			public List<Credentials> apply(EntityManager em) {
				return em.createQuery("SELECT c FROM Credentials c", Credentials.class).getResultList();
			}
		});
	}

	@Override
	public void remove(Long id) {
		jpaTemplate.tx(TransactionType.RequiresNew, entityManager -> {
			Credentials credentials = entityManager.find(Credentials.class, id);
			if (credentials != null) {
				entityManager.remove(credentials);
			}
		});
	}

	@Override
	public Credentials get(Long id) {
		return jpaTemplate.txExpr(TransactionType.Supports, entityManager -> entityManager.find(Credentials.class, id));
	}

	@Override
	public void add(Credentials credentials) {
		jpaTemplate.tx(TransactionType.RequiresNew, entityManager -> {
			entityManager.persist(credentials);
			entityManager.flush();
		});
	}

	@Override
	public void add(String username, String password) {
		Credentials credentials = new Credentials();
		credentials.setUsername(username);
		credentials.setPassword(password);
		jpaTemplate.tx(TransactionType.RequiresNew, entityManager -> {
			entityManager.persist(credentials);
			entityManager.flush();
		});
	}

	@Override
	public Credentials get(String username, String password) {
		return jpaTemplate.txExpr(TransactionType.Supports, new EmFunction<Credentials>() {
			@Override
			public Credentials apply(EntityManager em) {
				try {
					return em
							.createQuery("SELECT c FROM Credentials c WHERE c.username =:username AND c.password =:password ",
							Credentials.class)
							.setParameter("username", username)
							.setParameter("password", password)
							.getSingleResult();
				} catch (NoResultException nre) {
					return null;
				}
			}
		});
	}

	@Override
	public Credentials get(String username) {
		return jpaTemplate.txExpr(TransactionType.Supports, new EmFunction<Credentials>() {
			@Override
			public Credentials apply(EntityManager em) {
				try {
					return em.createQuery("SELECT c FROM Credentials c WHERE c.username =:username ", Credentials.class)
							.setParameter("username", username).getSingleResult();
				} catch (NoResultException nre) {
					return null;
				}
			}
		});
	}

}
