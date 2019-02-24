
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationParametersService;
import services.RequestService;
import domain.Request;

@Controller
@RequestMapping("/request")
public class RequestController extends AbstractController {

	@Autowired
	private RequestService					requestService;

	@Autowired
	private ConfigurationParametersService	configurationParametersService;


	// Listing --------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Request> requests;

		requests = this.requestService.findAll();

		result = new ModelAndView("request/list");
		result.addObject("requests", requests);
		result.addObject("requestURI", "request/list.do");
		final String banner = this.configurationParametersService.findBanner();
		result.addObject("banner", banner);

		return result;
	}

	// Display --------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int requestId) {
		ModelAndView result;
		Request request;

		request = this.requestService.findOne(requestId);
		if (request == null)
			result = new ModelAndView("redirect:/misc/403.jsp");
		else {
			result = new ModelAndView("request/display");
			result.addObject("request", request);
			final String banner = this.configurationParametersService.findBanner();
			result.addObject("banner", banner);
		}
		return result;
	}
}
