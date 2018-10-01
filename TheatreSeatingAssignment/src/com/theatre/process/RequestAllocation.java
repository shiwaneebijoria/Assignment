package com.theatre.process;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import com.theatre.pojos.RowSectionDetails;
import com.theatre.pojos.SeatsDetails;
import com.theatre.pojos.TicketRequest;

public class RequestAllocation implements AllocateRequest {

	// Check both the input parameters, if any parameter is not provided return
	// false
	private static boolean isEmpty(SeatsDetails layout, List<TicketRequest> requests) {
		if (layout != null && requests.size() != 0)
			return true;

		return false;
	}

	// Return true if request is complete else false
	private static boolean checkStatus(TicketRequest request) {
		if (request.reqComplete())
			return true;

		return false;
	}

	/*
	 * Return true if requested seats is greater than total layout vacant seats
	 * and set the request row and section details to -2
	 */
	private static boolean canHandleReq(TicketRequest request, SeatsDetails layout) {

		if (request.getNoOfTickets() > layout.getTotalVacantSeats()) {

			request.setRowNo(-2);
			request.setSectionNo(-2);
			return true;
		}
		return false;

	}

	// Return true if particular section can handle the request otherwise return
	// false and set status Call to Split Party
	public static boolean checkForSplit(TicketRequest request, List<RowSectionDetails> rowSectionDetList) {

		for (int i = 0; i < rowSectionDetList.size(); i++) {

			if (rowSectionDetList.get(i).getVacantSeats() >= request.getNoOfTickets())
				return true;
		}

		return false;
	}

	// Completing request with section details and mark as complete
	private static void fillSectionWithReq(TicketRequest request, RowSectionDetails rowSectionDetails,
			SeatsDetails layout) {

		request.setRowNo(rowSectionDetails.getRowNum());
		request.setSectionNo(rowSectionDetails.getSectionNum());
		rowSectionDetails.setVacantSeats(rowSectionDetails.getVacantSeats() - request.getNoOfTickets());
		layout.setTotalVacantSeats(layout.getTotalVacantSeats() - request.getNoOfTickets());
		request.setReqComplete(true);

	}

	// Return index of the RowSectionDetails object if section vacant seat is
	// equal to request seat
	public static int checkForMatchingSection(TicketRequest request, List<RowSectionDetails> rowSectionDetList) {
		int index = -1;
		for (int num = 0; num <= rowSectionDetList.size() - 1; num++) {
			RowSectionDetails rowSectionDet = rowSectionDetList.get(num);
			if (rowSectionDet.getVacantSeats() == request.getNoOfTickets()) {

				index = num;
				// System.out.println("checkForMatchingSection" + index);
				break;
			}
		}

		return index;
	}
	/*
	 * Iterate over Row-Section list for particular request, If find exact match
	 * for the request, assign the section. else if section value is greater
	 * than request value, check if any other section is exact match of the
	 * request. if yes put this request into hashmap for later processing. else
	 * assign that section to the request.
	 */

	public static Map<Integer, Integer> checkExactMatchBfrAssign(Map<Integer, Integer> hashMap,
			List<RowSectionDetails> rowSectionDetList, TicketRequest request, SeatsDetails layout, int i) {

		for (int j = 0; j < rowSectionDetList.size(); j++) {

			RowSectionDetails value = rowSectionDetList.get(j);

			if (value.getVacantSeats() >= request.getNoOfTickets()) {

				if (value.getVacantSeats() == request.getNoOfTickets()) {

					fillSectionWithReq(request, value, layout);

				} else {

					if (value.getVacantSeats() > request.getNoOfTickets()) {

						int index = checkForMatchingSection(request, rowSectionDetList);

						if (index > -1) {

							hashMap.put(Integer.valueOf(i), Integer.valueOf(request.getNoOfTickets()));

						} else {

							fillSectionWithReq(request, value, layout);

						}
					}
				}
				break;
			}
		}

		return hashMap;
	}

