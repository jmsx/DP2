
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

import services.BrotherhoodService;
import services.ConfigurationParametersService;
import services.EnrolmentService;
import services.MemberService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Enrolment;
import domain.Member;
import domain.Position;
import forms.EnrolmentForm;

@Controller
@RequestMapping("/enrolment/brotherhood")
public class EnrolmentBrotherhoodController extends AbstractController {

	@Autowired
	private EnrolmentService				enrolmentService;

	@Autowired
	private ConfigurationParametersService	configurationParametersService;

	@Autowired
	private BrotherhoodService				brotherhoodService;

	@Autowired
	private MemberService					memberService;


	// CONSTRUCTOR -----------------------------------------------------------

	public EnrolmentBrotherhoodController() {
		super();
	}

	// CREATEEDITMODELANDVIEW -----------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Enrolment enrolment) {
		ModelAndView result;

		result = this.createEditModelAndView(enrolment, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Enrolment enrolment, final String messageCode) {
		final ModelAndView result;

		final Collection<Position> positions = this.configurationParametersService.findPositionList();

		result = new ModelAndView("enrolment/edit");
		result.addObject("enrolment", enrolment);
		result.addObject("positions", positions);

		result.addObject("message", messageCode);

		final String banner = this.configurationParametersService.findBanner();
		result.addObject("banner", banner);

		return result;
	}

	// DISPLAY PARA VISTA DE BROTHERHOOD  ---------------------------------------------------------------		

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int memberId) {
		final ModelAndView result;

		final Brotherhood brotherhood = this.brotherhoodService.findByPrincipal();
		final Member member = this.memberService.findOne(memberId);
		final Enrolment enrolment = this.enrolmentService.getEnrolment(brotherhood, member);

		if (brotherhood != null) {
			result = new ModelAndView("enrolment/display");
			result.addObject("enrolment", enrolment);
			result.addObject("member", member);
			result.addObject("brotherhood", brotherhood);

			final String banner = this.configurationParametersService.findBanner();
			result.addObject("banner", banner);
		} else
			result = new ModelAndView("redirect:/misc/403.jsp");

		return result;
	}

	// SAVE  ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EnrolmentForm enrolmentForm, final BindingResult binding) {
		ModelAndView result;

		final Enrolment enrolment = this.enrolmentService.reconstruct(enrolmentForm, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(enrolment);
		else
			try {
				this.enrolmentService.save(enrolment);
				result = new ModelAndView("redirect:list.do");
				final String banner = this.configurationParametersService.findBanner();
				result.addObject("banner", banner);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(enrolment, "enrolment.commit.error");
			}

		return result;
	}
}
