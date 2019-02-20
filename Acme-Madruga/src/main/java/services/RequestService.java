
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import security.Authority;
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
	/**
	 * Este metodo devuelve todos los Request's a los que puede acceder el actor autentificado.
	 * 
	 * @return Collection<Request>
	 * @author jmsx
	 */
	public Collection<Request> findAll() {
		final Actor principal = this.actorService.findByPrincipal();
		final Collection<Request> res = new HashSet<>();
		if (this.actorService.checkAuthority(principal, Authority.MEMBER))
			res.addAll(this.requestRepository.findByMember(principal.getId()));
		else if (this.actorService.checkAuthority(principal, Authority.BROTHERHOOD))
			res.addAll(this.requestRepository.findByBrotherhood(principal.getId()));
		return res;
	}
	/**
	 * Este metodo devuelve Request si el actor tiene acceos a el.
	 * 
	 * @return Request
	 * @author jmsx
	 */
	public Request findOne(final Integer idRequest) {
		final Actor principal = this.actorService.findByPrincipal();
		Request res = this.requestRepository.findOne(idRequest);
		Assert.notNull(res, "Not found Request to this id");
		Boolean isAccepted = false;
		if (this.actorService.checkAuthority(principal, Authority.MEMBER))
			isAccepted = res.getMember().getId() == principal.getId();
		else if (this.actorService.checkAuthority(principal, Authority.BROTHERHOOD))
			isAccepted = this.requestRepository.checkBrotherhoodAccess(principal.getId(), idRequest);
		Assert.isTrue(isAccepted, "RequesService - findOne - Access Denied");
		res = isAccepted ? res : null;
		return res;
	}
	/**
	 * Devuelve un Request vacio.
	 * 
	 * @return Request
	 * @author jmsx
	 */
	public Request create() {
		final Request res = new Request();
		return res;
	}

	public Request save(Request req) {
		//TODO: Falsta comprobar las restricciones de quien guarda el request y en que condiciones.
		final Actor principal = this.actorService.findByPrincipal();
		final Boolean isMember = this.actorService.checkAuthority(principal, Authority.MEMBER);
		final Boolean isBrotherhood = this.actorService.checkAuthority(principal, Authority.BROTHERHOOD);
		if (req.getId() == 0)
			//Creacion de Request, esta debe estar PENDING
			Assert.isTrue(req.getStatus().equals(Request.PENDING), "Request must be create as PENDING");
		else {
			Assert.isTrue(!isMember, "A member cannot update the request");
			Assert.isTrue(isBrotherhood, "Only brotherhood can update a Request");
			Assert.isTrue(!this.requestRepository.checkBrotherhoodAccess(principal.getId(), req.getId()), "This Brotherhood haven't access to this request");
			if (req.getStatus().equals(Request.REJECTED))
				Assert.isTrue(!(req.getExplanation() == "" || req.getExplanation() == null), "If Request is REJECTED must have a explanation");
			if (req.getStatus().equals(Request.APPROVED)) {
				final boolean rowIsNull = req.getRow() == null;
				final boolean columnIsNull = req.getColumn() == null;
				Assert.isTrue(!(rowIsNull || columnIsNull), "If Reuqest is APPROVED, row and column cannot be null ");
			}
		}
		req = this.requestRepository.save(req);
		return req;
	}

	public void delete(final Request req) {
		final Actor principal = this.actorService.findByPrincipal();
		final Boolean isMember = this.actorService.checkAuthority(principal, Authority.MEMBER);
		Assert.isTrue(isMember, "Only a member can delete a request");
		Assert.isTrue(req.getMember().getId() == principal.getId(), "Member must be the own of the request");
		final Request request = this.requestRepository.findOne(req.getId());
		Assert.isTrue(request.getStatus().equals(Request.PENDING), "The Request must be PENDING");
		this.requestRepository.delete(req.getId());
	}
}
