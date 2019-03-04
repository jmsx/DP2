
package controllers;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.UserAccount;
import services.AreaService;
import services.BrotherhoodService;
import services.ConfigurationParametersService;
import services.EnrolmentService;
import services.MemberService;
import services.UserAccountService;
import services.auxiliary.RegisterService;
import domain.Area;
import domain.Brotherhood;
import domain.Member;
import forms.BrotherhoodForm;

@Controller
@RequestMapping("/brotherhood")
public class BrotherhoodController extends AbstractController {

	@Autowired
	private BrotherhoodService				brotherhoodService;

	@Autowired
	private ConfigurationParametersService	configurationParametersService;

	@Autowired
	private AreaService						areaService;

	@Autowired
	private MemberService					memberService;

	@Autowired
	private EnrolmentService				enrolmentService;

	@Autowired
	private MemberController				memberController;

	@Autowired
	private RegisterService					registerService;

	@Autowired
	private UserAccountService				userAccountService;


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
		final BrotherhoodForm brotherhoodForm = new BrotherhoodForm();
		result = new ModelAndView("brotherhood/edit");
		result.addObject("brotherhoodForm", brotherhoodForm);
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
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final BrotherhoodForm brotherhoodForm, final BindingResult binding) {
		ModelAndView result;
		result = new ModelAndView("brotherhood/edit");
		Brotherhood brotherhood;
		if (binding.hasErrors()) {
			result.addObject("errors", binding.getAllErrors());
			result.addObject("brotherhoodForm", brotherhoodForm);
		} else
			try {
				final UserAccount ua = this.userAccountService.reconstruct(brotherhoodForm, Authority.BROTHERHOOD);
				brotherhood = this.brotherhoodService.reconstruct(brotherhoodForm);
				brotherhood.setUserAccount(ua);
				this.registerService.saveBrotherhood(brotherhood, binding);
				result.addObject("alert", "brotherhood.edit.correct");
				result.addObject("brotherhoodForm", brotherhoodForm);
			} catch (final Throwable e) {
				if (e.getMessage().contains("username is register"))
					result.addObject("alert", "brotherhood.edit.usernameIsUsed");
				result.addObject("errors", binding.getAllErrors());
				result.addObject("brotherhoodForm", brotherhoodForm);
			}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		result = new ModelAndView("brotherhood/edit");
		final Brotherhood brotherhood = this.brotherhoodService.findByPrincipal();
		final BrotherhoodForm actor = this.registerService.inyect(brotherhood);
		result.addObject("brotherhoodForm", actor);
		return result;

	}

	// LIST MY BROTHERHOODS  ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Member member = this.memberService.findByPrincipal();
		final Collection<Brotherhood> brotherhoods;

		brotherhoods = this.brotherhoodService.findAllMyBrotherHoodByMember();

		final String lang = LocaleContextHolder.getLocale().getLanguage();

		result = new ModelAndView("brotherhood/list");
		result.addObject("lang", lang);
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("member", member);
		result.addObject("ok", false);
		result.addObject("leave", true);
		result.addObject("displayEnrolment", true);
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

		final String lang = LocaleContextHolder.getLocale().getLanguage();

		result = new ModelAndView("brotherhood/list");
		result.addObject("lang", lang);
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("member", member);
		result.addObject("ok", true);
		result.addObject("displayEnrolment", false);
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

		final String lang = LocaleContextHolder.getLocale().getLanguage();

		result = new ModelAndView("brotherhood/list");
		result.addObject("lang", lang);
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("member", member);
		result.addObject("requetURI", "brotherhood/listAll.do");
		result.addObject("ok", false);

		final String banner = this.configurationParametersService.findBanner();
		result.addObject("banner", banner);

		return result;
	}

	// LIST BROTHERHOODS HAS BELONGED  ---------------------------------------------------------------		

	@RequestMapping(value = "/brotherhoodsHasBelonged", method = RequestMethod.GET)
	public ModelAndView brotherhoodsHasBelonged() {
		final ModelAndView result;
		final Member member = this.memberService.findByPrincipal();
		final Collection<Brotherhood> brotherhoods;

		brotherhoods = this.brotherhoodService.brotherhoodsHasBelonged();

		final String lang = LocaleContextHolder.getLocale().getLanguage();

		result = new ModelAndView("brotherhood/list");
		result.addObject("lang", lang);
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("member", member);
		result.addObject("requetURI", "brotherhood/brotherhoodsHasBelonged.do");

		final String banner = this.configurationParametersService.findBanner();
		result.addObject("banner", banner);

		return result;
	}

	// DROP OUT  ---------------------------------------------------------------		

	@RequestMapping(value = "/dropOut", method = RequestMethod.GET)
	public ModelAndView dropOut(@RequestParam final int memberId) {
		final ModelAndView result;
		final Member member = this.memberService.findOne(memberId);

		this.enrolmentService.dropOut(member);

		result = this.memberController.list();

		final String banner = this.configurationParametersService.findBanner();
		result.addObject("banner", banner);

		return result;
	}
}
