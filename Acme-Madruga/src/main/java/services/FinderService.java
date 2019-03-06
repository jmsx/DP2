
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.FinderRepository;
import security.Authority;
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
	private ProcessionService							processionService;

	@Autowired
	private ConfigurationParametersService				configParamService;

	@Autowired
	private ActorService								actorService;

	@Autowired
	private org.springframework.validation.Validator	validator;


	//Métodos CRUD

	public Finder create() {
		final Finder finder = new Finder();
		finder.setKeyword("");
		finder.setAreaName("");
		finder.setMinDate(null);
		finder.setMaxDate(null);
		final Collection<Procession> ps = new ArrayList<Procession>();
		finder.setProcessions(ps);
		return finder;
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
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, Authority.MEMBER));
		Assert.notNull(finder);
		Assert.isTrue(finder.getId() != 0);
		if (finder.getMinDate() == null)
			finder.setMinDate(new Date());
		if (finder.getMaxDate() == null)
			finder.setMaxDate(new Date());
		final Finder res = this.finderRepository.save(finder);
		//Assert.notNull(me);
		Assert.notNull(res);
		return res;
	}

	public void delete(final Finder finder) {
		Assert.notNull(finder);
		Assert.isTrue(finder.getId() != 0);
		Assert.isTrue(this.finderRepository.exists(finder.getId()));
		this.finderRepository.delete(finder);
	}

	public Finder findMemberFinder(final int id) {
		Assert.isTrue(id != 0);
		final Member member = this.memberService.findOne(id);
		Assert.notNull(member);
		final Finder result = this.finderRepository.findMemberFinder(id);
		Assert.notNull(result);
		return result;
	}

	public Finder clear(final Finder finder, final BindingResult binding) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, Authority.MEMBER));
		final Member member = this.memberService.findByPrincipal();
		final Finder result = this.finderRepository.findMemberFinder(member.getId());
		Assert.isTrue(result.equals(finder));
		Assert.notNull(result);
		result.setKeyword("");
		result.setAreaName("");
		result.setMinDate(null);
		result.setMaxDate(null);
		result.setProcessions(new ArrayList<Procession>());
		final Finder saved = this.finderRepository.save(result);
		member.setFinder(saved);
		this.validator.validate(saved, binding);
		return saved;
	}

	public Collection<Procession> find(final String keyword, final String area, final Date maxDate, final Date minDate) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, Authority.MEMBER));
		final Collection<Procession> finalMode = this.processionService.findAllFinalMode();
		final Collection<Procession> result = new ArrayList<Procession>();
		for (final Procession p : finalMode)
			if ((keyword == "" || (p.getDescription().contains(keyword) || p.getTitle().contains(keyword) || p.getTicker().contains(keyword) && (area == "" || p.getBrotherhood().getArea().getName().contains(area))
				&& (maxDate == null || p.getMoment().before(maxDate)) && (minDate == null || p.getMoment().after(minDate)))))
				result.add(p);
		final Member member = this.memberService.findByPrincipal();
		final Finder finder = this.finderRepository.findMemberFinder(member.getId());
		finder.setAreaName(area);
		finder.setKeyword(keyword);
		finder.setMinDate(minDate);
		finder.setMaxDate(maxDate);
		finder.setProcessions(result);
		final Finder saved = this.finderRepository.save(finder);
		Assert.notNull(saved);
		member.setFinder(saved);
		return result;
	}

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
