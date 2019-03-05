
package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import domain.Area;

@Entity
@Access(AccessType.PROPERTY)
public class BrotherhoodAreaForm extends ActorFrom {

	private Area	area;


	@Valid
	@ManyToOne(optional = true)
	public Area getArea() {
		return this.area;
	}

	public void setArea(final Area area) {
		this.area = area;
	}

}
