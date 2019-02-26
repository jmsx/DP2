
package controllers;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AreaService;
import services.BrotherhoodService;
import services.ConfigurationParametersService;
import services.MemberService;
import services.UserAccountService;
import domain.Area;
import domain.Brotherhood;
import domain.Member;
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

	@Autowired
	private AreaService						areaService;

	@Autowired
	private MemberService					memberService;


	// CONSTRUCTOR -----------------------------------------------------------

	public BrotherhoodController() {
		super();
	}

	// CREATEEDITMODELANDVIEW -----------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Brotherhood brotherhood) {
		ModelAndView result;

		result = this.createEditModelAndView(brotherhood, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Brotherhood brotherhood, final String messageCode) {
		final ModelAndView result;
		final List<Area> libres = (List<Area>) this.areaService.AllAreasFree();

		result = new ModelAndView("brotherhood/edit");
		result.addObject("brotherhood", brotherhood);
		result.addObject("areas", libres);

		result.addObject("message", messageCode);
		final String banner = this.configurationParametersService.findBanner();
		result.addObject("banner", banner);

		return result;
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
	public ModelAndView displayPrincipal() {
		final ModelAndView result;

		Brotherhood brotherhood;
		brotherhood = this.brotherhoodService.findByPrincipal();

		if (brotherhood != null) {
			result = new ModelAndView("brotherhood/display");
			result.addObject("brotherhood", brotherhood);

			final String banner = this.configurationParametersService.findBanner();
			result.addObject("banner", banner);
		} else
			result = new ModelAndView("redirect:/misc/403.jsp");

		return result;
	}

	@RequestMapping(value = "/displayTabla", method = RequestMethod.GET)
	public ModelAndView displayTabla(@RequestParam final int brotherhoodId) {
		final ModelAndView result;

		Brotherhood brotherhood;
		brotherhood = this.brotherhoodService.findOne(brotherhoodId);

		if (brotherhood != null) {
			result = new ModelAndView("brotherhood/display");
			result.addObject("brotherhood", brotherhood);

			final String banner = this.configurationParametersService.findBanner();
			result.addObject("banner", banner);
		} else
			result = new ModelAndView("redirect:/misc/403.jsp");

		return result;
	}

	// SAVE  ---------------------------------------------------------------		
	//
	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	//	public ModelAndView edit(final ActorFrom actorForm, final BindingResult binding) {
	//		ModelAndView result;
	//		result = new ModelAndView("brotherhood/edit");
	//		Brotherhood brotherhood;
	//		if (binding.hasErrors())
	//			result.addObject("errors", binding.getFieldErrors());
	//		else {
	//			brotherhood = this.brotherhoodService.reconstruct(actorForm, binding);
	//			UserAccount ua = brotherhood.getUserAccount();
	//			ua = this.userAccountService.save(ua);
	//			brotherhood.setUserAccount(ua);
	//			brotherhood = this.brotherhoodService.save(brotherhood);
	//			result.addObject("alert", true);
	//			result.addObject("actorForm", brotherhood);
	//		}
	//		return result;
	//	}

	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	//	public ModelAndView save(final ActorFrom actorForm, final BindingResult binding) {
	//		ModelAndView result = new ModelAndView();
	//		Brotherhood brotherhood = new Brotherhood();
	//
	//		if (binding.hasErrors())
	//			result = this.createEditModelAndView(brotherhood);
	//		else
	//			try {
	//				brotherhood = this.brotherhoodService.reconstruct(actorForm, binding);
	//				UserAccount ua = brotherhood.getUserAccount();
	//				ua = this.userAccountService.save(ua);
	//				brotherhood.setUserAccount(ua);
	//				brotherhood = this.brotherhoodService.save(brotherhood);
	//				result.addObject("alert", true);
	//				result.addObject("actorForm", brotherhood);
	//				result = new ModelAndView("redirect:display.do");
	//				final String banner = this.configurationParametersService.findBanner();
	//				result.addObject("banner", banner);
	//			} catch (final Throwable oops) {
	//				result = this.createEditModelAndView(brotherhood, "brotherhood.commit.error");
	//			}
	//		return result;
	//	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Brotherhood brotherhood, final BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(brotherhood);
		else
			try {
				this.brotherhoodService.save(brotherhood);
				result = new ModelAndView("redirect:display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(brotherhood, "fixUpTask.commit.error");
			}

		return result;
	}

	// LIST MY BROTHERHOODS  ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Member member = this.memberService.findByPrincipal();
		final Collection<Brotherhood> brotherhoods;

		brotherhoods = this.brotherhoodService.findAllBrotherHoodByMember();

		result = new ModelAndView("brotherhood/list");
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("member", member);
		result.addObject("ok", false);
		result.addObject("requetURI", "brotherhood/list.do");

		final String banner = this.configurationParametersService.findBanner();
		result.addObject("banner", banner);

		return result;
	}

	// LIST ALL BROTHERHOOD FREE  ---------------------------------------------------------------		

	@RequestMapping(value = "/allBrotherhoodsFree", method = RequestMethod.GET)
	public ModelAndView allBrotherhoodsFree() {
		final ModelAndView result;
		final Member member = this.memberService.findByPrincipal();
		final Collection<Brotherhood> brotherhoods;

		brotherhoods = this.brotherhoodService.AllBrotherhoodsFree();

		result = new ModelAndView("brotherhood/list");
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("member", member);
		result.addObject("ok", true);
		result.addObject("requetURI", "brotherhood/allBrotherhoodsFree.do");

		final String banner = this.configurationParametersService.findBanner();
		result.addObject("banner", banner);

		return result;
	}

	// LIST ALL BROTHERHOODS  ---------------------------------------------------------------		

	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView listAll() {
		final ModelAndView result;
		final Member member = this.memberService.findByPrincipal();
		final Collection<Brotherhood> brotherhoods;

		brotherhoods = this.brotherhoodService.findAll();

		result = new ModelAndView("brotherhood/list");
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("member", member);
		result.addObject("requetURI", "brotherhood/listAll.do");
		result.addObject("ok", false);

		final String banner = this.configurationParametersService.findBanner();
		result.addObject("banner", banner);

		return result;
	}

	// DROP OUT  ---------------------------------------------------------------		

}
