package org.neurobrain.tlozbotw.dao;


import org.junit.Test;
import org.junit.runner.RunWith;

import org.neurobrain.tlozbotw.entity.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class IRoleDaoTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private IRoleDAO roleDAO;

	@Test
	public void persist() {
		Role role = new Role();
		role.setName("ROLE_USER");
		entityManager.persist(role);
		entityManager.flush();

		Role roleFind = roleDAO.findByName(role.getName()).orElse(null);
		System.out.println(roleFind);
		assertThat(roleFind.getName()).isEqualTo("ROLE_USER");
	}

}
