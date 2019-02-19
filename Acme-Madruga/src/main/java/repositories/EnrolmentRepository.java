
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;
import domain.Enrolment;

@Repository
public interface EnrolmentRepository extends JpaRepository<Enrolment, Integer> {

	@Query("select e from Enrolment e where e.member.userAccount.id=?1")
	Collection<Enrolment> findAllByMemberId(Integer id);

	@Query("select e from Enrolment e where e.brotherhood.userAccount.id=?1")
	Collection<Enrolment> findAllByBrotherHoodId(Integer id);

	@Query("select b from Enrolment e join e.brotherhood b where e.member.id=?1")
	Collection<Brotherhood> findAllBrotherHoodByMember(Integer id);
}
