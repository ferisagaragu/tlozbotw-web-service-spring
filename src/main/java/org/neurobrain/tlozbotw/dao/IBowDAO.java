package org.neurobrain.tlozbotw.dao;

import org.neurobrain.tlozbotw.entity.Bow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IBowDAO extends JpaRepository<Bow, Long> {

	@Query("select bow from Bow bow where bow.id not in (" +
						"select usBw.bow.id from UserBow usBw where usBw.user.id = :id" +
				") and bow.available = true"
	)
	List<Bow> findAllNotIn(Long id);

}
