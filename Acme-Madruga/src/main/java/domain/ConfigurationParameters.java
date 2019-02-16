
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class ConfigurationParameters extends DomainEntity {

	private String					sysName;
	private String					banner;
	private String					welcomeMessageEsp;
	private String					welcomeMessageEn;
	private String					countryPhoneCode;
	private Collection<Position>	positions;
	private Collection<String>		positiveWords;
	private Collection<String>		negativeWords;
	private Collection<String>		spamWords;
	private int						maxFinderResults;
	private int						finderTime;


	@NotBlank
	public String getSysName() {
		return this.sysName;
	}

	public void setSysName(final String sysName) {
		this.sysName = sysName;
	}

	@URL
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	public String getWelcomeMessageEsp() {
		return this.welcomeMessageEsp;
	}

	public void setWelcomeMessageEsp(final String welcomeMessageEsp) {
		this.welcomeMessageEsp = welcomeMessageEsp;
	}

	@NotBlank
	public String getWelcomeMessageEn() {
		return this.welcomeMessageEn;
	}

	public void setWelcomeMessageEn(final String welcomeMessageEn) {
		this.welcomeMessageEn = welcomeMessageEn;
	}

	//TODO
	@Pattern(regexp = "^[+][1-9]\\d{0,2}")
	public String getCountryPhoneCode() {
		return this.countryPhoneCode;
	}

	public void setCountryPhoneCode(final String countryPhoneCode) {
		this.countryPhoneCode = countryPhoneCode;
	}

	@Valid
	@NotEmpty
	public Collection<Position> getPositions() {
		return this.positions;
	}

	public void setPositions(final Collection<Position> positions) {
		this.positions = positions;
	}

	//@EachNotBlank
	//@ElementCollection
	@NotBlank
	public Collection<String> getPositiveWords() {
		return this.positiveWords;
	}

	public void setPositiveWords(final Collection<String> positiveWords) {
		this.positiveWords = positiveWords;
	}

	//@EachNotBlank
	//@ElementCollection
	@NotBlank
	public Collection<String> getNegativeWords() {
		return this.negativeWords;
	}

	public void setNegativeWords(final Collection<String> negativeWords) {
		this.negativeWords = negativeWords;
	}

	//@EachNotBlank
	//@ElementCollection
	@NotBlank
	public Collection<String> getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final Collection<String> spamWords) {
		this.spamWords = spamWords;
	}

	@Range(min = 10, max = 100)
	public int getMaxFinderResults() {
		return this.maxFinderResults;
	}

	public void setMaxFinderResults(final int maxFinderResults) {
		this.maxFinderResults = maxFinderResults;
	}

	@Range(min = 1, max = 24)
	public int getFinderTime() {
		return this.finderTime;
	}

	public void setFinderTime(final int finderTime) {
		this.finderTime = finderTime;
	}

}
