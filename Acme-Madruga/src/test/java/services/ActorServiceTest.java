
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Actor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ActorServiceTest extends AbstractTest {

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;


	@Test
	public void testSave() {
		final Actor a = this.administratorService.create();
		a.setName("José");
		a.setMiddleName("");
		a.setSurname("Millán");
		a.setPhoto("");
		a.setEmail("millan@gmail.com");
		a.setPhone("");
		a.setAddress("");
		a.setSpammer(false);

		final Actor saved = this.actorService.setNewActor(Authority.MEMBER, a);
		final Collection<Actor> actors = this.actorService.findAll();

		Assert.isTrue(actors.contains(saved));
	}

	@Test
	public void testUpdate() {
		final Actor a = (Actor) this.actorService.findAll().toArray()[0];
		final String oldEmail = a.getEmail();
		final String username = a.getUserAccount().getUsername();
		super.authenticate(username);
		a.setEmail("another@gmail.com");
		final Actor saved = this.actorService.update(a);

		Assert.isTrue(!oldEmail.equals(saved.getEmail()));
	}

	@Test
	public void testFindAll() {
		final Collection<Actor> actors = this.actorService.findAll();
		Assert.notEmpty(actors);
	}

	@Test
	public void testFindOne() {
		final Actor actor = (Actor) this.actorService.findAll().toArray()[0];
		final Actor retrieved = this.actorService.findOne(actor.getId());
		Assert.isTrue(actor.equals(retrieved));
	}

	@Test
	public void testFindByUserId() {
		final Actor retrieved = (Actor) this.actorService.findAll().toArray()[0];
		final int id = retrieved.getUserAccount().getId();
		final Actor a = this.actorService.findByUserId(id);
		Assert.isTrue(a.equals(retrieved));
	}

	@Test
	public void testFindByPrincipalActor() {
		super.authenticate("member1");
		final Actor res = this.actorService.findByPrincipal();
		Assert.notNull(res);
		Assert.isTrue(res.getUserAccount().getUsername().equals("member1"));
	}

	@Test
	public void testCheckAuthority() {
		final Actor retrieved = (Actor) this.actorService.findAll().toArray()[0];
		final UserAccount ua = retrieved.getUserAccount();
		final Authority auth = (Authority) ua.getAuthorities().toArray()[0];
		final boolean bool = this.actorService.checkAuthority(retrieved, auth.getAuthority());

		Assert.isTrue(bool);
	}

	@Test
	public void testFindSuspiciousActors() {
		super.authenticate("admin1");
		final Collection<Actor> actors = this.actorService.findAllSpammers();
		final Collection<Actor> spammers = new ArrayList<>();
		for (final Actor a : actors)
			if (a.getSpammer())
				spammers.add(a);
		Assert.isTrue(spammers.size() == actors.size());
	}
	@Test
	public void testBanActor() {
		super.authenticate("admin1");
		List<Actor> spammers = new ArrayList<>();
		spammers = (List<Actor>) this.actorService.findAllSpammers();
		final Actor a = spammers.get(0);
		this.actorService.banActor(a);

		final UserAccount ua = a.getUserAccount();
		final Collection<Authority> auths = ua.getAuthorities();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.BANNED);

		Assert.isTrue(auths.contains(auth));
	}

	@Test
	public void testUnbanActor() {
		super.authenticate("admin1");
		List<Actor> suspicious = new ArrayList<>();
		suspicious = (List<Actor>) this.actorService.findAllSpammers();
		final Actor a = suspicious.get(0);
		this.actorService.banActor(a);

		this.actorService.unbanActor(a);
		final UserAccount ua = a.getUserAccount();
		final Collection<Authority> auths = ua.getAuthorities();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.BANNED);

		Assert.isTrue(!auths.contains(auth));
	}

	@Test
	public void testComputeScore() {
		super.authenticate("admin1");
		final Actor a = (Actor) this.actorService.findAll().toArray()[0];
		final double res = this.actorService.computeScore(a);
		Assert.isTrue(res <= 1.0 && res >= -1.0);
	}
}
