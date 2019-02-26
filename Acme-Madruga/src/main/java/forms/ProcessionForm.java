
package forms;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

import domain.DomainEntity;
import domain.Float;

@Entity
@Access(AccessType.PROPERTY)
public class ProcessionForm extends DomainEntity {

	private String				title;
	private String				description;
	private Integer				maxRows;
	private Integer				maxColumns;

	//Relational atributes
	private Collection<Float>	floats;


	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	//@SafeHtml
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	//Relational Methods
	@Valid
	@ManyToMany
	public Collection<Float> getFloats() {
		return this.floats;
	}

	public void setFloats(final Collection<Float> floats) {
		this.floats = floats;
	}

	@Min(1)
	public Integer getMaxRows() {
		return this.maxRows;
	}

	public void setMaxRows(final Integer maxRows) {
		this.maxRows = maxRows;
	}

	@Min(1)
	public Integer getMaxColumns() {
		return this.maxColumns;
	}

	public void setMaxColumns(final Integer maxColumns) {
		this.maxColumns = maxColumns;
	}
}
