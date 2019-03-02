
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Area;

public interface AreaRepository extends JpaRepository<Area, Integer> {

	@Query("select b.area from Brotherhood b")
	Collection<Area> AllAreasSettled();

	@Query("select count(b) from Brotherhood b group by area")
	Collection<Integer> getNumberOfBrotherhoodsPerArea();

}
