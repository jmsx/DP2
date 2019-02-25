
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

import services.ActorService;
import services.ConfigurationParametersService;
import services.FolderService;
import domain.Folder;

@Controller
@RequestMapping("/folder")
public class FolderController extends AbstractController {

	@Autowired
	private FolderService					folderService;

	@Autowired
	private ActorService					actorService;

	@Autowired
	private ConfigurationParametersService	configurationParametersService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		final ModelAndView res;
		Collection<Folder> folders;

		folders = this.folderService.findAllByUserId(this.actorService.findByPrincipal().getId());
		folders = this.folderService.setFoldersByDefault(this.actorService.findByPrincipal());

		res = new ModelAndView("folder/list");
		res.addObject("folders", folders);
		//No necesitamos el objeto requestURI ya que lo hemos puesto directamente en la vista
		//		res.addObject("requestURI", "folder/list.do");
		final String banner = this.configurationParametersService.find().getBanner();
		res.addObject("banner", banner);

		return res;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView res;
		Folder folder;

		folder = this.folderService.create();
		res = this.createEditModelAndView(folder);
		res.addObject("new", false);

		return res;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int folderId) {
		ModelAndView result;
		Folder folder;

		folder = this.folderService.findOne(folderId);
		if (folder.getName().equals("In box") || folder.getName().equals("Out box") || folder.getName().equals("Spam box") || folder.getName().equals("Trash box") || folder.getName().equals("Notification box")) {
			result = new ModelAndView("administrator/error");
			result.addObject("trace", "You can't edit or delete system folder");
			return result;
		} else if (!folder.getActor().equals(this.actorService.findByPrincipal())) {
			result = new ModelAndView("administrator/error");
			result.addObject("trace", "You can't edit or delete anyone else's folder");
			return result;
		} else {
			Assert.notNull(folder);
			Assert.isTrue(!this.folderService.findAllByUserId(this.actorService.findByPrincipal().getId()).contains(folder));
			result = this.createEditModelAndView(folder);
			result.addObject("id", folder.getId());
		}
		return result;
	}

	//	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	//	public ModelAndView edit(@RequestParam final int folderId) {
	//		ModelAndView res;
	//		Folder folder;
	//		Collection<Folder> folders;
	//
	//		folder = this.folderService.findOne(folderId);
	//		folders = this.folderService.findAll();
	//
	//		if (folder != null && folders.contains(folder))
	//			res = this.createEditModelAndView(folder);
	//		else
	//			res = new ModelAndView("redirect:/misc/403.jsp");
	//
	//		return res;
	//
	//	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Folder folder, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(folder);
			result.addObject("id", folder.getId());
		} else if (folder.getName().equals("In box") || folder.getName().equals("Out box") || folder.getName().equals("Spam box") || folder.getName().equals("Trash box") || folder.getName().equals("Notification box"))
			result = this.createEditModelAndView(folder, "general.commit.cantEditSystemFolders");
		else
			try {
				folder.setActor(this.actorService.findByPrincipal());
				final Folder saved = this.folderService.save(folder, this.actorService.findByPrincipal());
				if (!this.folderService.findAllByUserId(this.actorService.findByPrincipal().getId()).contains(folder)) {
					this.folderService.save(saved, this.actorService.findByPrincipal());
					//					this.folderService.findAllByUserId(this.actorService.findByPrincipal().getId()).add(saved);
					this.actorService.save(this.actorService.findByPrincipal());
				}
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(folder, "general.commit.error");
				result.addObject("id", folder.getId());
			}
		return result;
	}
	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	//	public ModelAndView save(@Valid final Folder folder, final BindingResult binding) {
	//
	//		ModelAndView res;
	//		final Actor principal = this.actorService.findByPrincipal();
	//
	//		if (binding.hasErrors())
	//			res = this.createEditModelAndView(folder);
	//		else
	//			try {
	//				this.folderService.save(folder, principal);
	//				res = new ModelAndView("redirect:list.do");
	//				final String banner = this.configurationParametersService.find().getBanner();
	//				res.addObject("banner", banner);
	//			} catch (final Throwable oops) {
	//				res = this.createEditModelAndView(folder, "folder.commit.error");
	//			}
	//		return res;
	//	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int folderId) {
		ModelAndView result;
		final Folder folder = this.folderService.findOne(folderId);
		if (folder.getName().equals("In box") || folder.getName().equals("Out box") || folder.getName().equals("Spam box") || folder.getName().equals("Trash box") || folder.getName().equals("Notification box")) {
			result = new ModelAndView("administrator/error");
			result.addObject("trace", "You can't edit or delete system folder");
		} else
			try {
				//folder.setMezzages(null);
				this.folderService.delete(folder);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(folder, "general.commit.error");
				result.addObject("id", folder.getId());
			}

		return result;
	}
	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	//	public ModelAndView delete(final Folder folder, final BindingResult binding) {
	//
	//		ModelAndView res;
	//
	//		try {
	//			this.folderService.delete(folder);
	//			res = new ModelAndView("redirect:list.do");
	//			final String banner = this.configurationParametersService.find().getBanner();
	//			res.addObject("banner", banner);
	//		} catch (final Throwable oops) {
	//			res = this.createEditModelAndView(folder, "folder.commit.error");
	//		}
	//		return res;
	//	}

	protected ModelAndView createEditModelAndView(final Folder folder) {

		ModelAndView res;

		res = this.createEditModelAndView(folder, null);

		return res;

	}

	protected ModelAndView createEditModelAndView(final Folder folder, final String messageCode) {

		ModelAndView res;

		res = new ModelAndView("folder/edit");
		res.addObject("folder", folder);

		res.addObject("message", messageCode);
		final String banner = this.configurationParametersService.find().getBanner();
		res.addObject("banner", banner);

		return res;

	}
}
