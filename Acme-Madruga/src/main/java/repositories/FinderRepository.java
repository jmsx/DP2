
package repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.Procession;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select p from Procession p where (p.title LIKE CONCAT('%',:keyword,'%') or p.description LIKE CONCAT('%',:keyword,'%') or p.ticker LIKE CONCAT('%',:keyword,'%') or p.mode LIKE CONCAT('%',:keyword,'%'))")
	List<Procession> findForKeyword(@Param("keyword") String keyword);

	@Query("select p from Procession p join p.brotherhood b where b.area.name =?1")
	List<Procession> findForArea(String areaName);

	@Query("select p from Procession p where p.moment >=?1")
	List<Procession> findForMinDate(Date fecha);

	@Query("select p from Procession p where p.moment <=?1")
	List<Procession> findForMaxDate(Date fecha);

}
