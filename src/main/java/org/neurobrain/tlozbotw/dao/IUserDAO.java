package org.neurobrain.tlozbotw.dao;

import java.util.List;
import java.util.Optional;
import org.neurobrain.tlozbotw.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUserDAO extends JpaRepository<User, Long> {
	Optional<User> findByUserName(String userName);
	Optional<User> findByEmail(String userName);
	Optional<User> findByPhoneNumber(String phoneNumber);
	Optional<User> findByRecoverCode(String recoverCode);

	@Query(
		"select user from User user where " +
	 	"user.id <> :adminId and user.enabled = true"
	)
	List<User> findAllUsers(Long adminId);
}