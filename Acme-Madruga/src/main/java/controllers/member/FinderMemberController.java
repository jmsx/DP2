
package controllers.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FinderService;
import services.MemberService;
import controllers.AbstractController;
import domain.Finder;
import domain.Member;

@Controller
@RequestMapping("/finder/member")
public class FinderMemberController extends AbstractController {

	@Autowired
	private FinderService	finderService;

	@Autowired
	private MemberService	memberService;


	// CONSTRUCTOR -----------------------------------------------------------

	public FinderMemberController() {
		super();
	}

	// CREATEEDITMODELANDVIEW -----------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView result;

		result = this.createEditModelAndView(finder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("finder/edit");
		result.addObject("finder", finder);
		result.addObject("message", messageCode);

		return result;
	}

	// CREATE  ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Finder finder = this.finderService.create();
		result = this.createEditModelAndView(finder);
		return result;
	}

	// UPDATE  ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		final Finder finder;
		final Member member = this.memberService.findByPrincipal();
		finder = this.finderService.findMemberFinder(member.getId());
		Assert.notNull(finder);
		result = this.createEditModelAndView(finder);
		return result;
	}

}
