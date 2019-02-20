
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
	private Integer				row;
	private Integer				column;

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

	@Column(name = "row_position")
	public int getRow() {
		return this.row;
	}

	public void setRow(final int row) {
		this.row = row;
	}

	@Column(name = "column_position")
	public int getColumn() {
		return this.column;
	}

	public void setColumn(final int column) {
		this.column = column;
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

}
