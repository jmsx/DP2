
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BrotherhoodRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Area;
import domain.Brotherhood;
import forms.BrotherhoodForm;

@Service
@Transactional
public class BrotherhoodService {

	@Autowired
	private BrotherhoodRepository	brotherhoodRepository;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private FolderService			folderService;

	@Autowired
	private EnrolmentService		enrolmentService;

	@Autowired
	private UserAccountService		userAccountService;


	public Brotherhood create() {
		final Brotherhood brotherhood = new Brotherhood();
		this.actorService.setAuthorityUserAccount(Authority.BROTHERHOOD, brotherhood);

		return brotherhood;
	}

	public Collection<Brotherhood> findAll() {
		final Collection<Brotherhood> result = this.brotherhoodRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Brotherhood findOne(final int brotherhoodId) {
		Assert.isTrue(brotherhoodId != 0);
		final Brotherhood result = this.brotherhoodRepository.findOne(brotherhoodId);
		Assert.notNull(result, "Find One de brotherhood es nulo");
		return result;
	}

	public Brotherhood save(final Brotherhood brotherhood) {
		Assert.notNull(brotherhood);
		Brotherhood result;
		this.actorService.checkForSpamWords(brotherhood);

		if (brotherhood.getId() == 0) {
			this.actorService.setAuthorityUserAccount(Authority.BROTHERHOOD, brotherhood);
			result = this.brotherhoodRepository.save(brotherhood);
			this.folderService.setFoldersByDefault(result);
		} else {
			final Actor principal = this.actorService.findByPrincipal();
			Assert.isTrue(principal.getId() == brotherhood.getId(), "You only can edit your info");
			final Brotherhood old = this.brotherhoodRepository.findOne(brotherhood.getId());
			//TODO: Un brotherhood no puede actualizar su area
			Assert.isTrue((brotherhood.getArea().equals(old.getArea())), "You can't change of area");
			result = (Brotherhood) this.actorService.save(brotherhood);
		}
		return result;
	}

	// TODO: delete all information but name including folders and their messages (but no as senders!!)
	public void delete(final Brotherhood brotherhood) {
		Assert.notNull(brotherhood);
		Assert.isTrue(this.findByPrincipal().equals(brotherhood));
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
		final boolean bool = this.actorService.checkAuthority(brotherhood, Authority.BROTHERHOOD);
		Assert.isTrue(bool);

		return brotherhood;
	}

	public Brotherhood findByUserId(final int id) {
		Assert.isTrue(id != 0);
		final Brotherhood brotherhood = this.brotherhoodRepository.findByUserId(id);
		return brotherhood;
	}

	public Collection<Brotherhood> findAllBrotherHoodByMember() {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, Authority.MEMBER));
		final Collection<Brotherhood> all = this.brotherhoodRepository.findAllBrotherHoodByMember(principal.getUserAccount().getId());
		final Collection<Brotherhood> res = new ArrayList<>();
		for (final Brotherhood b : all)
			if (this.enrolmentService.getEnrolment(b, principal).getDropOut() == null)
				res.add(b);
		return res;
	}

	public void areaSet(final Area area) {
		Assert.notNull(area);
		final Brotherhood principal = this.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, Authority.BROTHERHOOD));
		Assert.isTrue(principal.getArea() == null);
		principal.setArea(area);
		this.save(principal);
	}

	public Collection<Brotherhood> AllBrotherhoodsFree() {
		final List<Brotherhood> all = this.brotherhoodRepository.findAll();
		final List<Brotherhood> ocupadas = (List<Brotherhood>) this.findAllBrotherHoodByMember();
		all.removeAll(ocupadas);
		return all;
	}

	public Brotherhood reconstruct(final BrotherhoodForm brotherhoodForm) {
		Brotherhood brotherhood;
		if (brotherhoodForm.getId() == 0) {
			brotherhood = new Brotherhood();
			brotherhood.setName(brotherhoodForm.getName());
			brotherhood.setMiddleName(brotherhoodForm.getMiddleName());
			brotherhood.setSurname(brotherhoodForm.getSurname());
			brotherhood.setPhoto(brotherhoodForm.getPhoto());
			brotherhood.setPhone(brotherhoodForm.getPhone());
			brotherhood.setEmail(brotherhoodForm.getEmail());
			brotherhood.setAddress(brotherhoodForm.getAddress());
			brotherhood.setScore(0.0);
			brotherhood.setSpammer(false);
			brotherhood.setTitle(brotherhoodForm.getTitle());
			brotherhood.setPictures(brotherhoodForm.getPictures());
			final UserAccount account = this.userAccountService.create();
			final Collection<Authority> authorities = new ArrayList<>();
			final Authority auth = new Authority();
			auth.setAuthority(Authority.BROTHERHOOD);
			authorities.add(auth);
			account.setAuthorities(authorities);
			account.setUsername(brotherhoodForm.getUserAccountuser());
			account.setPassword(brotherhoodForm.getUserAccountpassword());
			brotherhood.setUserAccount(account);
		} else {
			brotherhood = this.brotherhoodRepository.findOne(brotherhoodForm.getId());
			brotherhood.setName(brotherhoodForm.getName());
			brotherhood.setMiddleName(brotherhoodForm.getMiddleName());
			brotherhood.setSurname(brotherhoodForm.getSurname());
			brotherhood.setPhoto(brotherhoodForm.getPhoto());
			brotherhood.setPhone(brotherhoodForm.getPhone());
			brotherhood.setEmail(brotherhoodForm.getEmail());
			brotherhood.setAddress(brotherhoodForm.getAddress());
			brotherhood.setTitle(brotherhoodForm.getTitle());
			brotherhood.setPictures(brotherhoodForm.getPictures());
			final UserAccount account = this.userAccountService.findOne(brotherhood.getUserAccount().getId());
			account.setUsername(brotherhoodForm.getUserAccountuser());
			account.setPassword(brotherhoodForm.getUserAccountpassword());
			brotherhood.setUserAccount(account);
		}
		return brotherhood;

	}
}
