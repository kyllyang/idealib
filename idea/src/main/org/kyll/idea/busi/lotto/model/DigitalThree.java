package org.kyll.idea.busi.lotto.model;

import java.io.Serializable;
import java.util.Date;

public class DigitalThree implements Serializable {
	private String id;
	private Date date;
	private String term;
	private Integer digital0;
	private Integer digital1;
	private Integer digital2;

	public DigitalThree() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Integer getDigital0() {
		return digital0;
	}

	public void setDigital0(Integer digital0) {
		this.digital0 = digital0;
	}

	public Integer getDigital1() {
		return digital1;
	}

	public void setDigital1(Integer digital1) {
		this.digital1 = digital1;
	}

	public Integer getDigital2() {
		return digital2;
	}

	public void setDigital2(Integer digital2) {
		this.digital2 = digital2;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DigitalThree that = (DigitalThree) o;

		if (!term.equals(that.term)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return term.hashCode();
	}
}
