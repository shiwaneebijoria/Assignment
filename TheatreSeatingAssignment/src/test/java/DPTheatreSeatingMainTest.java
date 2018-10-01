package test.java;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import com.theatre.pojos.SeatsDetails;
import com.theatre.pojos.TicketRequest;
import com.theatre.process.RequestAllocation;
import com.theatre.process.Output;
import com.theatre.process.ParseInput;
import com.theatre.process.ParseInputData;
import com.theatre.process.AllocateRequest;

public class DPTheatreSeatingMainTest {

	@Test
	public void testAllocateSeatPerReq() {

		ParseInput parseInput = new ParseInputData();

		StringBuilder seatlayout = new StringBuilder();

		seatlayout.append("6 6" + System.lineSeparator());
		seatlayout.append("3 5 5 3" + System.lineSeparator());
		seatlayout.append("4 6 6 4" + System.lineSeparator());
		seatlayout.append("2 8 8 2" + System.lineSeparator());
		seatlayout.append("6 6" + System.lineSeparator());

		SeatsDetails layout = parseInput.parsingSeatInput(seatlayout);

		StringBuilder ticketRequest = new StringBuilder();

		ticketRequest.append("Smith 2" + System.lineSeparator());
		ticketRequest.append("Jones 5" + System.lineSeparator());
		ticketRequest.append("Davis 6" + System.lineSeparator());
		ticketRequest.append("Wilson 100" + System.lineSeparator());
		ticketRequest.append("Johnson 3" + System.lineSeparator());
		ticketRequest.append("Williams 4" + System.lineSeparator());
		ticketRequest.append("Brown 8" + System.lineSeparator());
		ticketRequest.append("Miller 12" + System.lineSeparator());

		List<TicketRequest> requests = parseInput.parsingTicketInput(ticketRequest);
		List<TicketRequest> original = new ArrayList<TicketRequest>(requests);
		AllocateRequest processRequests = new RequestAllocation();
		List<TicketRequest> ticketReqList = processRequests.allocateSeatsToPatrons(layout, requests);

		String result[] = new String[ticketReqList.size()];
		int index = 0;

		for (TicketRequest OriginalReq : original) {

			for (TicketRequest output : ticketReqList) {

				if (OriginalReq.getPersonName() == output.getPersonName()
						&& OriginalReq.getNoOfTickets() == output.getNoOfTickets()) {
					result[index] = Output.getStatus(output);
					index++;
					break;
				} else {
					continue;
				}
			}

		}

		assertEquals("Smith Row 1 Section 2", result[0]);
		assertEquals("Jones Row 2 Section 2", result[1]);
		assertEquals("Davis Row 1 Section 1", result[2]);
		assertEquals("Wilson Sorry, we can't handle your party.", result[3]);
		assertEquals("Johnson Row 2 Section 1", result[4]);
		assertEquals("Williams Row 1 Section 2", result[5]);
		assertEquals("Brown Row 4 Section 2", result[6]);
		assertEquals("Miller Call to split party.", result[7]);

	}

	@Test(expected = java.lang.ArrayIndexOutOfBoundsException.class)
	public void testParsingTicketInput() {

		ParseInput parseInput = new ParseInputData();

		StringBuilder ticketRequest = new StringBuilder();
		ticketRequest.append("" + System.lineSeparator());

		List<TicketRequest> requests = parseInput.parsingTicketInput(ticketRequest);

		assertEquals(0, requests.size());

	}

}
