
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.BrotherhoodRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Area;
import domain.Brotherhood;
import forms.ActorFrom;

@Service
@Transactional
public class BrotherhoodService {

	@Autowired
	private BrotherhoodRepository						brotherhoodRepository;

	@Autowired
	private ActorService								actorService;

	@Autowired
	private FolderService								folderService;

	@Autowired
	private MemberService								memberService;

	@Autowired
	private UserAccountService							userAccountService;

	@Autowired
	private org.springframework.validation.Validator	validator;


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
		} else
			result = (Brotherhood) this.actorService.save(brotherhood);

		return result;
	}
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
		final Actor principal = this.memberService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, Authority.MEMBER));
		return this.brotherhoodRepository.findAllBrotherHoodByMember(principal.getId());
	}

	public void areaSet(final Area area) {
		Assert.notNull(area);
		final Brotherhood principal = this.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, Authority.BROTHERHOOD));
		Assert.isTrue(principal.getArea() == null);
		principal.setArea(area);
		this.save(principal);
	}

	//	public void registerAsBrotherhood(Brotherhood brotherhood) {
	//		brotherhood = this.create();
	//		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
	//		final String hash = encoder.encodePassword(brotherhood.getUserAccount().getPassword(), null);
	//		brotherhood.getUserAccount().setPassword(hash);
	//		brotherhood.setSpammer(false);
	//		brotherhood.setScore(0.0);
	//		brotherhood.setPictures(new ArrayList<String>());
	//		brotherhood.setDate(new Date(System.currentTimeMillis() - 1));
	//
	//		final Brotherhood saved = this.brotherhoodRepository.save(brotherhood);
	//		this.folderService.setFoldersByDefault(saved);
	//	}

	public Brotherhood reconstruct(final ActorFrom actorForm, BindingResult binding) {
		binding = binding;
		Brotherhood brotherhood;
		if (actorForm.getId() == 0) {
			brotherhood = this.create();
			brotherhood.setName(actorForm.getName());
			brotherhood.setMiddleName(actorForm.getMiddleName());
			brotherhood.setSurname(actorForm.getSurname());
			brotherhood.setPhoto(actorForm.getPhoto());
			brotherhood.setPhone(actorForm.getPhone());
			brotherhood.setEmail(actorForm.getEmail());
			brotherhood.setAddress(actorForm.getAddress());
			brotherhood.setScore(0.0);
			brotherhood.setSpammer(false);
			final UserAccount account = this.userAccountService.create();
			final Collection<Authority> authorities = new ArrayList<>();
			final Authority auth = new Authority();
			auth.setAuthority(Authority.BROTHERHOOD);
			authorities.add(auth);
			account.setAuthorities(authorities);
			account.setUsername(actorForm.getUserAccountuser());
			account.setPassword(actorForm.getUserAccountpassword());
			brotherhood.setUserAccount(account);
		} else {
			brotherhood = this.brotherhoodRepository.findOne(actorForm.getId());
			brotherhood.setName(actorForm.getName());
			brotherhood.setMiddleName(actorForm.getMiddleName());
			brotherhood.setSurname(actorForm.getSurname());
			brotherhood.setPhoto(actorForm.getPhoto());
			brotherhood.setPhone(actorForm.getPhone());
			brotherhood.setEmail(actorForm.getEmail());
			brotherhood.setAddress(actorForm.getAddress());
			final UserAccount account = this.userAccountService.findOne(brotherhood.getUserAccount().getId());
			account.setUsername(actorForm.getUserAccountuser());
			account.setPassword(actorForm.getUserAccountpassword());
			brotherhood.setUserAccount(account);
		}
		this.validator.validate(brotherhood.getUserAccount(), binding);
		this.validator.validate(brotherhood, binding);
		return brotherhood;
	}
}
