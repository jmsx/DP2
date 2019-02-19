
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
import domain.Position;

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

		final Date moment = new Date(System.currentTimeMillis() - 1000);
		enrolment.setMoment(moment);

		final Position position = new Position();
		enrolment.setPosition(position);

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
		final Enrolment result;

		if (enrolment.getId() == 0) {
			Assert.isTrue(!this.brotherhoodService.findAllBrotherHoodByMember().contains(enrolment.getBrotherhood()));
			final Date moment = new Date(System.currentTimeMillis() - 1);
			enrolment.setMoment(moment);
			enrolment.setMember(this.memberService.findByPrincipal());

		}
		if (enrolment.getId() != 0)
			Assert.isTrue(enrolment.getMember() == this.memberService.findByPrincipal());
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
