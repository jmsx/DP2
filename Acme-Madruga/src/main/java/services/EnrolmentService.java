
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
	private MemberService		memberService;


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

	public void delete(final Enrolment enrolment) {
		Assert.notNull(enrolment);
		Assert.isTrue(enrolment.getId() != 0);
		Assert.isTrue(this.enrolmentRepository.exists(enrolment.getId()));
		this.enrolmentRepository.delete(enrolment);
	}

	/* ========================= OTHER METHODS =========================== */

	public Collection<Enrolment> getEnrolmentByBrotherhood(final Brotherhood brotherhood) {
		return this.enrolmentRepository.findAllByBrotherHoodId(brotherhood.getId());
	}

	public Collection<Enrolment> getEnrolmentByMember(final Member member) {
		return this.enrolmentRepository.findAllByMemberId(member.getId());
	}

	public Collection<Brotherhood> brotherhoodByMember(final Member member) {
		Assert.notNull(member);
		final Collection<Brotherhood> res = this.enrolmentRepository.findAllBrotherHoodByMember(member.getId());
		Assert.notNull(res);
		return res;
	}

	public Boolean checkBrotherhoodFromMember(final Brotherhood brotherhood) {
		Boolean res = false;
		final Member member = this.memberService.findByPrincipal();
		Assert.notNull(member);

		if (this.brotherhoodByMember(member).contains(brotherhood))
			res = true;

		return res;
	}
}
