
package controllers.member;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.FinderService;
import services.MemberService;
import services.ProcessionService;
import controllers.AbstractController;
import domain.Actor;
import domain.Finder;
import domain.Procession;

@Controller
@RequestMapping("/finder/member")
public class FinderMemberController extends AbstractController {

	@Autowired
	private FinderService		finderService;

	@Autowired
	private MemberService		memberService;

	@Autowired
	private ProcessionService	processionService;

	@Autowired
	private ActorService		actorService;


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
		finder = this.finderService.findMemberFinder();
		Assert.notNull(finder);
		result = this.createEditModelAndView(finder);
		return result;
	}

	// CLEAR  ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "clear")
	public ModelAndView clear(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {
				final Finder cleared = this.finderService.clear(finder);
				// this.finderService.save(cleared);
				result = new ModelAndView("redirect:edit.do");
				result.addObject(cleared);
			} catch (final Throwable e) {
				result = this.createEditModelAndView(finder);
			}
		return result;
	}

	// SAVE  ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Finder finder, final BindingResult binding) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, Authority.MEMBER));
		ModelAndView result;
		final Collection<Procession> listFinder = this.finderService.find(finder.getKeyword(), finder.getAreaName(), finder.getMinDate(), finder.getMaxDate());
		final String lang = LocaleContextHolder.getLocale().getLanguage();
		if (binding.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {
				finder.setProcessions(listFinder);
				final Finder saved = this.finderService.save(finder);
				result = new ModelAndView("redirect:display.do");
				result.addObject("finder", saved);
				result.addObject("processions", listFinder);
				result.addObject("lang", lang);
			} catch (final Throwable e) {
				result = this.createEditModelAndView(finder);
			}
		return result;
	}

	// DISPLAY  ---------------------------------------------------------------	

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result;
		final Finder finder = this.finderService.findMemberFinder();
		Assert.notNull(finder);
		final String lang = LocaleContextHolder.getLocale().getLanguage();

		Collection<Procession> procs;
		procs = this.finderService.find(finder.getKeyword(), finder.getAreaName(), finder.getMinDate(), finder.getMaxDate());
		//procs = this.processionService.findAllFinalMode();
		result = new ModelAndView("finder/display");
		result.addObject("finder", finder);
		result.addObject("processions", procs);
		result.addObject("lang", lang);
		//		 else
		//			result = new ModelAndView("redirect:/misc/403.jsp");

		return result;

	}

}
