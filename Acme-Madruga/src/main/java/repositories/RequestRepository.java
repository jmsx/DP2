
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

	@Query("select r from Request r join r.member m where m.id = ?1")
	Collection<Request> findByMember(Integer idMember);

	@Query("select r from Request r join r.Procession p where p.id = ?1")
	Collection<Request> findByProcesion(Integer idProcesion);

	@Query("select r from Request r join r.procession p where p.brotherhood.id = ?1")
	Collection<Request> findByBrotherhood(Integer idBrtoherhood);

	@Query("select case when (count(r) > 0)  then true else false end from Request r join r.procession p where (p.brotherhood.id = ?1 and r.id = ?2)")
	Boolean checkBrotherhoodAccess(Integer idBrtoherhood, Integer idRequest);

}
