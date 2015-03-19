package org.kyll.idea.busi.lotto.model;

import java.io.Serializable;
import java.util.Date;

public class DoubleColor implements Serializable {
	private String id;
	private Date date;
	private String term;
	private Integer red0;
	private Integer red1;
	private Integer red2;
	private Integer red3;
	private Integer red4;
	private Integer red5;
	private Integer blue;

	public DoubleColor() {
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

	public Integer getRed0() {
		return red0;
	}

	public void setRed0(Integer red0) {
		this.red0 = red0;
	}

	public Integer getRed1() {
		return red1;
	}

	public void setRed1(Integer red1) {
		this.red1 = red1;
	}

	public Integer getRed2() {
		return red2;
	}

	public void setRed2(Integer red2) {
		this.red2 = red2;
	}

	public Integer getRed3() {
		return red3;
	}

	public void setRed3(Integer red3) {
		this.red3 = red3;
	}

	public Integer getRed4() {
		return red4;
	}

	public void setRed4(Integer red4) {
		this.red4 = red4;
	}

	public Integer getRed5() {
		return red5;
	}

	public void setRed5(Integer red5) {
		this.red5 = red5;
	}

	public Integer getBlue() {
		return blue;
	}

	public void setBlue(Integer blue) {
		this.blue = blue;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DoubleColor that = (DoubleColor) o;

		if (!term.equals(that.term)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return term.hashCode();
	}
}
