
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EnrolmentRepository;
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
	private ActorService		actorService;

	@Autowired
	private MemberService		memberService;


	public Enrolment create() {
		final Enrolment enrolment = new Enrolment();

		final Brotherhood brotherhood = new Brotherhood();
		enrolment.setBrotherhood(brotherhood);

		final Member principal = this.memberService.findByPrincipal();
		enrolment.setMember(principal);

		final Date moment = new Date(System.currentTimeMillis() - 1000);
		enrolment.setMoment(moment);

		/*
		 * Contemplar la restriccion de que un member no puede ocupar dos posiciones
		 * en la misma hermandad
		 */
		final Position position = new Position();
		enrolment.setPosition(position);

		return enrolment;
	}
	public Collection<Enrolment> findAll() {
		final Collection<Enrolment> res = this.enrolmentRepository.findAll();
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

		if (enrolment.getVersion() == 0) {
			final Date moment = new Date(System.currentTimeMillis() - 1);
			enrolment.setMoment(moment);
			enrolment.setMember(this.memberService.findByPrincipal());

		}
		if (enrolment.getVersion() != 0)
			Assert.isTrue(enrolment.getMember() == this.memberService.findByPrincipal());
		result = this.enrolmentRepository.save(enrolment);
		return result;
	}

}
