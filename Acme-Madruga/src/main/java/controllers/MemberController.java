
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.UserAccount;
import services.BrotherhoodService;
import services.ConfigurationParametersService;
import services.EnrolmentService;
import services.MemberService;
import services.UserAccountService;
import services.auxiliary.RegisterService;
import domain.Brotherhood;
import domain.Member;
import forms.ActorFrom;

@Controller
@RequestMapping("/member")
public class MemberController extends AbstractController {

	@Autowired
	private MemberService					memberService;

	@Autowired
	private BrotherhoodService				brotherhoodService;

	@Autowired
	private UserAccountService				userAccountService;

	@Autowired
	private ConfigurationParametersService	configurationParametersService;

	@Autowired
	private EnrolmentService				enrolmentService;

	@Autowired
	private BrotherhoodController			brotherhoodController;

	@Autowired
	private RegisterService					registerService;


	// Constructors -----------------------------------------------------------
	public MemberController() {
		super();
	}

	// Create -----------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result = new ModelAndView();
		final ActorFrom member = new ActorFrom();
		result = new ModelAndView("member/edit");
		result.addObject("actorForm", member);
		return result;
	}

	// Display -----------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result;
		final Member member = this.memberService.findByPrincipal();
		if (member != null) {
			result = new ModelAndView("member/display");
			result.addObject("member", member);
			final String banner = this.configurationParametersService.findBanner();
			result.addObject("banner", banner);
		} else
			result = new ModelAndView("redirect:/misc/403.jsp");

		return result;

	}

	@RequestMapping(value = "/displayTabla", method = RequestMethod.GET)
	public ModelAndView displayTabla(@RequestParam final int memberId) {
		final ModelAndView result;

		Member member;
		member = this.memberService.findOne(memberId);

		if (member != null) {
			result = new ModelAndView("member/display");
			result.addObject("member", member);

			final String banner = this.configurationParametersService.findBanner();
			result.addObject("banner", banner);
		} else
			result = new ModelAndView("redirect:/misc/403.jsp");

		return result;
	}

	// Save -----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ActorFrom actorForm, final BindingResult binding) {
		ModelAndView result;
		result = new ModelAndView("administrator/edit");
		Member member;
		if (binding.hasErrors()) {
			result.addObject("errors", binding.getAllErrors());
			result.addObject("actorForm", actorForm);
		} else
			try {
				final UserAccount ua = this.userAccountService.reconstruct(actorForm, Authority.MEMBER);
				member = this.memberService.reconstruct(actorForm);
				member.setUserAccount(ua);
				this.registerService.saveMember(member, binding);
				result.addObject("alert", "member.edit.correct");
				result.addObject("actorForm", actorForm);
			} catch (final Throwable e) {
				if (e.getMessage().contains("username is register"))
					result.addObject("alert", "member.edit.usernameIsUsed");
				result.addObject("errors", binding.getAllErrors());
				result.addObject("actorForm", actorForm);
			}
		return result;
	}

	// LIST MY MEMBERS  ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Brotherhood brotherhood = this.brotherhoodService.findByPrincipal();
		final Collection<Member> members;

		members = this.memberService.allMembersFromBrotherhood();

		result = new ModelAndView("member/list");
		result.addObject("members", members);
		result.addObject("brotherhood", brotherhood);
		result.addObject("ok", true);
		result.addObject("requetURI", "member/list.do");

		final String banner = this.configurationParametersService.findBanner();
		result.addObject("banner", banner);

		return result;
	}

	// LEAVE  ---------------------------------------------------------------		

	@RequestMapping(value = "/leave", method = RequestMethod.GET)
	public ModelAndView leave(@RequestParam final int brotherhoodId) {
		final ModelAndView result;
		final Brotherhood brotherhood = this.brotherhoodService.findOne(brotherhoodId);

		this.enrolmentService.leave(brotherhood);

		result = this.brotherhoodController.list();

		final String banner = this.configurationParametersService.findBanner();
		result.addObject("banner", banner);

		return result;
	}

}
