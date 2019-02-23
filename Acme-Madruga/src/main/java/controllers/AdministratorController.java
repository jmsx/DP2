/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import services.AdministratorService;
import services.UserAccountService;
import domain.Administrator;
import forms.ActorFrom;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private UserAccountService		accountService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping("/action-1")
	public ModelAndView action1() {
		ModelAndView result;

		result = new ModelAndView("administrator/action-1");

		return result;
	}

	// Action-2 ---------------------------------------------------------------

	@RequestMapping("/action-2")
	public ModelAndView action2() {
		ModelAndView result;

		result = new ModelAndView("administrator/action-2");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView edit(final ActorFrom actorForm, final BindingResult binding) {
		ModelAndView result;
		result = new ModelAndView("administrator/edit");
		Administrator admin;
		if (binding.hasErrors())
			result.addObject("errors", binding.getFieldErrors());
		else {
			admin = this.administratorService.reconstruct(actorForm, binding);
			UserAccount ua = admin.getUserAccount();
			ua = this.accountService.save(ua);
			admin.setUserAccount(ua);
			admin = this.administratorService.save(admin);
			result.addObject("alert", true);
			result.addObject("actorForm", admin);

		}

		return result;
	}
	@RequestMapping("/create")
	public ModelAndView create() {
		ModelAndView result = new ModelAndView();
		final ActorFrom admin = new ActorFrom();
		result = new ModelAndView("administrator/edit");
		result.addObject("actorForm", admin);
		return result;
	}

}
