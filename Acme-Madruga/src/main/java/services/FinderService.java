
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
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
	private org.springframework.validation.Validator	validator;

	@Autowired
	private ActorService								actorService;


	//Metodos CRUD

	public Finder create() {
		final Finder finder = new Finder();
		finder.setKeyword("");
		finder.setAreaName("");
		finder.setMinDate(null);
		finder.setMaxDate(null);
		final Collection<Procession> ps = new ArrayList<Procession>();
		finder.setCreationDate(new Date());
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

	// Antes de guardar tengo que pasar por este metodo para setearle las nuevas procesiones segun los nuevos parametros
	public Finder find(final Finder finder) {
		this.memberService.findByPrincipal();
		final Collection<Procession> result = this.finderRepository.findProcessions(finder.getKeyword(), finder.getMinDate(), finder.getMaxDate(), finder.getAreaName());
		finder.setProcessions(result);
		return this.save(finder);
	}

	public Finder save(final Finder finder) {
		final Member member = this.memberService.findByPrincipal();
		Assert.notNull(finder);
		Assert.isTrue(finder.getId() != 0);
		Assert.isTrue(this.finderRepository.findMemberFinder(member.getId()).getId() == finder.getId(), "You're not owner of this finder, you cannot modify it");

		finder.setCreationDate(new Date());
		final Finder res = this.finderRepository.save(finder);
		Assert.notNull(res);

		member.setFinder(finder);
		this.memberService.save(member);
		return res;
	}

	public Finder createForNewMember() {
		final Finder finder = new Finder();
		finder.setKeyword("");
		finder.setAreaName("");
		finder.setMinDate(null);
		finder.setMaxDate(null);
		final Collection<Procession> ps = new ArrayList<Procession>();
		finder.setCreationDate(new Date());
		finder.setProcessions(ps);
		final Finder res = this.finderRepository.save(finder);
		return res;
	}

	public void delete(final Finder finder) {
		Assert.notNull(finder);
		final Member member = this.memberService.findByPrincipal();
		Assert.isTrue(finder.getId() != 0);
		Assert.isTrue(this.finderRepository.exists(finder.getId()));
		Assert.isTrue(this.finderRepository.findMemberFinder(member.getId()).getId() == finder.getId(), "You're not owner of this finder, you cannot delete it");
		this.finderRepository.delete(finder);
	}

	public Finder findMemberFinder() {
		final Member principal = this.memberService.findByPrincipal();

		final Finder finder = this.finderRepository.findMemberFinder(principal.getId());
		Assert.notNull(finder);

		final int finderTime = this.configParamService.find().getFinderTime();
		final LocalDateTime ldt = new LocalDateTime(finder.getCreationDate());
		ldt.plusHours(finderTime);

		if (ldt.isBefore(LocalDateTime.now()))
			this.clear(finder);

		return finder;
	}

	public Finder clear(final Finder finder) {
		final Member member = this.memberService.findByPrincipal();
		final Finder result = this.finderRepository.findMemberFinder(member.getId());
		Assert.isTrue(result.equals(finder), "You're not owner of this finder");
		Assert.notNull(result);
		result.setKeyword("");
		result.setAreaName("");
		result.setMinDate(null);
		result.setMaxDate(null);
		result.setProcessions(new ArrayList<Procession>());
		final Finder saved = this.save(result);
		return saved;
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
