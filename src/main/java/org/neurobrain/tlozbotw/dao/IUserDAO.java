package org.neurobrain.tlozbotw.dao;

import java.util.Optional;
import org.neurobrain.tlozbotw.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserDAO extends JpaRepository<User, Long> {
	Optional<User> findByUserName(String userName);
	Optional<User> findByEmail(String userName);
	Optional<User> findByPhoneNumber(String phoneNumber);
	Optional<User> findByRecoverCode(String recoverCode);
}