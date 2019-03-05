
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.FinderRepository;
import domain.Actor;
import domain.Finder;
import domain.Member;
import domain.Procession;

@Service
@Transactional
public class FinderService {

	@Autowired
	private FinderRepository							finderRepository;

	@Autowired
	private MemberService								memberService;

	@Autowired
	private org.springframework.validation.Validator	validator;

	@Autowired
	private ProcessionService							processionService;

	@Autowired
	private ConfigurationParametersService				configParamService;

	@Autowired
	private ActorService								actorService;


	//Métodos CRUD

	public Finder create() {
		final Finder result = new Finder();
		result.setKeyword("");
		result.setAreaName("");
		result.setMinDate(null);
		result.setMaxDate(null);
		result.setProcessions(new ArrayList<Procession>());
		return result;
	}

	public Collection<Finder> findAll() {
		final Collection<Finder> res = this.finderRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Finder findOne(final int finderId) {
		Assert.notNull(finderId);
		Assert.isTrue(finderId != 0);
		final Finder res = this.finderRepository.findOne(finderId);
		Assert.notNull(res);
		return res;
	}

	public Finder save(final Finder finder) {
		Assert.notNull(finder);
		Assert.isTrue(finder.getId() != 0);
		final Actor me = this.actorService.findByPrincipal();
		Assert.notNull(me);
		final Finder res = this.finderRepository.save(finder);
		Assert.notNull(res);
		return res;
	}

	public void delete(final Finder finder) {
		Assert.notNull(finder);
		Assert.isTrue(finder.getId() != 0);
		Assert.isTrue(this.finderRepository.exists(finder.getId()));
		this.finderRepository.delete(finder);
	}

	public Finder clear(final Finder finder, final BindingResult binding) {
		Finder result;
		result = this.finderRepository.findOne(finder.getId());
		Assert.notNull(result);
		final Finder clon = (Finder) result.clone();
		clon.setKeyword("");
		clon.setAreaName("");
		clon.setMinDate(null);
		clon.setMaxDate(null);
		clon.setProcessions(new ArrayList<Procession>());

		result = clon;
		this.validator.validate(result, binding);

		return result;
	}

	public Finder reconstruct(final Finder finder, final BindingResult binding) {
		Finder result;
		result = this.finderRepository.findOne(finder.getId());
		Assert.notNull(result);
		final Finder clon = (Finder) result.clone();
		clon.setKeyword(finder.getKeyword());
		clon.setAreaName(finder.getAreaName());
		clon.setMinDate(finder.getMinDate());
		clon.setMaxDate(finder.getMaxDate());
		clon.setProcessions(new ArrayList<Procession>());

		result = clon;
		this.validator.validate(result, binding);

		return result;
	}

	public Finder findMemberFinder(final int id) {
		Assert.isTrue(id != 0);
		final Member member = this.memberService.findOne(id);
		Assert.notNull(member);
		final Finder result = this.finderRepository.findMemberFinder(id);
		Assert.notNull(result);
		return result;
	}

	//	public Collection<Procession> findProcessions(final String keyword, final Date minDate, final Date maxDate, final String area) {
	//		final Actor principal = this.actorService.findByPrincipal();
	//		Assert.isTrue(this.actorService.checkAuthority(principal, Authority.MEMBER));
	//		final UserAccount user = LoginService.getPrincipal();
	//		final int id = user.getId();
	//		final Member member = this.memberService.findOne(id);
	//		Assert.notNull(member);
	//
	//		final Finder result = this.finderRepository.findMemberFinder(member.getId());
	//		Assert.notNull(result);
	//
	//		result.setKeyword(keyword);
	//		result.setAreaName(area);
	//		result.setMinDate(minDate);
	//		result.setMaxDate(maxDate);
	//		final Collection<Procession> proc = new ArrayList<Procession>(this.finderRepository.findProcessions(keyword, minDate, maxDate, area));
	//		result.setProcessions(proc);
	//		final Finder finderSaved = this.finderRepository.save(result);
	//
	//		return finderSaved.getProcessions();
	//	}

	//	public Collection<Procession> find(final Finder finder) {
	//		Collection<Procession> result;
	//
	//		try {
	//
	//			/*
	//			 * Crear en el Servicio de ConfigurationParameters:
	//			 * public ConfigurationParameters getConfigParas(){
	//			 * List<ConfigurationParameters> configParams = new ArrayList<>(configurationParametersRepository.findAll());
	//			 * return configParams;
	//			 * }
	//			 */
	//
	//			final String keyword = finder.getKeyword();
	//			final String areaName = finder.getAreaName();
	//			final Date minDate = finder.getMinDate();
	//			final Date maxDate = finder.getMaxDate();
	//			final Collection<Procession> aux = this.processionService.findAll();
	//			if (keyword != null)
	//				aux.retainAll(this.finderRepository.findForKeyword(keyword));
	//			else if (areaName != null)
	//				aux.retainAll(this.finderRepository.findForArea(areaName));
	//			else if (minDate != null)
	//				aux.retainAll(this.finderRepository.findForMinDate(minDate));
	//			else if (maxDate != null)
	//				aux.retainAll(this.finderRepository.findForMaxDate(maxDate));
	//			else
	//				result = aux;
	//
	//			result = aux;
	//
	//			//			final Member member = this.memberService.findByPrincipal();
	//			//			member.getFinder().add(finder);
	//			//			this.memberService.save(member);
	//			return result;
	//		} catch (final Exception e) {
	//			e.printStackTrace();
	//			return new ArrayList<Procession>();
	//		}
	//
	//	}

	public Double getAverageFinderResults() {
		final Double result = this.finderRepository.getAverageFinderResults();
		Assert.notNull(result);
		return result;
	}

	public Integer getMaxFinderResults() {
		final Integer result = this.finderRepository.getMaxFinderResults();
		Assert.notNull(result);
		return result;
	}

	public Integer getMinFinderResults() {
		final Integer result = this.finderRepository.getMinFinderResults();
		Assert.notNull(result);
		return result;
	}

	public Double getDesviationFinderResults() {
		final Double result = this.finderRepository.getDesviationFinderResults();
		Assert.notNull(result);
		return result;
	}

	public Double getRatioEmptyFinders() {
		final Double result = this.finderRepository.getRatioEmptyFinders();
		Assert.notNull(result);
		return result;
	}
}
