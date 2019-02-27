
package controllers.administrator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import controllers.AbstractController;
import domain.Actor;

@Controller
@RequestMapping(value = "/ban/administrator")
public class BanAdministratorController extends AbstractController {

	@Autowired
	private ActorService	actorService;


	//Listing banned spammers -----------------------------------
	@RequestMapping(value = "/listBannedSpammer", method = RequestMethod.GET)
	public ModelAndView listBannedSpammers() {
		final ModelAndView result;

		final List<Actor> actors = (List<Actor>) this.actorService.findAllBannedSpammers();
		Assert.notNull(actors);

		result = new ModelAndView("ban/listBannedSpammer");
		result.addObject("actors", actors);
		result.addObject("requestURI", "ban/administrator/listBannedSpammer.do");

		return result;

	}

	//Listing not banned spammers-----------------------------------
	@RequestMapping(value = "/listNotBannedSpammer", method = RequestMethod.GET)
	public ModelAndView listNotBanned() {
		final ModelAndView result;

		final List<Actor> actors = (List<Actor>) this.actorService.findAllNotBannedSpammers();
		Assert.notNull(actors);

		result = new ModelAndView("ban/listNotBannedSpammer");
		result.addObject("actors", actors);
		result.addObject("requestURI", "ban/administrator/listNotBannedSpammer.do");

		return result;

	}

	//Listing banned negative -----------------------------------
	@RequestMapping(value = "/listBannedNegative", method = RequestMethod.GET)
	public ModelAndView listBannedNegative() {
		final ModelAndView result;

		final List<Actor> actors = (List<Actor>) this.actorService.findAllBannedNegative();
		Assert.notNull(actors);

		result = new ModelAndView("ban/listBannedNegative");
		result.addObject("actors", actors);
		result.addObject("requestURI", "ban/administrator/listBannedNegative.do");

		return result;

	}

	//Listing banned negative -----------------------------------
	@RequestMapping(value = "/listNotBannedNegative", method = RequestMethod.GET)
	public ModelAndView listBannedNotNegative() {
		final ModelAndView result;

		final List<Actor> actors = (List<Actor>) this.actorService.findAllNotBannedNegative();
		Assert.notNull(actors);

		result = new ModelAndView("ban/listNotBannedNegative");
		result.addObject("actors", actors);
		result.addObject("requestURI", "ban/administrator/listNotBannedNegative.do");

		return result;

	}

	//To ban an actor -----------------------------------
	@RequestMapping(value = "/ban", method = RequestMethod.POST)
	public ModelAndView ban(@RequestParam final int actorId) {
		final ModelAndView result;

		final Actor actor = this.actorService.findOne(actorId);
		Assert.notNull(actor);
		this.actorService.banActor(actor);

		result = new ModelAndView("redirect:listBannedNegative.do");

		return result;

	}

	//To unban an actor -----------------------------------
	@RequestMapping(value = "/unban", method = RequestMethod.POST)
	public ModelAndView unban(@RequestParam final int actorId) {
		final ModelAndView result;

		final Actor actor = this.actorService.findOne(actorId);
		Assert.notNull(actor);
		this.actorService.unbanActor(actor);

		result = new ModelAndView("redirect:listBannedNegative.do");

		return result;

	}
}
