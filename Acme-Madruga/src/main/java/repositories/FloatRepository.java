
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Float;
import domain.Procession;

@Repository
public interface FloatRepository extends JpaRepository<Float, Integer> {

	@Query("select p from Procession p join p.floats b where b.title =?1")
	List<Procession> findForFloat(String fProcession);

	@Query("select distinct f from Float f where f.brotherhood.userAccount.id = ?1")
	Collection<Float> findByBrotherhood(int brotherhooodUserAccountId);

	@Query("select distinct f from Float f where f.brotherhood.userAccount.id = ?1 AND ((1.0*(select count(p) from Procession p join p.floats fp where fp.id=f.id))>0)")
	Collection<Float> findSelectedByBrotherhood(int brotherhooodUserAccountId);

}
