
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Actor;
import domain.Finder;
import domain.Member;
import domain.Procession;

@Service
@Transactional
public class FinderService {

	@Autowired
	private FinderRepository				finderRepository;

	@Autowired
	private MemberService					memberService;

	@Autowired
	private ProcessionService				processionService;

	@Autowired
	private ConfigurationParametersService	configParamService;

	@Autowired
	private ActorService					actorService;


	//Métodos CRUD

	public Finder create() {
		final Finder finder = new Finder();
		final Collection<Procession> ps = new ArrayList<>();
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

	public Finder findMemberFinder(final int id) {
		Assert.isTrue(id != 0);
		final Member member = this.memberService.findOne(id);
		Assert.notNull(member);
		final Finder result = this.finderRepository.findMemberFinder(id);
		Assert.notNull(result);
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
