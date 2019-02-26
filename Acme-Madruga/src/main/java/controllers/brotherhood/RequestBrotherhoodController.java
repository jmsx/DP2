
package controllers.brotherhood;

import java.util.Collection;

import javax.validation.Valid;

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
import domain.Procession;
import domain.Request;

@Controller
@RequestMapping("/request/brotherhood")
public class RequestBrotherhoodController extends AbstractController {

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
		final String rol = "brotherhood";

		requests = this.requestService.findAll();

		result = new ModelAndView("request/list");
		result.addObject("requests", requests);
		result.addObject("rol", rol);

		final String banner = this.configurationParametersService.findBanner();
		result.addObject("banner", banner);

		return result;
	}

	// Edition --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int requestId, final String mostrarTarjetas) {
		ModelAndView result;
		Request request;

		request = this.requestService.findOne(requestId);
		if (request == null)
			result = new ModelAndView("redirect:/misc/403.jsp");
		else
			result = this.createEditModelAndView(request);

		return result;
	}
	// Save --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Request request, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(request);
		else
			try {
				result = new ModelAndView("redirect:/request/brotherhood/list.do");
				final Request retrieved = this.requestService.findOne(request.getId());
				if (!retrieved.getStatus().equals(request.getStatus()))
					this.requestService.save(request);
				else
					result = this.createEditModelAndView(request, "request.commit.error");
			} catch (final Throwable oops) {
				String errorMessage = "request.commit.error";
				if (oops.getMessage().contains("message.error"))
					errorMessage = oops.getMessage();
				result = this.createEditModelAndView(request, errorMessage);
			}

		final String banner = this.configurationParametersService.findBanner();
		result.addObject("banner", banner);
		return result;
	}
	// Ancillary methods --------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Request request) {
		return this.createEditModelAndView(request, null);
	}

	protected ModelAndView createEditModelAndView(final Request request, final String messageCode) {
		ModelAndView result;

		final Collection<Procession> processions;
		final String rol = "brotherhood";

		processions = this.processionService.findAll();

		result = new ModelAndView("request/edit");
		result.addObject("request", request);
		result.addObject("processions", processions);
		result.addObject("rol", rol);
		result.addObject("requestURI", "request/brotherhood/edit.do");
		result.addObject("message", messageCode);
		// the message code references an error message or null

		final String banner = this.configurationParametersService.findBanner();
		result.addObject("banner", banner);
		return result;
	}
}
