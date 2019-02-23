
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PositionRepository;
import security.Authority;
import domain.Actor;
import domain.Position;

@Service
@Transactional
public class PositionService {

	@Autowired
	private PositionRepository				positionRepository;

	@Autowired
	private AdministratorService			administratorService;

	@Autowired
	private ActorService					actorService;

	@Autowired
	private ConfigurationParametersService	configurationParametersService;


	Position create(final String nameSpanish, final String nameEnglish) {
		final Position res = new Position();
		Assert.isTrue(!(this.configurationParametersService.findSpamWords().contains(nameEnglish) || this.configurationParametersService.findSpamWords().contains(nameSpanish)));
		res.setNameEnglish(nameEnglish);
		res.setNameSpanish(nameSpanish);
		return res;
	}

	public Collection<Position> findAll() {
		final Collection<Position> res = this.positionRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Position findOne(final int positionId) {
		Assert.notNull(positionId);
		Assert.isTrue(positionId != 0);
		final Position res = this.positionRepository.findOne(positionId);
		Assert.notNull(res);
		return res;
	}

	public Position save(final Position position) {
		Assert.notNull(position);
		if (position.getId() != 0)
			Assert.isTrue(!(this.configurationParametersService.findSpamWords().contains(position.getNameEnglish()) || this.configurationParametersService.findSpamWords().contains(position.getNameSpanish())));
		final Position res = this.positionRepository.save(position);
		return res;
	}

	//	public void delete(final Position position) {
	//		//		final Actor principal = this.administratorService.findByPrincipal();
	//		//		final Boolean isAdmin = this.actorService.checkAuthority(principal, Authority.ADMIN);
	//		//		Assert.notNull(principal);
	//		//		Assert.isTrue(position.getId() != 0);
	//		//		//		final Collection<Position> all = this.AllPositionUsed();
	//		//		//		Assert.isTrue(!.contains(position)); /* Una position se puede borrar siempre y cuando no se esté usando */
	//		//		if (isAdmin)
	//		//			this.positionRepository.delete(position);
	//
	//		final Actor principal = this.administratorService.findByPrincipal();
	//		final Boolean isAdmin = this.actorService.checkAuthority(principal, Authority.ADMIN);
	//		Assert.notNull(position);
	//		Assert.isTrue(position.getId() != 0);
	//		Assert.isTrue(!this.AllPositionUsed().contains(position));
	//		if (isAdmin)
	//			this.positionRepository.delete(position);
	//
	//	}

	public void delete(final Position position) {
		final Actor principal = this.actorService.findByPrincipal();
		final Boolean isAdmin = this.actorService.checkAuthority(principal, Authority.ADMIN);
		Assert.notNull(position);
		Assert.isTrue(position.getId() != 0);
		Assert.isTrue(!this.AllPositionUsed().contains(position));
		if (isAdmin)
			this.positionRepository.delete(position.getId());
	}
	/* ========================= OTHER METHODS =========================== */

	public Collection<Position> AllPositionUsed() {
		final Collection<Position> res = this.positionRepository.AllPositionUsed();
		Assert.notNull(res);
		return res;
	}
}
