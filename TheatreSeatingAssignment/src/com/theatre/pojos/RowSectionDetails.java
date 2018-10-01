package com.theatre.pojos;

import java.util.Objects;

public class RowSectionDetails {

	private int rowNum;
	private int sectionNum;
	private int totalSeats;
	private int vacantSeats;

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public int getSectionNum() {
		return sectionNum;
	}

	public void setSectionNum(int sectionNum) {
		this.sectionNum = sectionNum;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public int getVacantSeats() {
		return vacantSeats;
	}

	public void setVacantSeats(int vacantSeats) {
		this.vacantSeats = vacantSeats;
	}

	@Override
	public int hashCode() {

		return Objects.hash(rowNum, sectionNum, totalSeats, vacantSeats);

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		RowSectionDetails other = (RowSectionDetails) obj;
		if (rowNum != other.rowNum && sectionNum != other.sectionNum && totalSeats != other.totalSeats
				&& vacantSeats != other.vacantSeats)
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "RowSectionDetails [rowNum=" + rowNum + ", sectionNum=" + sectionNum + ", totalSeats=" + totalSeats
				+ ", vacantSeats=" + vacantSeats + "]";
	}

}
