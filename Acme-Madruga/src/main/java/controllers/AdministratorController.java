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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.UserAccount;
import services.AdministratorService;
import services.UserAccountService;
import services.auxiliary.RegisterService;
import domain.Administrator;
import forms.ActorFrom;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private UserAccountService		accountService;
	@Autowired
	private Validator				validator;
	@Autowired
	private RegisterService			registerService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final ActorFrom actorForm, final BindingResult binding) {
		ModelAndView result;
		result = new ModelAndView("administrator/edit");
		Administrator admin;
		if (binding.hasErrors()) {
			result.addObject("errors", binding.getAllErrors());
			result.addObject("actorForm", actorForm);
		} else
			try {
				final UserAccount ua = this.accountService.reconstruct(actorForm, Authority.ADMIN);
				admin = this.administratorService.reconstruct(actorForm);
				admin.setUserAccount(ua);
				this.registerService.saveAdmin(admin, binding);
				result.addObject("alert", "administartor.edit.correct");
				result.addObject("actorForm", actorForm);
			} catch (final Throwable e) {
				if (e.getMessage().contains("username is register"))
					result.addObject("alert", "administartor.edit.usernameIsUsed");
				result.addObject("errors", binding.getAllErrors());
				result.addObject("actorForm", actorForm);
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		result = new ModelAndView("administrator/edit");
		final Administrator admin = this.administratorService.findByPrincipal();
		final ActorFrom actor = this.registerService.inyect(admin);
		result.addObject("actorForm", actor);
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
