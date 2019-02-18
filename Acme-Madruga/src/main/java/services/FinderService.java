
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Finder;
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

	public Finder save(final int finderId) {
		Assert.notNull(finderId);
		Assert.isTrue(finderId != 0);
		final Finder res = this.finderRepository.findOne(finderId);
		Assert.notNull(res);
		return res;
	}

	public void delete(final Finder finder) {
		Assert.notNull(finder);
		Assert.isTrue(finder.getId() != 0);
		Assert.isTrue(this.finderRepository.exists(finder.getId()));
		this.finderRepository.delete(finder);
	}

	public Collection<Procession> find(final Finder finder) {
		List<Procession> result;

		try {

			final Integer numberOfElementInList = this.configParamService.get.getHutil().getNumberOfResults();
			/*
			 * Crear en el Servicio de ConfigurationParameters:
			 * public ConfigurationParameters getConfigParas(){
			 * List<ConfigurationParameters> configParams = new ArrayList<>(configurationParametersRepository.findAll());
			 * return configParams;
			 * }
			 */

			final String keyword = finder.getKeyword();
			final String areaName = finder.getAreaName();
			final Date minDate = finder.getMinDate();
			final Date maxDate = finder.getMaxDate();
			final List<Procession> aux = this.processionService.findAll();
			if (keyword != null)
				aux.retainAll(this.finderRepository.findForKeyword(keyword));
			else if (areaName != null)
				aux.retainAll(this.finderRepository.findForArea(areaName));
			else if (minDate != null)
				aux.retainAll(this.finderRepository.findForMinDate(minDate));
			else if (maxDate != null)
				aux.retainAll(this.finderRepository.findForMaxDate(maxDate));
			else
				result = aux;

			if (aux.size() > numberOfElementInList)
				result = new ArrayList<>(aux.subList(0, numberOfElementInList));
			else
				result = aux;

			final Member member = this.memberService.findByPrincipal();
			member.getSearches().add(finder);
			this.memberService.save(member);
			return result;
		} catch (final Exception e) {
			e.printStackTrace();
			return new ArrayList<Procession>();
		}

	}
}
