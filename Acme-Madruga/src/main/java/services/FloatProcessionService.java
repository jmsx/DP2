
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FloatProcessionRepository;
import domain.Brotherhood;
import domain.FloatProcession;
import domain.Procession;

@Service
@Transactional
public class FloatProcessionService {

	@Autowired
	private FloatProcessionRepository		floatProcessionRepository;

	@Autowired
	private ProcessionService				processionService;

	@Autowired
	private ConfigurationParametersService	configuracionParametersService;


	//Métodos CRUD

	public FloatProcession create() {
		final FloatProcession fProcession = new FloatProcession();
		final Collection<String> pictures = new ArrayList<>();
		final Brotherhood bhood = new Brotherhood();
		fProcession.setBrotherhood(bhood);
		fProcession.setPictures(pictures);
		return fProcession;
	}

	public Collection<FloatProcession> findAll() {
		final Collection<FloatProcession> res = this.floatProcessionRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public FloatProcession save(final int floatId) {
		Assert.notNull(floatId);
		Assert.isTrue(floatId != 0);
		final FloatProcession res = this.floatProcessionRepository.findOne(floatId);
		Assert.notNull(res);
		return res;
	}

	public void delete(final FloatProcession fProcession) {
		Assert.notNull(fProcession);
		Assert.isTrue(fProcession.getId() != 0);
		Assert.isTrue(this.floatProcessionRepository.exists(fProcession.getId()));
		this.floatProcessionRepository.delete(fProcession);
	}

	public Collection<Procession> find(final String fProcession) {
		final List<Procession> res;
		final Integer numberOfElementInList = this.configuracionParametersService.get.getNumberOfResults();

		try {
			final List<Procession> aux = this.processionService.findAll();
			aux.retainAll(this.floatProcessionRepository.findForFloat(fProcession));
			if (aux.size() > numberOfElementInList)
				res = new ArrayList<>(aux.subList(0, numberOfElementInList));
			else
				res = aux;
			return res;
		} catch (final Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
}
