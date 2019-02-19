package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.ConfigurationParameters;

public interface ConfigurationParametersRepository extends JpaRepository<ConfigurationParameters, Integer>{

}
