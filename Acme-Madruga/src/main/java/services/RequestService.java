
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import security.Authority;
import domain.Actor;
import domain.Procession;
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
			res.addAll(this.requestRepository.findByMember(principal.getUserAccount().getId()));
		else if (this.actorService.checkAuthority(principal, Authority.BROTHERHOOD))
			res.addAll(this.requestRepository.findByBrotherhood(principal.getUserAccount().getId()));
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
			isAccepted = res.getMember().getUserAccount().getId() == principal.getUserAccount().getId();
		else if (this.actorService.checkAuthority(principal, Authority.BROTHERHOOD))
			isAccepted = this.requestRepository.checkBrotherhoodAccess(principal.getUserAccount().getId(), idRequest);
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
		final Actor principal = this.actorService.findByPrincipal();
		final Boolean isMember = this.actorService.checkAuthority(principal, Authority.MEMBER);
		final Boolean isBrotherhood = this.actorService.checkAuthority(principal, Authority.BROTHERHOOD);
		if (req.getId() == 0) {
			//Creacion de Request, esta debe estar PENDING
			Assert.isTrue(req.getStatus().equals(Request.PENDING), "Request must be create as PENDING");
			Assert.isTrue(this.requestRepository.hasMemberRequestToProcession(req.getProcession().getId(), req.getMember().getUserAccount().getId()), "A member cannot request twice to the same procession");
		} else {
			Assert.isTrue(!isMember, "A member cannot update the request");
			Assert.isTrue(isBrotherhood, "Only brotherhood can update a Request");
			Assert.isTrue(!this.requestRepository.checkBrotherhoodAccess(principal.getUserAccount().getId(), req.getId()), "This Brotherhood haven't access to this request");
			if (req.getStatus().equals(Request.REJECTED))
				Assert.isTrue(!(req.getExplanation() == "" || req.getExplanation() == null), "If Request is REJECTED must have a explanation");
			if (req.getStatus().equals(Request.APPROVED)) {
				Assert.isTrue((req.getExplanation() == "" || req.getExplanation() == null), "A explanation musn't be written if you approve the request");
				final boolean rowIsNull = req.getRow() == null && req.getRow() <= req.getProcession().getMaxRows();
				final boolean columnIsNull = req.getColumn() == null && req.getColumn() <= req.getProcession().getMaxColumns();
				Assert.isTrue(!(rowIsNull || columnIsNull), "If Request is APPROVED, row and column cannot be null or greater than maximum allowed");
				Assert.isTrue(this.requestRepository.availableRowColumn(req.getRow(), req.getColumn(), req.getProcession().getId()), "If Request is APPROVED, row and column assigned by brotherhood must be unique");
			}
		}
		req = this.requestRepository.save(req);
		return req;
	}
	public void delete(final Request req) {
		final Actor principal = this.actorService.findByPrincipal();
		final Boolean isMember = this.actorService.checkAuthority(principal, Authority.MEMBER);
		Assert.isTrue(isMember, "Only a member can delete a request");
		Assert.isTrue(req.getMember().getId() == principal.getUserAccount().getId(), "Member must be the own of the request");
		final Request request = this.requestRepository.findOne(req.getId());
		Assert.isTrue(request.getStatus().equals(Request.PENDING), "The Request must be PENDING");
		this.requestRepository.delete(req.getId());
	}

	public List<Integer> suggestPosition(final Procession procession) {
		final int processionId = procession.getId();
		final List<Integer> res = new ArrayList<>();
		for (int i = 1; i <= procession.getMaxRows(); i++) {
			for (int j = 1; j <= procession.getMaxColumns(); j++) {
				if (this.requestRepository.availableRowColumn(i, j, processionId)) {
					res.add(i);
					res.add(j);
					break;
				}
				j++;
			}
			i++;
		}
		return res;
	}
}
