
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Message;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository					actorRepository;

	@Autowired
	private FolderService					folderService;

	@Autowired
	private UserAccountService				userAccountService;

	@Autowired
	private AdministratorService			administratorService;

	@Autowired
	private ConfigurationParametersService	configurationParametersService;

	@Autowired
	private MessageService					messageService;


	public Actor create() {
		return new Actor();
	}

	public Collection<Actor> findAll() {
		final Collection<Actor> result = this.actorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Actor findOne(final int id) {
		Assert.isTrue(id != 0);
		final Actor result = this.actorRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public int countByMessageId(final Integer id) {
		Assert.isTrue(id != 0);
		return this.actorRepository.countByMessageId(id);
	}

	public Actor findByUserId(final int id) {
		Assert.isTrue(id != 0);
		return this.actorRepository.findByUserId(id);
	}

	public Actor findByPrincipal() {
		final UserAccount user = LoginService.getPrincipal();
		Assert.notNull(user);

		final Actor a = this.findByUserId(user.getId());
		Assert.notNull(a);

		return a;
	}

	/**
	 * Check if a user account authorities of an actor contains a specific authority, checking that this actor isn't banned
	 * 
	 * @param actor
	 *            Actor whose authorities will be changed
	 * @param auth
	 *            Authority you want to include
	 * @return True if the actor authorities contains the authority pass as a parameter
	 * @author a8081
	 * */
	public boolean checkAuthority(final Actor a, final String auth) {
		Assert.notNull(auth);

		final UserAccount user = a.getUserAccount();
		final Actor retrieved = this.findByUserId(user.getId());
		Assert.notNull(retrieved);

		final Collection<Authority> auths = user.getAuthorities();
		Assert.notEmpty(auths);

		final Authority banned = new Authority();
		banned.setAuthority(Authority.BANNED);
		Assert.isTrue(!auths.contains(banned));

		final Authority newAuth = new Authority();
		newAuth.setAuthority(auth);

		return auths.contains(newAuth);
	}

	/**
	 * Initializes the atributes common to every actor (system folders, user account and its authority).
	 * 
	 * @param authority
	 *            String of the authority you want the actor to have
	 * @param actor
	 *            The new actor
	 * @return The new actor with its atributes initialized
	 * @author a8081
	 * */
	public Actor setNewActor(final String authority, final Actor actor) {
		final UserAccount userAccount = this.userAccountService.create();
		final Collection<Authority> authorities = new ArrayList<>();
		final Authority auth = new Authority();
		auth.setAuthority(authority);

		if (!authorities.contains(auth))
			authorities.add(auth);
		userAccount.setAuthorities(authorities);
		final UserAccount saved = this.userAccountService.save(userAccount);
		actor.setUserAccount(saved);

		this.folderService.setFoldersByDefault(actor);

		actor.setSpammer(false);

		return actor;
	}

	// =========================================== Admin =================================================

	public void banActor(final Actor a) {
		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);
		Assert.isTrue(a.getSpammer());

		this.administratorService.findByPrincipal();

		final UserAccount user = a.getUserAccount();
		final Collection<Authority> auths = user.getAuthorities();
		final Authority auth = new Authority();

		auth.setAuthority(Authority.BANNED);
		auths.add(auth);
		user.setAuthorities(auths);
		this.userAccountService.save(user);

		this.update(a);
	}

	public void unbanActor(final Actor a) {
		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);

		this.administratorService.findByPrincipal();

		final UserAccount user = a.getUserAccount();
		final Collection<Authority> auths = user.getAuthorities();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.BANNED);
		Assert.isTrue(auths.contains(auth));

		auths.remove(auth);
		user.setAuthorities(auths);
		this.userAccountService.save(user);

		this.update(a);
	}

	public Collection<Actor> findAllSpammers() {
		this.administratorService.findByPrincipal();
		final Collection<Actor> result = this.actorRepository.findAllSpammer();
		Assert.notNull(result);
		return result;
	}

	public void checkForSpamWords(final Actor a) {
		final Collection<String> words = new ArrayList<>();

		words.add(a.getAddress());
		words.add(a.getEmail());
		words.add(a.getMiddleName());
		words.add(a.getName());
		words.add(a.getSurname());

		if (this.configurationParametersService.checkForSpamWords(words))
			a.setSpammer(true);
	}

	public double computeScore(final Actor a) {
		final boolean isBrotherhood = this.checkAuthority(a, Authority.BROTHERHOOD);
		final boolean isMember = this.checkAuthority(a, Authority.MEMBER);
		Assert.isTrue(isMember || isBrotherhood);

		Assert.notNull(a);
		int p = 0;
		int n = 0;

		this.administratorService.findByPrincipal();
		final Collection<String> pwords = this.configurationParametersService.findPositiveWords();
		final Collection<String> nwords = this.configurationParametersService.findNegativeWords();

		final Collection<String> messagesStrings = new ArrayList<>();
		for (final Message m : this.messageService.findActorMessages(a)) {
			final String body = m.getBody().toLowerCase();
			final String subject = m.getSubject().toLowerCase();
			messagesStrings.add(subject);
			messagesStrings.add(body);
		}

		for (final String pword : pwords)
			for (final String comment : messagesStrings) {
				final boolean bool = comment.matches(".*" + pword + ".*");
				if (bool)
					p++;
			}

		for (final String nword : nwords)
			for (final String comment : messagesStrings) {
				final boolean bool = comment.matches(".*" + nword + ".*");
				if (bool)
					n++;
			}

		final int min = -n;
		final int max = p;
		final int range = max - min;
		final int res = p - n;
		final double normRes;

		if (range != 0)
			normRes = (2 * ((res - min) / range)) - 1;
		else
			normRes = 0;

		return normRes;
	}

	/**
	 * An administrator can ban system actors, so he must be able to modify them. That's an ancilliary method to "banActor" and "unbanActor", it checks administrator only changes actor's spammer attribute.
	 * 
	 * @param a
	 *            Actor who will be modified
	 * @author a8081
	 * */
	public Actor update(final Actor a) {
		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);
		this.administratorService.findByPrincipal();
		Assert.isTrue(this.equalsLessSpammer(this.findByUserId(a.getUserAccount().getId()), a));
		return this.actorRepository.save(a);
	}

	private boolean equalsLessSpammer(final Actor a1, final Actor a2) {
		if (a1.getAddress() == null) {
			if (a2.getAddress() != null)
				return false;
		} else if (!a1.getAddress().equals(a2.getAddress()))
			return false;
		if (a1.getEmail() == null) {
			if (a2.getEmail() != null)
				return false;
		} else if (!a1.getEmail().equals(a2.getEmail()))
			return false;
		if (a1.getMiddleName() == null) {
			if (a2.getMiddleName() != null)
				return false;
		} else if (!a1.getMiddleName().equals(a2.getMiddleName()))
			return false;
		if (a1.getName() == null) {
			if (a2.getName() != null)
				return false;
		} else if (!a1.getName().equals(a2.getName()))
			return false;
		if (a1.getPhone() == null) {
			if (a2.getPhone() != null)
				return false;
		} else if (!a1.getPhone().equals(a2.getPhone()))
			return false;
		if (a1.getPhoto() == null) {
			if (a2.getPhoto() != null)
				return false;
		} else if (!a1.getPhoto().equals(a2.getPhoto()))
			return false;
		if (a1.getSurname() == null) {
			if (a2.getSurname() != null)
				return false;
		} else if (!a1.getSurname().equals(a2.getSurname()))
			return false;
		return true;
	}

}
