package com.theatre.pojos;

import java.util.Objects;

public class TicketRequest implements Comparable<TicketRequest> {

	private String personName;
	private int noOfTickets;
	private boolean reqComplete;
	private int rowNo;
	private int sectionNo;

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public int getNoOfTickets() {
		return noOfTickets;
	}

	public void setNoOfTickets(int noOfTickets) {
		this.noOfTickets = noOfTickets;
	}

	public boolean reqComplete() {
		return reqComplete;
	}

	public void setReqComplete(boolean reqComplete) {
		this.reqComplete = reqComplete;
	}

	public int getRowNo() {
		return rowNo;
	}

	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}

	public int getSectionNo() {
		return sectionNo;
	}

	public void setSectionNo(int sectionNo) {
		this.sectionNo = sectionNo;
	}

	@Override
	public int hashCode() {

		return Objects.hash(noOfTickets, personName, reqComplete, rowNo, sectionNo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TicketRequest other = (TicketRequest) obj;

		if (personName == null) {
			if (other.personName != null)
				return false;
		}
		if (reqComplete != other.reqComplete && rowNo != other.rowNo && sectionNo != other.sectionNo
				&& noOfTickets != other.noOfTickets && !personName.equals(other.personName))
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "TicketRequest [personName=" + personName + ", noOfTickets=" + noOfTickets + ", reqComplete="
				+ reqComplete + ", rowNo=" + rowNo + ", sectionNo=" + sectionNo + "]";
	}

	@Override
	public int compareTo(TicketRequest o) {

		return this.noOfTickets - o.noOfTickets;
	}

}
