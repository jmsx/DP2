
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EnrolmentRepository;
import security.Authority;
import domain.Actor;
import domain.Brotherhood;
import domain.Enrolment;
import domain.Member;

@Service
@Transactional
public class EnrolmentService {

	@Autowired
	private EnrolmentRepository	enrolmentRepository;

	@Autowired
	private MemberService		memberService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	public Enrolment create() {
		final Enrolment enrolment = new Enrolment();

		final Brotherhood brotherhood = new Brotherhood();
		enrolment.setBrotherhood(brotherhood);

		final Member principal = this.memberService.findByPrincipal();
		enrolment.setMember(principal);

		return enrolment;
	}
	public Collection<Enrolment> findAll() {
		Collection<Enrolment> res = new ArrayList<>();
		final Actor principal = this.actorService.findByPrincipal();
		final Boolean isBrotherhood = this.actorService.checkAuthority(principal, Authority.BROTHERHOOD);
		final Boolean isMember = this.actorService.checkAuthority(principal, Authority.MEMBER);

		if (isBrotherhood)
			res = this.enrolmentRepository.findAllByBrotherHoodId(principal.getUserAccount().getId());
		else if (isMember)
			res = this.enrolmentRepository.findAllByMemberId(principal.getUserAccount().getId());
		//Si salta puede ser un Admin
		Assert.notNull(res);
		return res;
	}

	public Enrolment findOne(final int enrolmentId) {
		Assert.isTrue(enrolmentId != 0);
		final Enrolment res = this.enrolmentRepository.findOne(enrolmentId);
		Assert.notNull(res);
		return res;
	}

	public Enrolment save(final Enrolment enrolment) {
		Assert.notNull(enrolment);
		final Actor principal = this.actorService.findByPrincipal();
		final Enrolment result;
		final Boolean isBrotherhood = this.actorService.checkAuthority(principal, Authority.BROTHERHOOD);
		final Boolean isMember = this.actorService.checkAuthority(principal, Authority.MEMBER);

		if (isMember) {
			if (enrolment.getId() == 0) {
				Assert.isTrue(!this.brotherhoodService.findAllBrotherHoodByMember().contains(enrolment.getBrotherhood()));
				final Date moment = new Date(System.currentTimeMillis() - 1);
				enrolment.setMoment(moment);
				enrolment.setMember(this.memberService.findByPrincipal());
				enrolment.setDropOut(null);
			} else
				Assert.isTrue(enrolment.getMember() == this.memberService.findByPrincipal());
			if (enrolment.getDropOut() != null)
				Assert.isTrue(enrolment.getMoment().before(enrolment.getDropOut()));

		} else if (isBrotherhood) {
			Assert.isTrue(enrolment.getBrotherhood() == this.brotherhoodService.findByPrincipal());
			Assert.notNull(enrolment.getPosition());
			enrolment.setEnrolled(true);
			if (enrolment.getDropOut() != null)
				Assert.isTrue(enrolment.getMoment().before(enrolment.getDropOut()));
		}
		result = this.enrolmentRepository.save(enrolment);
		return result;
	}

	public void delete(final Enrolment enrolment) {
		Assert.notNull(enrolment);
		Assert.isTrue(enrolment.getId() != 0);
		Assert.isTrue(this.enrolmentRepository.exists(enrolment.getId()));
		this.enrolmentRepository.delete(enrolment);
	}

	/* ========================= OTHER METHODS =========================== */

}
