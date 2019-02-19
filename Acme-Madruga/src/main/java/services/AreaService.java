
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AreaRepository;
import security.Authority;
import domain.Actor;
import domain.Area;

@Service
@Transactional
public class AreaService {

	@Autowired
	private AreaRepository	areaRepository;
	
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;


	public Area create() {
		return new Area();
	}

	public Collection<Area> findAll() {
		final Collection<Area> result = this.areaRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Area findOne(final int areaId) {
		Assert.notNull(areaId);
		Assert.isTrue(areaId != 0);
		final Area result = this.areaRepository.findOne(areaId);
		Assert.notNull(result);
		return result;
	}

	public Area save(final Area area) {
		Assert.notNull(area);
		final Area result = this.areaRepository.save(area);
		return result;
	}

	public void delete(final Area area) {
		final Actor principal = this.administratorService.findByPrincipal();
		final Boolean isAdmin = this.actorService.checkAuthority(principal, Authority.ADMIN);
		Assert.notNull(area);
		Assert.isTrue(area.getId() != 0);
		Assert.isTrue(this.areaRepository.exists(area.getId()) 
				&& !this.AllAreasSettled().contains(area));
		if (isAdmin){
			this.areaRepository.delete(area);
		}
	}
	
	/* ========================= OTHER METHODS =========================== */
	
	public Collection<Area> AllAreasSettled(){
		return this.areaRepository.AllAreasSettled();
	}
	
	

}
