
package controllers.member;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationParametersService;
import services.ProcessionService;
import controllers.AbstractController;
import domain.Procession;

@Controller
@RequestMapping("/procession/member")
public class ProcessionMemberController extends AbstractController {

	@Autowired
	private ProcessionService				processionService;

	@Autowired
	private ConfigurationParametersService	configurationParametersService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int processionId) {
		final ModelAndView result;
		Procession procession;

		procession = this.processionService.findOne(processionId);

		if (procession != null && procession.getMode().equals("FINAL")) {
			result = new ModelAndView("procession/display");
			result.addObject("procession", procession);
			result.addObject("rol", "member");

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
		Collection<Procession> memberProcessions;

		processions = this.processionService.findAllFinalMode();
		memberProcessions = this.processionService.findAllByPrincipal();

		result = new ModelAndView("procession/list");
		result.addObject("processions", processions);
		result.addObject("rol", "member");
		result.addObject("memberProcessions", memberProcessions);
		result.addObject("requetURI", "procession/member/list.do");

		final String banner = this.configurationParametersService.findBanner();
		result.addObject("banner", banner);

		return result;
	}

}
