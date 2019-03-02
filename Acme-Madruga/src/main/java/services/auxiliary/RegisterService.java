
package services.auxiliary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import security.UserAccount;
import security.UserAccountRepository;
import services.ActorService;
import services.AdministratorService;
import services.MemberService;
import services.UserAccountService;
import domain.Administrator;
import domain.Member;

@Service
@Transactional
public class RegisterService {

	@Autowired
	private ActorService			actorService;
	@Autowired
	private UserAccountService		userAccountService;
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private UserAccountRepository	userAccountRepository;

	@Autowired
	private MemberService			memberService;


	public Administrator saveAdmin(final Administrator admin, final BindingResult binding) {
		Administrator result;
		final UserAccount ua = admin.getUserAccount();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hash = encoder.encodePassword(ua.getPassword(), null);

		Assert.isTrue(this.userAccountRepository.findByUsername(ua.getUsername()) == null, "The username is register");

		ua.setPassword(hash);

		admin.setUserAccount(ua);
		result = this.administratorService.save(admin);

		UserAccount uaSaved = result.getUserAccount();
		uaSaved.setAuthorities(ua.getAuthorities());
		uaSaved.setUsername(ua.getUsername());
		uaSaved.setPassword(ua.getPassword());
		uaSaved = this.userAccountService.save(uaSaved);

		result.setUserAccount(uaSaved);

		return result;
	}

	public Member saveMember(final Member member, final BindingResult binding) {
		Member result;
		final UserAccount ua = member.getUserAccount();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hash = encoder.encodePassword(ua.getPassword(), null);

		Assert.isTrue(this.userAccountRepository.findByUsername(ua.getUsername()) == null, "The username is register");

		ua.setPassword(hash);

		member.setUserAccount(ua);
		result = this.memberService.save(member);

		UserAccount uaSaved = result.getUserAccount();
		uaSaved.setAuthorities(ua.getAuthorities());
		uaSaved.setUsername(ua.getUsername());
		uaSaved.setPassword(ua.getPassword());
		uaSaved = this.userAccountService.save(uaSaved);

		result.setUserAccount(uaSaved);

		return result;
	}
}
