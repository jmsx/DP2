
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import services.BrotherhoodService;
import services.ConfigurationParametersService;
import services.EnrolmentService;
import services.MemberService;
import services.UserAccountService;
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

	// Save -----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final ActorFrom actorForm, final BindingResult binding) {
		final ModelAndView result = new ModelAndView("member/edit");
		Member member;
		if (binding.hasErrors())
			result.addObject("errors", binding.getFieldErrors());
		else {
			member = this.memberService.reconstruct(actorForm, binding);
			UserAccount ua = member.getUserAccount();
			ua = this.userAccountService.save(ua);
			member.setUserAccount(ua);
			member = this.memberService.save(member);
			result.addObject("alert", true);
			result.addObject("actorForm", member);
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
