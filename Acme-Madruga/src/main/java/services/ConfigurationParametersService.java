
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationParametersRepository;
import domain.ConfigurationParameters;

@Service
@Transactional
public class ConfigurationParametersService {

	@Autowired
	private ConfigurationParametersRepository	configurationParametersRepository;


	public ConfigurationParameters create() {
		return new ConfigurationParameters();
	}

	public Collection<ConfigurationParameters> findAll() {
		final Collection<ConfigurationParameters> res = this.configurationParametersRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public ConfigurationParameters findOne(final int id) {
		Assert.isTrue(id != 0);
		final ConfigurationParameters res = this.configurationParametersRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}

	public ConfigurationParameters save(final ConfigurationParameters conf) {
		Assert.notNull(conf);
		return this.configurationParametersRepository.save(conf);
	}

	public Boolean checkForSpamWords(final Collection<String> words) {
		Boolean result = false;
		final List<ConfigurationParameters> confs = new ArrayList<>(this.findAll());
		final ConfigurationParameters conf = confs.get(0);
		for (final String word : words)
			for (final String spam : conf.getSpamWords())
				if (word.contains(spam)) {
					result = true;
					break;
				}
		return result;

	}

	public Collection<String> findPositiveWords() {
		final Collection<String> result = new ArrayList<String>();
		final List<ConfigurationParameters> confs = new ArrayList<>(this.findAll());
		final ConfigurationParameters conf = confs.get(0);
		result.addAll(conf.getPositiveWords());
		return result;
	}

	public Collection<String> findNegativeWords() {
		final Collection<String> result = new ArrayList<String>();
		final List<ConfigurationParameters> confs = new ArrayList<>(this.findAll());
		final ConfigurationParameters conf = confs.get(0);
		result.addAll(conf.getNegativeWords());
		return result;
	}

}
