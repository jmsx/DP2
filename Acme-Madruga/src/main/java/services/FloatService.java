
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FloatRepository;
import domain.Actor;
import domain.Brotherhood;
import domain.Float;
import domain.Procession;

@Service
@Transactional
public class FloatService {

	@Autowired
	private FloatRepository					floatRepository;

	@Autowired
	private ProcessionService				processionService;

	@Autowired
	private ConfigurationParametersService	configuracionParametersService;

	@Autowired
	private BrotherhoodService				brotherhoodService;


	//Métodos CRUD

	public Float create() {
		final Float fProcession = new Float();
		final Collection<String> pictures = new ArrayList<>();
		final Brotherhood bhood = this.brotherhoodService.findByPrincipal();
		fProcession.setBrotherhood(bhood);
		fProcession.setPictures(pictures);
		Assert.notNull(fProcession);
		return fProcession;
	}
	public Collection<Float> findAll() {
		final Collection<Float> res = this.floatRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Float findOne(final int floatId) {
		Assert.notNull(floatId);
		Assert.isTrue(floatId != 0);
		final Float res = this.floatRepository.findOne(floatId);
		Assert.notNull(res);
		return res;
	}

	public Float save(final Float f) {
		Assert.notNull(f);
		Assert.isTrue(f.getId() != 0);
		final Actor me = this.brotherhoodService.findByPrincipal();
		Assert.notNull(me);
		final Float res = this.floatRepository.save(f);
		Assert.notNull(res);
		return res;
	}

	public void delete(final Float fProcession) {
		Assert.notNull(fProcession);
		Assert.isTrue(fProcession.getId() != 0);
		Assert.isTrue(this.floatRepository.exists(fProcession.getId()));
		this.floatRepository.delete(fProcession);
	}

	public Collection<Procession> find(final String fProcession) {
		final Collection<Procession> res;

		try {
			final Collection<Procession> aux = this.processionService.findAll();
			aux.retainAll(this.floatRepository.findForFloat(fProcession));
			//			if (aux.size() > numberOfElementInList)
			//				res = new ArrayList<>(aux.subList(0, numberOfElementInList));
			//			else
			res = aux;
			return res;
		} catch (final Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * This method returns all floats associated to a brotherhood
	 * 
	 * @param b
	 *            Brotherhood
	 * 
	 * @author a8081
	 * */
	public Collection<Float> findByBrotherhood(final Brotherhood b) {
		Assert.notNull(b);
		Assert.isTrue(b.getId() != 0);
		final Collection<Float> res = this.floatRepository.findByBrotherhood(b.getUserAccount().getId());
		Assert.notNull(res);
		return res;
	}
}
