
package services;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Finder;

public class FinderServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private FinderService	finderService;


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
		final Finder finder = this.finderService.findOne(super.getEntityId("Member1"));
		Assert.notNull(finder);
	}

}
