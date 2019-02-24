
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import services.BrotherhoodService;
import services.ConfigurationParametersService;
import services.UserAccountService;
import domain.Brotherhood;
import forms.ActorFrom;

@Controller
@RequestMapping("/brotherhood")
public class BrotherhoodController extends AbstractController {

	@Autowired
	private BrotherhoodService				brotherhoodService;

	@Autowired
	private UserAccountService				userAccountService;

	@Autowired
	private ConfigurationParametersService	configurationParametersService;


	// CONSTRUCTOR -----------------------------------------------------------

	public BrotherhoodController() {
		super();
	}

	// CREATE  ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result = new ModelAndView();
		final ActorFrom brotherhood = new ActorFrom();
		result = new ModelAndView("brotherhood/edit");
		result.addObject("actorForm", brotherhood);
		return result;
	}

	// DISPLAY  ---------------------------------------------------------------		

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result;
		Brotherhood brotherhood;

		brotherhood = this.brotherhoodService.findByPrincipal();

		if (brotherhood != null) {
			result = new ModelAndView("brotherhood/display");
			result.addObject("botherhood", brotherhood);

			final String banner = this.configurationParametersService.findBanner();
			result.addObject("banner", banner);
		} else
			result = new ModelAndView("redirect:/misc/403.jsp");

		return result;
	}

	// SAVE  ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final ActorFrom actorForm, final BindingResult binding) {
		ModelAndView result;
		result = new ModelAndView("brotherhood/edit");
		Brotherhood brotherhood;
		if (binding.hasErrors())
			result.addObject("errors", binding.getFieldErrors());
		else {
			brotherhood = this.brotherhoodService.reconstruct(actorForm, binding);
			UserAccount ua = brotherhood.getUserAccount();
			ua = this.userAccountService.save(ua);
			brotherhood.setUserAccount(ua);
			brotherhood = this.brotherhoodService.save(brotherhood);
			result.addObject("alert", true);
			result.addObject("actorForm", brotherhood);
		}
		return result;
	}

}
