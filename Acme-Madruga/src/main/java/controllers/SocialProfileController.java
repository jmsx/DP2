
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.SocialProfileService;
import domain.SocialProfile;

@Controller
@RequestMapping("/socialProfile")
public class SocialProfileController extends AbstractController {

	@Autowired
	private SocialProfileService	socialProfileService;
	@Autowired
	private ActorService			actorService;


	@RequestMapping("/create")
	public ModelAndView create() {

		return null;
	}

	@RequestMapping("/list")
	public ModelAndView list() {
		final ModelAndView result = new ModelAndView("socialProfile/list");
		//		final Actor actor = this.actorService.findByPrincipal();
		final Collection<SocialProfile> list = this.socialProfileService.findAll();
		result.addObject("socialProfiles", list);
		return result;
	}
}
