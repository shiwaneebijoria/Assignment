package com.theatre.pojos;

import java.util.List;
import java.util.Objects;

public class SeatsDetails {

	private int totalSeats;
	private int totalVacantSeats;
	private List<RowSectionDetails> seatsArrgmnt;

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public int getTotalVacantSeats() {
		return totalVacantSeats;
	}

	public void setTotalVacantSeats(int totalVacantSeats) {
		this.totalVacantSeats = totalVacantSeats;
	}

	public List<RowSectionDetails> getSeatsArrgmnt() {
		return seatsArrgmnt;
	}

	public void setSeatsArrgmnt(List<RowSectionDetails> seatsArrgmnt) {
		this.seatsArrgmnt = seatsArrgmnt;
	}

	@Override
	public int hashCode() {

		return Objects.hash(seatsArrgmnt, totalSeats, totalVacantSeats);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SeatsDetails other = (SeatsDetails) obj;
		if (seatsArrgmnt == null) {
			if (other.seatsArrgmnt != null)
				return false;
		}
		if (totalSeats != other.totalSeats && totalVacantSeats != other.totalVacantSeats
				&& !seatsArrgmnt.equals(other.seatsArrgmnt))
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "SeatsDetails [totalSeats=" + totalSeats + ", totalVacantSeats=" + totalVacantSeats + ", seatsArrgmnt="
				+ seatsArrgmnt + "]";
	}

}