	/* Get key for the particular Value from HashMap */
	public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
		for (Entry<T, E> entry : map.entrySet()) {
			if (Objects.equals(value, entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

	/* Check if any other request is same as remaining seats of the section */
	public static int checkMapforRemainSeats(Map<Integer, Integer> sorted, int remainSeats, int key,
			List<TicketRequest> requests) {

		if (sorted.containsValue(remainSeats)) {
			for (Map.Entry<Integer, Integer> entry : sorted.entrySet()) {

				if (entry.getValue() == remainSeats && entry.getKey() != key
						&& !requests.get(entry.getKey()).reqComplete()) {

					return entry.getKey();
				}
			}
		}
		return -1;
	}

	/*
	 * For Every hashmap value check every remaining section from Front if
	 * section value == Hashmap Request value assign the section if section
	 * value> Hashmap Value first check if any other request in Hashmap is exact
	 * match for the section if yes, assign that request to section else check
	 * if exact match of (section value - Current Hashmap value) present in the
	 * hashmap. if yes assign both request to section else assign the request to
	 * section
	 */
	private static void processReqFrmRemainings(Map<Integer, Integer> sorted, List<RowSectionDetails> rowSectionDetList,
			List<TicketRequest> requests, SeatsDetails layout) {

		for (Map.Entry<Integer, Integer> entry : sorted.entrySet()) {

			if (!(requests.get(entry.getKey()).reqComplete())) {

				for (int i = 0; i < rowSectionDetList.size(); i++) {

					RowSectionDetails value = rowSectionDetList.get(i);
					if (value.getVacantSeats() >= entry.getValue()) {

						if (value.getVacantSeats() == entry.getValue()) {

							TicketRequest req = requests.get(entry.getKey());
							fillSectionWithReq(req, value, layout);
							sorted.put(entry.getKey(), value.getVacantSeats() - entry.getValue());

						} else {

							if (value.getVacantSeats() > entry.getValue()) {

								if (sorted.containsValue(value.getVacantSeats())) {

									int key = getKeyByValue(sorted, value.getVacantSeats());
									if (!(requests.get(key).reqComplete())) {
										TicketRequest req = requests.get(key);
										fillSectionWithReq(req, value, layout);
										sorted.put(key, value.getVacantSeats() - sorted.get(key));

										continue;

									}
								} else {

									int remainSeats = value.getVacantSeats() - entry.getValue();
									int key = checkMapforRemainSeats(sorted, remainSeats, entry.getKey(), requests);
									if (key > -1) {

										TicketRequest remainReq = requests.get(key);
										fillSectionWithReq(remainReq, value, layout);

										sorted.put(key, sorted.get(key) - remainReq.getNoOfTickets());

										TicketRequest req = requests.get(entry.getKey());
										fillSectionWithReq(req, value, layout);
										sorted.put(entry.getKey(), sorted.get(entry.getKey()) - req.getNoOfTickets());

									} else {

										TicketRequest req = requests.get(entry.getKey());
										fillSectionWithReq(req, value, layout);

									}
								}
							}
						}
						break;
					}
				}
			}

		}
	}

	/* This is the start method for request processing and section assignment */
	public List<TicketRequest> allocateSeatsToPatrons(SeatsDetails layout, List<TicketRequest> requests) {

		Map<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
		if (isEmpty(layout, requests)) {

			List<RowSectionDetails> rowSectionDetList = null;

			Collections.sort(requests);

			for (int i = 0; i < requests.size(); i++) {

				TicketRequest request = requests.get(i);
				if (checkStatus(request) || canHandleReq(request, layout))
					continue;

				rowSectionDetList = layout.getSeatsArrgmnt();
				if (!(checkForSplit(request, rowSectionDetList))) {
					request.setRowNo(-1);
					request.setSectionNo(-1);
					continue;
				} else {

					hashMap = checkExactMatchBfrAssign(hashMap, rowSectionDetList, request, layout, i);
				}

			}

			/*
			 * Map<Integer, Integer> sorted =
			 * hashMap.entrySet().stream().sorted(comparingByValue()).collect(
			 * toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
			 * LinkedHashMap::new));
			 */

			processReqFrmRemainings(hashMap, rowSectionDetList, requests, layout);

		}

		return requests;
	}

}
