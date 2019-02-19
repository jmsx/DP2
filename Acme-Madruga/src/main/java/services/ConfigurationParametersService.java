package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.ConfigurationParameters;

import repositories.ConfigurationParametersRepository;

@Service
@Transactional
public class ConfigurationParametersService {
	
	@Autowired
	private ConfigurationParametersRepository configurationParametersRepository;
	
	@Autowired
	private ConfigurationParametersService configurationParametersService;
	
	public ConfigurationParameters create(){
		return new ConfigurationParameters();
	}
	
	public Collection<ConfigurationParameters> findAll(){
		final Collection<ConfigurationParameters> res = this.configurationParametersRepository.findAll();
		Assert.notNull(res);
		return res;
	}
	
	public ConfigurationParameters findOne(final int id){
		Assert.isTrue(id != 0);
		final ConfigurationParameters res = this.configurationParametersRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}
	
	public ConfigurationParameters save(final ConfigurationParameters conf){
		Assert.notNull(conf);
		return this.configurationParametersRepository.save(conf);
	}
	
	public Boolean checkForSpamWords(final Collection<String> words){
		Boolean result = false;
		List<ConfigurationParameters> confs = new ArrayList<>(this.findAll());
		ConfigurationParameters conf = confs.get(0);
		for(String word: words){
			for(String spam: conf.getSpamWords()){
				if(word.contains(spam)){
					result = true;
					break;
				}
			}
		}
		return result;
		
	}
}
