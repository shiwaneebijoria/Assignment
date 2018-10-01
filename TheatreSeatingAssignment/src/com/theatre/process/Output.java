package com.theatre.process;

import java.util.List;
import java.util.logging.Logger;

import com.theatre.pojos.TicketRequest;

public class Output {
	final static Logger logger = Logger.getLogger(Output.class.getName());
	
	public void outputDisplay(List<TicketRequest> output, List<TicketRequest> req){
		logger.info("\nOutput:\n");

		for (TicketRequest OriginalReq : req) {

			for(TicketRequest result : output){
				
				if(OriginalReq.getPersonName() == result.getPersonName() && 
						OriginalReq.getNoOfTickets() == result.getNoOfTickets()){
					logger.info(getStatus(result));
				}else{
					continue;
				}
			}
			

		}
		}
	
	public static String getStatus(TicketRequest request) {

		String status = null;

		if (request.reqComplete()) {

			status = request.getPersonName() + " " + "Row " + request.getRowNo() + " " + "Section " + request.getSectionNo();

		} else {

			if (request.getRowNo() == -1 && request.getSectionNo() == -1) {

				status = request.getPersonName() + " " + "Call to split party.";

			} else {

				status = request.getPersonName() + " " + "Sorry, we can't handle your party.";

			}

		}

		return status;
	}
}
