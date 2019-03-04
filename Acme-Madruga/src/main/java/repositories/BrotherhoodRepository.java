
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

	@Query("SELECT STDDEV(x),MAX(x),MIN(x),AVG(x) FROM (SELECT COUNT(*) AS x FROM `acme-madruga`.ENROLMENT GROUP BY member) AS x")
	Double[] getStadisticsOfMembersPerBrotherhood();

	/** The largest brotherhood is the one with highest number of members **/
	@Query("SELECT brotherhood FROM `acme-madruga`.ENROLMENT GROUP BY brotherhood HAVING COUNT(*) = (SELECT MAX(x) FROM (SELECT COUNT(*) AS x FROM `acme-madruga`.ENROLMENT GROUP BY member)AS X )")
	Brotherhood getLargestBrotherhood();

	/** The smallest brotherhood is the one with lowest number of members **/
	@Query("SELECT brotherhood FROM `acme-madruga`.ENROLMENT GROUP BY brotherhood HAVING COUNT(*) = (SELECT MIN(x) FROM (SELECT COUNT(*) AS x FROM `acme-madruga`.ENROLMENT GROUP BY member)AS X )")
	Brotherhood getSmallestBrotherhood();

	@Query("select distinct b from Enrolment e join e.brotherhood b where e.member.userAccount.id=?1 and e.dropOut!=null")
	Collection<Brotherhood> brotherhoodsHasBelonged(Integer memberUAId);
}
