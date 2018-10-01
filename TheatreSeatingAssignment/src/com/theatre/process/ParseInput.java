package com.theatre.process;

import java.util.List;

import com.theatre.pojos.SeatsDetails;
import com.theatre.pojos.TicketRequest;

public interface ParseInput {

	SeatsDetails parsingSeatInput(StringBuilder seatlayout);
	
	List<TicketRequest> parsingTicketInput(StringBuilder ticketRequests);
}
