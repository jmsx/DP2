
package controllers.administrator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.PositionService;
import controllers.AbstractController;
import domain.Position;

@Controller
@RequestMapping(value = "/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	@Autowired
	private PositionService	positionService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		final Map<Position, Integer> positionsFrequency = this.positionService.getPositionsFrequency();
		Assert.notNull(positionsFrequency);
		final List<Position> positions = new ArrayList<Position>();
		final List<Integer> frequencies = new ArrayList<Integer>();
		for (final Map.Entry<Position, Integer> entry : positionsFrequency.entrySet()) {
			positions.add(entry.getKey());
			frequencies.add(entry.getValue());
		}

		final List<String> positions2 = new ArrayList<String>();
		final List<Integer> frequencies2 = new ArrayList<Integer>();
		positions2.add("hola");
		positions2.add("holaa");
		frequencies2.add(1);
		frequencies2.add(5);

		result = new ModelAndView("dashboard/list"); //lleva al list.jsp
		//result.addObject("positions", positions);
		//result.addObject("frequencies", frequencies);
		result.addObject("positions2", positions);
		result.addObject("frequencies2", frequencies);
		result.addObject("requestURI", "dashboard/admnistrator/list.do");

		return result;

	}
}
