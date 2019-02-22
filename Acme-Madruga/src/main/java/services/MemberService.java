
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MemberRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Member;

@Service
@Transactional
public class MemberService {

	@Autowired
	private MemberRepository	memberRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private FolderService		folderService;


	public Member create() {
		return new Member();
	}

	public Collection<Member> findAll() {

		final Collection<Member> result = this.memberRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Member findOne(final int memberId) {
		Assert.isTrue(memberId != 0);
		final Member result = this.memberRepository.findOne(memberId);
		Assert.notNull(result);
		return result;
	}

	public Member save(final Member member) {
		Assert.notNull(member);

		Member result;
		this.actorService.checkForSpamWords(member);
		if (member.getId() == 0) {
			this.actorService.setAuthorityUserAccount(Authority.MEMBER, member);
			result = this.memberRepository.save(member);
			this.folderService.setFoldersByDefault(result);
		} else
			result = (Member) this.actorService.save(member);

		return result;
	}

	public void delete(final Member member) {
		Assert.notNull(member);
		Assert.isTrue(this.findByPrincipal().equals(member));
		Assert.isTrue(member.getId() != 0);
		Assert.isTrue(this.memberRepository.exists(member.getId()));
		this.memberRepository.delete(member);
	}

	/* ========================= OTHER METHODS =========================== */

	public Member findByPrincipal() {
		final UserAccount user = LoginService.getPrincipal();
		Assert.notNull(user);

		final Member member = this.findByUserId(user.getId());
		Assert.notNull(member);
		final boolean bool = this.actorService.checkAuthority(member, Authority.MEMBER);
		Assert.isTrue(bool);

		return member;
	}

	public Member findByUserId(final int id) {
		Assert.isTrue(id != 0);
		final Member member = this.memberRepository.findByUserId(id);
		return member;
	}

}
