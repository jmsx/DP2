package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Brotherhood;

public interface BrotherhoodRepository extends JpaRepository<Brotherhood, Integer>{
	
	@Query("select b from Brotherhood b where b.userAccount.id=?1")
	Brotherhood findByUserId(Integer brotherhoodId);

}
