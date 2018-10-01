package com.theatre.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import com.theatre.pojos.SeatsDetails;
import com.theatre.pojos.TicketRequest;

public class Input {

	final static Logger logger = Logger.getLogger(Input.class.getName());

	StringBuilder seatlayout = new StringBuilder();
	StringBuilder ticketRequests = new StringBuilder();

	public void userInput() {

		boolean seatsInfo = false;
		String input;

		logger.info("Please enter Input parameter and if completed please enter 'OUTPUT' for requesting output.\n");

		Scanner scan = new Scanner(System.in);

		try {

			while ((input = scan.nextLine()) != null && !input.equalsIgnoreCase("Output")) {

				if (input.length() == 0) {

					seatsInfo = true;
					continue;

				}

				if (!seatsInfo) {

					seatlayout.append(input + System.lineSeparator());

				} else {

					ticketRequests.append(input + System.lineSeparator());

				}

			}

		} finally {

			scan.close();
		}

		SeatsDetails layout = callParseLayoutInput(seatlayout);
		List<TicketRequest> req = callParseRequestInput(ticketRequests);
		List<TicketRequest> original = new ArrayList<TicketRequest>(req);

		if (layout != null) {
			List<TicketRequest> output = callProcessReq(layout, req);
			Output result = new Output();
			result.outputDisplay(output, original);
		}

	}

	private static SeatsDetails callParseLayoutInput(StringBuilder seatlayout) {

		ParseInput parseInput = new ParseInputData();

		return parseInput.parsingSeatInput(seatlayout);
	}

	private static List<TicketRequest> callParseRequestInput(StringBuilder ticketRequests) {

		ParseInput parseInput = new ParseInputData();

		return parseInput.parsingTicketInput(ticketRequests);
	}

	private static List<TicketRequest> callProcessReq(SeatsDetails layout, List<TicketRequest> requests) {

		AllocateRequest processRequests = new RequestAllocation();

		return processRequests.allocateSeatsToPatrons(layout, requests);

	}

}
