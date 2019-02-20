
package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProcessionRepository;
import security.Authority;
import domain.Actor;
import domain.Brotherhood;
import domain.Float;
import domain.Procession;

@Service
@Transactional
public class ProcessionService {

	@Autowired
	private ProcessionRepository	processionRepository;

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private ActorService			actorService;


	public Procession create() {
		final Procession procession = new Procession();

		final Collection<Float> floats = new ArrayList<>();
		procession.setFloatProcessions(floats);

		final Brotherhood brotherhood = this.brotherhoodService.findByPrincipal();
		procession.setBrotherhood(brotherhood);

		return procession;

	}

	public Collection<Procession> findAll() {
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

		if (isBrotherhood)
			if (procession.getId() == 0) {
				procession.setBrotherhood(this.brotherhoodService.findByPrincipal());
				procession.setMode("DRAFT");
				final Date moment = new Date(System.currentTimeMillis() - 1);
				procession.setMoment(moment);
			} else
				Assert.isTrue(procession.getBrotherhood() == this.brotherhoodService.findByPrincipal());

		result = this.processionRepository.save(procession);
		return result;
	}

	public void delete(final Procession procession) {
		Assert.notNull(procession);
		Assert.isTrue(procession.getId() != 0);

		final Brotherhood principal = this.brotherhoodService.findByPrincipal();
		Assert.isTrue(procession.getBrotherhood().equals(principal));

		this.processionRepository.delete(procession);

	}

	/* ========================= OTHER METHODS =========================== */

	private String generateTicker(final Date date) {
		String res = "";
		final SimpleDateFormat myFormat = new SimpleDateFormat("yyMMdd", Locale.ENGLISH);
		final String YYMMMDDD = myFormat.format(date);
		final String word = RandomStringUtils.randomAlphabetic(5);
		final int max = 99;
		final int min = 10;
		final Random rand = new Random();
		final int randomNum = rand.nextInt((max - min) + 1) + min;
		final String num = String.valueOf(randomNum);
		final String yy = YYMMMDDD.substring(0, 2);
		final String mm = YYMMMDDD.substring(2, 5);
		final String dd = YYMMMDDD.substring(5, 6);
		final String tickr = yy + mm + dd + '-' + word;
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

}
