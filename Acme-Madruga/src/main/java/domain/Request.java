
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

@Entity
@Access(AccessType.PROPERTY)
public class Request extends DomainEntity {

	private String				status;
	private Date				moment;
	private String				explanation;

	//Relation attributes
	private Member				member;
	private Procession			procession;
	private ProcessionPosition	processionPosition;


	@Pattern(regexp = "^(APPROVED|PENDING|REJECTED)$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	//@SafeHtml
	public String getExplanation() {
		return this.explanation;
	}

	public void setExplanation(final String explanation) {
		this.explanation = explanation;
	}

	//Relation metods
	@Valid
	@ManyToOne(optional = false)
	public Member getMember() {
		return this.member;
	}

	public void setMember(final Member member) {
		this.member = member;
	}
	@Valid
	@ManyToOne(optional = false)
	public Procession getProcession() {
		return this.procession;
	}

	public void setProcession(final Procession procession) {
		this.procession = procession;
	}

	@Valid
	@OneToOne(optional = true)
	public ProcessionPosition getProcessionPosition() {
		return this.processionPosition;
	}

	public void setProcessionPosition(final ProcessionPosition processionPosition) {
		this.processionPosition = processionPosition;
	}

}
