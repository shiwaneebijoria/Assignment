package com.theatre.process;

import java.util.List;

import com.theatre.pojos.SeatsDetails;
import com.theatre.pojos.TicketRequest;

public interface AllocateRequest {

	List<TicketRequest> allocateSeatsToPatrons(SeatsDetails layout, List<TicketRequest> requests);
}
