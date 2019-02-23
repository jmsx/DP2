
package services;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Position;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class PositionServiceTest extends AbstractTest {

	//Services
	@Autowired
	private PositionService		positionService;

	@Autowired
	private EnrolmentService	enrolmentService;

	@Autowired
	private ActorService		actorService;


	//	@Test
	//	public void testCreateStringString() {
	//		super.authenticate("admin1");
	//		final Position pos = this.positionService.create("Hola", "Hello");
	//		Assert.notNull(pos);
	//	}
	//
	//	@Test
	//	public void testFindAll() {
	//		Assert.isTrue(this.positionService.findAll().size() > 0);
	//	}
	//
	//	@Test
	//	public void testFindOne() {
	//		final List<Position> positions = new ArrayList<>(this.positionService.findAll());
	//		Assert.isTrue(positions.size() > 0);
	//		Assert.notNull(this.positionService.findOne(positions.get(0).getId()));
	//	}
	//
	//	@Test
	//	public void testSave() {
	//		super.authenticate("admin1");
	//		final Position position = this.positionService.create("Hola", "Hello");
	//		final Position saved = this.positionService.save(position);
	//		Assert.notNull(saved);
	//	}
	//
	@Test
	public void testDelete() {
		super.authenticate("admin1");
		//		final Actor principal = this.actorService.findByPrincipal();
		//		Assert.isTrue(this.actorService.checkAuthority(principal, Authority.ADMIN));
		final List<Position> allPositions = (List<Position>) this.positionService.findAll();
		Assert.notEmpty(allPositions);
		final int id = allPositions.get(1).getId();
		final Position pos = this.positionService.findOne(id);
		Assert.notNull(pos);
		this.positionService.delete(pos);
		Assert.isTrue(!this.positionService.findAll().contains(pos), "La posicion aun existe");
	}
	//
	// Devuelve correctamente la lista de positions usadas
	//	@Test
	//	public void testAllPositionUsed() {
	//		super.authenticate("admin1");
	//		final Collection<Position> test = this.positionService.AllPositionUsed();
	//		Assert.notEmpty(test);
	//	}

}
