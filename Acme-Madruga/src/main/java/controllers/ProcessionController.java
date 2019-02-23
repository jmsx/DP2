
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationParametersService;
import services.ProcessionService;
import domain.Procession;

@Controller
@RequestMapping("/procession")
public class ProcessionController {

	@Autowired
	private ProcessionService				processionService;

	@Autowired
	private ConfigurationParametersService	configurationParametersService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int processionId) {
		final ModelAndView result;
		Procession procession;

		procession = this.processionService.findOne(processionId);

		if (procession != null) {
			result = new ModelAndView("procession/display");
			result.addObject("procession", procession);

			final String banner = this.configurationParametersService.findBanner();
			result.addObject("banner", banner);
		} else
			result = new ModelAndView("redirect:/misc/403.jsp");

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Procession> processions;

		processions = this.processionService.findAll();

		result = new ModelAndView("procession/list");
		result.addObject("processions", processions);
		result.addObject("requetURI", "procession/list.do");

		final String banner = this.configurationParametersService.findBanner();
		result.addObject("banner", banner);

		return result;
	}
}
