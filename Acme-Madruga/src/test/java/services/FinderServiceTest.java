
package services;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Finder;

public class FinderServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private FinderService		finderService;

	@Autowired
	private ProcessionService	processionService;


	// Tests ----------------------------------------------

	@Test
	public void testCreate() {
		super.authenticate("finder1");

		final Finder finder = this.finderService.create();
		Assert.notNull(finder);

		super.unauthenticate();
	}

	@Test
	public void testFindOne() {
		final Finder finder = this.finderService.findOne(super.getEntityId("member1"));
		Assert.notNull(finder);
	}

	@Test
	public void testSave() {
		super.authenticate("member1");

		final Finder finder = this.finderService.create();

		finder.setKeyword("This");

		final Finder res = this.finderService.save(finder);
		Assert.notNull(res);

		super.unauthenticate();
	}
	//	@Test
	//	public void testDelete() {
	//		super.authenticate("member1");
	//
	//		Finder finder;
	//		finder = this.finderService.findOne(super.getEntityId("member1"));
	//		this.finderService.delete(finder);
	//
	//		super.unauthenticate();
	//	}
	//
	//	@Test
	//	public void testFindAll() {
	//		final Collection<Finder> finder = this.finderService.findAll();
	//		Assert.notNull(finder);
	//	}
	//
	//	@Test
	//	public void testFind() {
	//		super.authenticate("member1");
	//
	//		final Finder finder = this.finderService.create();
	//		final Collection<Procession> processions = this.processionService.findAll();
	//		finder.setKeyword("This");
	//		processions.addAll();
	//
	//	}
}
