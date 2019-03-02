
package controllers.member;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationParametersService;
import services.ProcessionService;
import services.RequestService;
import controllers.AbstractController;
import domain.Request;

@Controller
@RequestMapping("/request/member")
public class RequestMemberController extends AbstractController {

	@Autowired
	private RequestService					requestService;

	@Autowired
	private ProcessionService				processionService;

	@Autowired
	private ConfigurationParametersService	configurationParametersService;


	// Listing --------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Request> requests;
		final String rol = "member";

		requests = this.requestService.findAll();

		result = new ModelAndView("request/list");
		result.addObject("requests", requests);
		result.addObject("rol", rol);
		result.addObject("theresProcessionsAvailable", !this.processionService.processionsAvailable().isEmpty());
		result.addObject("requestURI", "request/member/list.do");
		final String banner = this.configurationParametersService.findBanner();
		result.addObject("banner", banner);

		return result;
	}

	// Creation --------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final Integer processionId) {
		ModelAndView result;
		try {
			this.requestService.requestToProcession(processionId);
			result = new ModelAndView("redirect:/request/member/list.do");
			final String banner = this.configurationParametersService.findBanner();
			result.addObject("banner", banner);
		} catch (final Throwable oops) {
			String errorMessage = "request.create.error";
			if (oops.getMessage().contains("message.error"))
				errorMessage = oops.getMessage();
			result = new ModelAndView("redirect:/misc/error.jsp");
			result.addObject("errorMessage", errorMessage);
		}

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
			result.addObject("rol", "member");
			final String banner = this.configurationParametersService.findBanner();
			result.addObject("banner", banner);
		}
		return result;
	}

	@RequestMapping(value = "/displayByProcession", method = RequestMethod.GET)
	public ModelAndView displayByProcession(@RequestParam final int processionId) {
		ModelAndView result;
		Request request;

		// only members can use this method
		request = this.requestService.findByProcessionMember(processionId);
		if (request == null)
			result = new ModelAndView("redirect:/misc/403.jsp");
		else {
			result = new ModelAndView("request/display");
			result.addObject("request", request);
			result.addObject("rol", "member");
			final String banner = this.configurationParametersService.findBanner();
			result.addObject("banner", banner);
		}
		return result;
	}

	// Delete --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Request request, final BindingResult binding) {
		ModelAndView result;

		try {
			// service controlled that procession deleted has pending status
			this.requestService.delete(request);
			result = new ModelAndView("redirect:/member/list.do");
			final String banner = this.configurationParametersService.findBanner();
			result.addObject("banner", banner);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(request, "request.commit.error");
		}

		return result;
	}

	// Ancillary methods
	// --------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Request request) {
		return this.createEditModelAndView(request, null);
	}

	protected ModelAndView createEditModelAndView(final Request request, final String messageCode) {
		ModelAndView result;

		final String rol = "member";

		result = new ModelAndView("request/edit");
		result.addObject("request", request);
		result.addObject("processionsAvailable", this.processionService.processionsAvailable());
		result.addObject("rol", rol);
		result.addObject("message", messageCode);
		// the message code references an error message or null
		final String banner = this.configurationParametersService.findBanner();
		result.addObject("banner", banner);

		return result;
	}
}
