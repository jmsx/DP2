
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import security.Authority;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Actor;
import forms.ActorFrom;

@Service
@Transactional
public class UserAccountService {

	@Autowired
	private UserAccountRepository	userAccountRepository;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private Validator				validator;


	public UserAccount create() {
		return new UserAccount();
	}

	public Collection<UserAccount> findAll() {
		final Collection<UserAccount> result = this.userAccountRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public UserAccount findOne(final int id) {
		Assert.isTrue(id != 0);
		final UserAccount result = this.userAccountRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public UserAccount save(final UserAccount ua) {
		Assert.notNull(ua);
		return this.userAccountRepository.save(ua);
	}

	public UserAccount reconstruct(final ActorFrom actorForm, final BindingResult bind) {
		UserAccount ua;
		if (actorForm.getId() == 0) {
			ua = this.create();
			final Collection<Authority> authorities = new ArrayList<>();
			final Authority auth = new Authority();
			auth.setAuthority(Authority.ADMIN);
			authorities.add(auth);
			ua.setAuthorities(authorities);
			ua.setUsername(actorForm.getUserAccountuser());
			ua.setPassword(actorForm.getUserAccountpassword());
		} else {
			final Actor actor = this.actorService.findOne(actorForm.getId());
			ua = this.userAccountRepository.findOne(actor.getUserAccount().getId());
			ua.setUsername(actorForm.getUserAccountuser());
			ua.setPassword(actorForm.getUserAccountpassword());

		}
		// TODO
		// this.validator.validate(ua, bind);
		return ua;
	}
}
