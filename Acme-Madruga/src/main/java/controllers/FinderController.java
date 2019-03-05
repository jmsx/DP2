
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationParametersService;
import services.FinderService;
import services.MemberService;
import services.UserAccountService;
import domain.Finder;
import domain.Member;
import domain.Procession;

@Controller
@RequestMapping("/finder")
public class FinderController extends AbstractController {

	@Autowired
	private MemberService					memberService;

	@Autowired
	private FinderService					finderService;

	@Autowired
	private UserAccountService				userAccountService;

	@Autowired
	private ConfigurationParametersService	configurationParametersService;


	// Constructors -----------------------------------------------------------
	public FinderController() {
		super();
	}

	// Create -----------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Finder finder;
		finder = this.finderService.create();
		result = this.createEditModelAndView(finder, null);

		return result;
	}

	// Edit -----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int finderId) {
		ModelAndView result;
		Finder finder;
		finder = this.finderService.findOne(finderId);
		Assert.notNull(finder);
		result = this.createEditModelAndView(finder, null);

		return result;
	}

	// Save -----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(finder, null);
		else
			try {
				final Finder recons = this.finderService.reconstruct(finder, binding);
				this.finderService.save(recons);
				result = this.createEditModelAndView2(finder, null);
			} catch (final Throwable e) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
			}
		return result;
	}

	// Clear -----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "clear")
	public ModelAndView clear(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(finder, null);
		else
			try {
				final Finder cleared = this.finderService.clear(finder, binding);
				this.finderService.save(cleared);
				result = new ModelAndView("finder/edit");
				result.addObject(cleared);
			} catch (final Throwable e) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
			}
		return result;
	}

	// Display -----------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result;
		final Member member = this.memberService.findByPrincipal();
		final Finder finder = member.getFinder();
		Assert.notNull(finder);
		if (member != null) {
			Collection<Procession> procs;
			procs = this.finderService.findProcessions(finder.getKeyword(), finder.getMinDate(), finder.getMaxDate(), finder.getAreaName());
			result = new ModelAndView("finder/display");
			result.addObject("finder", finder);
			result.addObject("processions", procs);
		} else
			result = new ModelAndView("redirect:/misc/403.jsp");

		return result;

	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String message) {
		ModelAndView result;
		result = new ModelAndView("finder/edit");
		result.addObject("finder", finder);
		result.addObject("message", message);
		return result;
	}

	protected ModelAndView createEditModelAndView2(final Finder finder, final String message) {
		ModelAndView result;
		Collection<Procession> procs;
		procs = this.finderService.findProcessions(finder.getKeyword(), finder.getMinDate(), finder.getMaxDate(), finder.getAreaName());
		result = new ModelAndView("redirect:display.do");
		result.addObject("finder", finder);
		result.addObject("processions", procs);
		result.addObject("message", message);
		return result;
	}

}
