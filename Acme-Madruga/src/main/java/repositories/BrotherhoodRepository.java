
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Brotherhood;

public interface BrotherhoodRepository extends JpaRepository<Brotherhood, Integer> {

	@Query("select b from Brotherhood b where b.userAccount.id=?1")
	Brotherhood findByUserId(Integer brotherhoodId);

	@Query("select b from Enrolment e join e.brotherhood b where e.member.userAccount.id=?1 and e.dropOut=null")
	Collection<Brotherhood> findAllBrotherHoodByMember(Integer id);

	@Query("select count(e) from Enrolment e where e.dropOut=null group by e.member")
	Collection<Integer> getNumberOfMembersPerBrotherhood();

	@Query("select distinct b from Enrolment e join e.brotherhood b where e.member.userAccount.id=?1 and e.dropOut!=null")
	Collection<Brotherhood> brotherhoodsHasBelonged(Integer memberUAId);
}
