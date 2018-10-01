package com.theatre.process;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


import com.theatre.pojos.RowSectionDetails;
import com.theatre.pojos.SeatsDetails;
import com.theatre.pojos.TicketRequest;

public class ParseInputData implements ParseInput {
	
	final static Logger logger = Logger.getLogger(ParseInputData.class.getName());

	// Dividing Seats input into row and section
	@Override
	public SeatsDetails parsingSeatInput(StringBuilder seatlayout) {
		SeatsDetails seatingLayout = new SeatsDetails();
		RowSectionDetails rowSectionDetails;
		List<RowSectionDetails> rowSectionDetailsList = new ArrayList<RowSectionDetails>();
		int totalSeats = 0, seats;
		 try {
		String[] rows = seatlayout.toString().split(System.lineSeparator());
		String[] sections;

		for (int i = 0; i < rows.length; i++) {

			sections = rows[i].split(" ");

			for (int j = 0; j < sections.length; j++) {

				seats = Integer.valueOf(sections[j]);

				totalSeats = totalSeats + seats;

				rowSectionDetails = new RowSectionDetails();
				rowSectionDetails.setRowNum(i + 1);
				rowSectionDetails.setSectionNum(j + 1);
				rowSectionDetails.setTotalSeats(seats);
				rowSectionDetails.setVacantSeats(seats);

				rowSectionDetailsList.add(rowSectionDetails);

			}

		}

		seatingLayout.setTotalSeats(totalSeats);
		seatingLayout.setTotalVacantSeats(totalSeats);
		seatingLayout.setSeatsArrgmnt(rowSectionDetailsList);

		
		 } catch (NumberFormatException ex) {
		  
		  
			 logger.info("Seats info is missing or not entered properly.\n" + ex); }
		 

		return seatingLayout;
	}

	// Dividing Ticket Request info into person name and number of seats
	// requested
	@Override
	public List<TicketRequest> parsingTicketInput(StringBuilder ticketRequests) {
		List<TicketRequest> requestsList = new ArrayList<TicketRequest>();
		TicketRequest request;

		
		String[] requests = ticketRequests.toString().split(System.lineSeparator());

		if(requests.length<=0)
			throw new ArrayIndexOutOfBoundsException("Ticket Request input is missing or not entered properly.\n");
		
		for (String personRequest : requests) {

			String[] personReqInfo = personRequest.split(" ");

			request = new TicketRequest();

			request.setPersonName(personReqInfo[0]);

			request.setNoOfTickets(Integer.valueOf(personReqInfo[1]));

			request.setReqComplete(false);

			requestsList.add(request);

		}
		 
			
		 
		return requestsList;
	}

}
