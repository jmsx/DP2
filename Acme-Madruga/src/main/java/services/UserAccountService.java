
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.UserAccount;
import security.UserAccountRepository;

@Service
@Transactional
public class UserAccountService {

	@Autowired
	private UserAccountRepository	userAccountRepository;


	public UserAccount create() {
		return new UserAccount();
	}

	public Collection<UserAccount> findAll() {
		final Collection<UserAccount> result = this.userAccountRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public UserAccount findOne(final int id) {
		Assert.isTrue(id != 0);
		final UserAccount result = this.userAccountRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public UserAccount save(final UserAccount ua) {
		Assert.notNull(ua);
		return this.userAccountRepository.save(ua);
	}
}
