
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

import services.FinderService;
import services.PositionService;
import controllers.AbstractController;
import domain.Position;

@Controller
@RequestMapping(value = "/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	@Autowired
	private PositionService	positionService;

	@Autowired
	private FinderService	finderService;


	@RequestMapping(value = "/chart", method = RequestMethod.GET)
	public ModelAndView chart() {
		final ModelAndView result;

		final Map<Position, Long> positionsFrequency = this.positionService.getPositionsFrequency();
		Assert.notNull(positionsFrequency);
		final List<String> positions = new ArrayList<String>();
		final List<Long> frequencies = new ArrayList<Long>();
		for (final Map.Entry<Position, Long> entry : positionsFrequency.entrySet()) {
			positions.add(entry.getKey().getNameEnglish() + "/" + entry.getKey().getNameSpanish());
			frequencies.add(entry.getValue());
		}

		final List<String> positions2 = new ArrayList<String>();
		final List<Integer> frequencies2 = new ArrayList<Integer>();
		positions2.add("hola");
		positions2.add("holaa");
		frequencies2.add(1);
		frequencies2.add(5);

		result = new ModelAndView("dashboard/chart"); //lleva al list.jsp
		//result.addObject("positions", positions);
		//result.addObject("frequencies", frequencies);
		result.addObject("positions2", positions);
		result.addObject("frequencies2", frequencies);
		result.addObject("requestURI", "dashboard/admnistrator/chart.do");

		return result;

	}

	@RequestMapping(value = "/statistics", method = RequestMethod.GET)
	public ModelAndView statistics() {
		final ModelAndView result;

		final Double averageResults = this.finderService.getAverageFinderResults();
		final Integer maxResults = this.finderService.getMaxFinderResults();
		final Integer minResults = this.finderService.getMinFinderResults();
		final Double desviationResults = this.finderService.getDesviationFinderResults();
		final Double ratioFinders = this.finderService.getRatioEmptyFinders();

		result = new ModelAndView("dashboard/statistics"); //lleva al list.jsp
		result.addObject("requestURI", "dashboard/admnistrator/statistics.do");
		result.addObject("averageResults", averageResults);
		result.addObject("minResults", minResults);
		result.addObject("maxResults", maxResults);
		result.addObject("desviationResults", desviationResults);
		result.addObject("ratioFinders", ratioFinders);

		return result;

	}
}
