
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BrotherhoodRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Brotherhood;

@Service
@Transactional
public class BrotherhoodService {

	@Autowired
	private BrotherhoodRepository	brotherhoodRepository;

	@Autowired
	private ActorService		actorService;


	public Brotherhood create() {
		return new Brotherhood();
	}

	public Collection<Brotherhood> findAll() {
		final Collection<Brotherhood> result = this.brotherhoodRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Brotherhood findOne(final int brotherhoodId) {
		Assert.isTrue(brotherhoodId != 0);
		final Brotherhood result = this.brotherhoodRepository.findOne(brotherhoodId);
		Assert.notNull(result);
		return result;
	}

	public Brotherhood save(final Brotherhood brotherhood) {
		Assert.notNull(brotherhood);
		Brotherhood result;
		this.actorService.checkForSpamWords(brotherhood);
		if (brotherhood.getId() == 0)
			result = this.brotherhoodRepository.save(brotherhood);
		else
			result = (Brotherhood) this.actorService.update(brotherhood);

		return result;
	}

	public void delete(final Brotherhood brotherhood) {
		Assert.notNull(brotherhood);
		Assert.isTrue(brotherhood.getId() != 0);
		Assert.isTrue(this.brotherhoodRepository.exists(brotherhood.getId()));
		this.brotherhoodRepository.delete(brotherhood);
	}

	/* ========================= OTHER METHODS =========================== */

	public Brotherhood findByPrincipal() {
		final UserAccount user = LoginService.getPrincipal();
		Assert.notNull(user);

		final Brotherhood brotherhood = this.findByUserId(user.getId());
		Assert.notNull(brotherhood);
		final boolean bool = this.actorService.checkAuthority(brotherhood, Authority.MEMBER);
		Assert.isTrue(bool);

		return brotherhood;
	}

	public Brotherhood findByUserId(final int id) {
		Assert.isTrue(id != 0);
		final Brotherhood brotherhood = this.brotherhoodRepository.findByUserId(id);
		return brotherhood;
	}

}
