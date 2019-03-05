
package controllers;

import java.util.ArrayList;
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

import security.Authority;
import services.ActorService;
import services.ConfigurationParametersService;
import services.FinderService;
import services.MemberService;
import domain.Actor;
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
	private ActorService					actorService;

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
		result = this.createEditModelAndView(finder);

		return result;
	}

	// Edit -----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int finderId) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, Authority.MEMBER));

		final Finder finder = this.finderService.findOne(finderId);
		Assert.notNull(finder);
		ModelAndView result;
		result = this.createEditModelAndView(finder);

		return result;
	}

	// Save -----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {
				final Finder recons = this.finderService.reconstruct(finder, binding);
				this.finderService.save(recons);
				result = this.createEditModelAndView2(finder, null);
			} catch (final Throwable e) {
				result = this.createEditModelAndView(finder);
			}
		return result;
	}

	// Clear -----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "clear")
	public ModelAndView clear(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {
				final Finder cleared = this.finderService.clear(finder, binding);
				this.finderService.save(cleared);
				result = new ModelAndView("redirect:edit.do");
				result.addObject(cleared);
			} catch (final Throwable e) {
				result = this.createEditModelAndView(finder);
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
			//procs = this.finderService.findProcessions(finder.getKeyword(), finder.getMinDate(), finder.getMaxDate(), finder.getAreaName());
			procs = new ArrayList<Procession>();
			result = new ModelAndView("finder/display");
			result.addObject("finder", finder);
			result.addObject("processions", procs);
		} else
			result = new ModelAndView("redirect:/misc/403.jsp");

		return result;

	}

	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView result;
		result = new ModelAndView("finder/edit");
		result.addObject("finder", finder);
		return result;
	}

	protected ModelAndView createEditModelAndView2(final Finder finder, final String message) {
		ModelAndView result;
		Collection<Procession> procs;
		//procs = this.finderService.findProcessions(finder.getKeyword(), finder.getMinDate(), finder.getMaxDate(), finder.getAreaName());
		procs = new ArrayList<Procession>();
		result = new ModelAndView("redirect:display.do");
		result.addObject("finder", finder);
		result.addObject("processions", procs);
		result.addObject("message", message);
		return result;
	}

}
