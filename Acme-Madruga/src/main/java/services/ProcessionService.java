
package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProcessionRepository;
import security.Authority;
import domain.Actor;
import domain.Brotherhood;
import domain.Float;
import domain.Member;
import domain.Procession;
import forms.ProcessionForm;

@Service
@Transactional
public class ProcessionService {

	@Autowired
	private ProcessionRepository	processionRepository;

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private MemberService			memberService;

	@Autowired
	private Validator				validator;


	public Procession create() {
		final Procession procession = new Procession();

		final Collection<Float> floats = new ArrayList<>();
		procession.setFloats(floats);

		final Brotherhood brotherhood = this.brotherhoodService.findByPrincipal();
		procession.setBrotherhood(brotherhood);

		return procession;

	}

	public Collection<Procession> findAll() {
		final Collection<Procession> result = this.processionRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Collection<Procession> findAllFinalMode() {
		final Collection<Procession> result = this.processionRepository.findAllFinalMode();
		Assert.notNull(result);

		return result;
	}

	public Collection<Procession> findAllByPrincipal() {
		Collection<Procession> res = new ArrayList<>();
		final Actor principal = this.actorService.findByPrincipal();
		final Boolean isBrotherhood = this.actorService.checkAuthority(principal, Authority.BROTHERHOOD);
		final Boolean isMember = this.actorService.checkAuthority(principal, Authority.MEMBER);

		if (isBrotherhood)
			res = this.processionRepository.findAllProcessionByBrotherhoodId(principal.getUserAccount().getId());
		else if (isMember)
			res = this.processionRepository.findAllProcessionByBMemberId(principal.getUserAccount().getId());
		//Si salta puede ser un Admin
		Assert.notNull(res);
		return res;
	}

	public Procession findOne(final int processionId) {
		Assert.isTrue(processionId != 0);
		final Procession res = this.processionRepository.findOne(processionId);
		Assert.notNull(res);
		return res;
	}

	public Procession save(final Procession procession) {
		Assert.notNull(procession);
		final Actor principal = this.actorService.findByPrincipal();
		final Procession result;
		final Boolean isBrotherhood = this.actorService.checkAuthority(principal, Authority.BROTHERHOOD);
		final Brotherhood bro = this.brotherhoodService.findByUserId(principal.getId());

		if (isBrotherhood && bro.getArea() != null)
			if (procession.getId() == 0) {
				procession.setBrotherhood(this.brotherhoodService.findByPrincipal());
				procession.setMode("DRAFT");
				final Date moment = new Date(System.currentTimeMillis() - 1);
				procession.setMoment(moment);
				procession.setTicker(this.generateTicker(moment));
			} else
				Assert.isTrue(procession.getBrotherhood() == this.brotherhoodService.findByPrincipal());

		result = this.processionRepository.save(procession);
		return result;
	}

	public void delete(final Procession procession) {
		Assert.notNull(procession);
		Assert.isTrue(procession.getId() != 0);

		final Procession retrieved = this.findOne(procession.getId());

		final Brotherhood principal = this.brotherhoodService.findByPrincipal();
		Assert.isTrue(retrieved.getBrotherhood().equals(principal));
		this.processionRepository.delete(procession);

	}

	/* ========================= OTHER METHODS =========================== */

	private String generateTicker(final Date date) {
		String res = "";
		final SimpleDateFormat myFormat = new SimpleDateFormat("yyMMdd", Locale.ENGLISH);
		final String YYMMMDDD = myFormat.format(date);
		final String word = RandomStringUtils.randomAlphabetic(5);
		final String tickr = YYMMMDDD + '-' + word;
		res = tickr;

		if (this.hasDuplicate(res))
			this.generateTicker(date);
		return res;
	}

	private boolean hasDuplicate(final String ticker) {

		boolean res = true;
		try {
			if (this.processionRepository.getProcessionWithTicker(ticker).isEmpty())
				res = false;
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	/**
	 * It returns all processions which principal isn't enrolled. The principal must be a member.
	 * 
	 * @author a8081
	 * */
	public Collection<Procession> processionsAvailable() {
		final Member principal = this.memberService.findByPrincipal();
		final Collection<Procession> memberProcessions = this.processionRepository.findAllProcessionByBMemberId(principal.getUserAccount().getId());
		final Collection<Procession> processions = this.findAllFinalMode();
		processions.removeAll(memberProcessions);
		return processions;
	}

	public boolean exists(final Integer processionId) {
		return this.processionRepository.exists(processionId);
	}

	public Procession toFinalMode(final int processionId) {
		final Procession procession = this.findOne(processionId);
		final Procession result;
		final Brotherhood bro = this.brotherhoodService.findByPrincipal();
		Assert.isTrue(procession.getBrotherhood() == bro, "Actor who want to edit procession mode to FINAL is not his owner");
		Assert.isTrue(procession.getMode().equals("DRAFT"));
		if (bro.getArea() != null)
			procession.setMode("FINAL");
		result = this.processionRepository.save(procession);
		return result;
	}
	public Procession reconstruct(final ProcessionForm pform, final BindingResult binding) {
		Procession result;

		if (pform.getId() == 0)
			result = this.create();
		else
			result = this.findOne(pform.getId());

		result.setTitle(pform.getTitle());
		result.setDescription(pform.getDescription());
		result.setMaxRows(pform.getMaxRows());
		result.setMaxColumns(pform.getMaxColumns());
		result.setFloats(pform.getFloats());

		this.validator.validate(result, binding);

		return result;
	}
}
