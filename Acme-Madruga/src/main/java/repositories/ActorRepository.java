
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select count(a) from Actor a join a.folders f join f.messages m where m.id=?1")
	int countByMessageId(Integer id);

	@Query("select a from Actor a where a.userAccount.id=?1")
	Actor findByUserId(Integer id);

	@Query("select a from Actor a where a.spammer=true")
	Collection<Actor> findAllSpammer();

	@Query("select a from Actor a join a.socialProfiles s where s.id=?1")
	Actor findBySocialProfileId(Integer id);
}
