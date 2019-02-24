
package controllers.brotherhood;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import services.BrotherhoodService;
import services.UserAccountService;
import controllers.AbstractController;
import domain.Brotherhood;
import forms.ActorFrom;

@Controller
@RequestMapping("/brotherhood")
public class BrotherhoodController extends AbstractController {

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private UserAccountService	userAccountService;


	// CONSTRUCTOR -----------------------------------------------------------

	public BrotherhoodController() {
		super();
	}

	// CreateEditModelAndView ---------------------------------------------------------------		

	// REGISTER
	protected ModelAndView createEditModelAndView(final Brotherhood brotherhood) {
		ModelAndView result;

		result = this.createEditModelAndView(brotherhood, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Brotherhood brotherhood, final String message) {
		ModelAndView result;

		result = new ModelAndView("brotherhood/register");
		result.addObject("brotherhood", brotherhood);
		result.addObject("message", message);

		return result;

	}

	//EDIT
	protected ModelAndView createEditModelAndView2(final Brotherhood brotherhood) {
		ModelAndView result;

		result = this.createEditModelAndView2(brotherhood, null);

		return result;
	}

	protected ModelAndView createEditModelAndView2(final Brotherhood brotherhood, final String message) {
		ModelAndView result;

		result = new ModelAndView("brotherhood/edit");
		result.addObject("brotherhood", brotherhood);
		result.addObject("message", message);

		return result;

	}

	// CREATE  ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Brotherhood brotherhood = this.brotherhoodService.create();
		result = this.createEditModelAndView(brotherhood);
		return result;
	}

	// VIEW  ---------------------------------------------------------------		

	//	@RequestMapping(value = "/display", method = RequestMethod.GET)
	//	 public ModelAndView view(@RequestParam final int brotherhoodId) {
	//		ModelAndView result;
	//		final Brotherhood brotherhood = this.brotherhoodService.findOne(brotherhoodId);
	//		result = new ModelAndView("brotherhood/display");
	//		
	//		result.addObject("", brotherhood.getTitle());
	//		result.addObject("", brotherhood.get)
	//	 }

	// REGISTER  ---------------------------------------------------------------		

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView register(@Valid final Brotherhood brotherhood, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(brotherhood);
		else
			try {
				this.brotherhoodService.registerAsBrotherhood(brotherhood);
				result = new ModelAndView("administrator/success");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(brotherhood, "general.commit.error");
			}
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
