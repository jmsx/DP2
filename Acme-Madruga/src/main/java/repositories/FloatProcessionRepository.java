
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FloatProcession;
import domain.Procession;

@Repository
public interface FloatProcessionRepository extends JpaRepository<FloatProcession, Integer> {

	@Query("select p from Procession p join p.floatProcessions b where b.title =?1")
	List<Procession> findForFloat(String fProcession);

}
