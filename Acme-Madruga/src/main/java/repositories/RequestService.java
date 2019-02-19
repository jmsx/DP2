
package repositories;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import security.Authority;
import services.ActorService;
import domain.Actor;
import domain.Request;

@Service
@Transactional
public class RequestService {

	@Autowired
	private RequestRepository	requestRepository;

	@Autowired
	private ActorService		actorService;


	// ======================= CRUD ================================
	public Collection<Request> findAll() {
		final Actor principal = this.actorService.findByPrincipal();
		final Collection<Request> res = new HashSet<>();
		if (this.actorService.checkAuthority(principal, Authority.MEMBER))
			res.addAll(this.requestRepository.findByMember(principal.getId()));
		else if (this.actorService.checkAuthority(principal, Authority.BROTHERHOOD))
			res.addAll(this.requestRepository.findByBrotherhood(principal.getId()));
		return res;
	}
	public Request findOne(final Integer idRequest) {
		final Actor principal = this.actorService.findByPrincipal();
		Request res = this.requestRepository.findOne(idRequest);
		Boolean isAccepted = false;
		if (this.actorService.checkAuthority(principal, Authority.MEMBER))
			isAccepted = res.getMember().getId() == principal.getId();
		else if (this.actorService.checkAuthority(principal, Authority.BROTHERHOOD))
			isAccepted = this.requestRepository.checkBrotherhoodAccess(principal.getId());
		Assert.isTrue(isAccepted, "RequesService - findOne - Access Denied");
		res = isAccepted ? res : null;
		return res;
	}

}
