
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PositionRepository;
import domain.Administrator;
import domain.Position;

@Service
@Transactional
public class PositionService {

	@Autowired
	private PositionRepository		positionRepository;

	@Autowired
	private AdministratorService	administratorService;


	public Position create() {
		final Position res = new Position();
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
		final Position res = this.positionRepository.save(position);
		return res;
	}

	public void delete(final Position position) {
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		Assert.isTrue(!this.AllPositionUsed().contains(position)); /* Una position se puede borrar siempre y cuando no se est� usando */
		this.positionRepository.delete(position);
	}

	/* ========================= OTHER METHODS =========================== */

	public Collection<Position> AllPositionUsed() {
		return this.positionRepository.AllPositionUsed();
	}
}