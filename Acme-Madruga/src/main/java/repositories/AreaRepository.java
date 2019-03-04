
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Area;

public interface AreaRepository extends JpaRepository<Area, Integer> {

	@Query("select b.area from Brotherhood b")
	Collection<Area> AllAreasSettled();

//	@Query("select count(b) from Brotherhood b group by area")
//	Collection<Integer> getNumberOfBrotherhoodsPerArea();

	@Query("select count(ab) from Brotherhood b join b.area ab")
	Integer getNumberOfAreasAssigned();

	@Query("select count(a) from Area a")
	Integer getTotalOfAreas();
}
