
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Procession;

@Repository
public interface ProcessionRepository extends JpaRepository<Procession, Integer> {

	@Query("select p from Procession p where p.brotherhood.userAccount.id=?1")
	Collection<Procession> findAllProcessionByBrotherhoodId(Integer id);

	@Query("select p from Request r join r.Procession p where r.member.userAccount.id=?1")
	Collection<Procession> findAllProcessionByBMemberId(Integer id);

	@Query("select p from Procession p where p.ticker = ?1")
	Collection<Procession> getProcessionWithTicker(String ticker);

}
