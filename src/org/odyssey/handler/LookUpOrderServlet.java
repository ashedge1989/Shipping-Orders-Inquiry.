package org.odyssey.handler;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.odyssey.config.ConfigOdysseyTransportation;
import org.odyssey.model.Order;
import org.odyssey.service.LookUpOrderService;

import com.google.gson.Gson;

public class LookUpOrderServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Order retrievedOrder = null;
		String jsonString = "";
		Gson gson = new Gson();
		int orderNum = 0;
		boolean err = false;

		// get the order number that user wants to search for
		String str = req.getParameter("userSearchOrderNumber");

		try {
			orderNum = Integer.parseInt(str); // if fails throws NuberFormatException.
			
		} catch (NumberFormatException e) {
			// user has entered data in wrong fomrat
			err = true;
		}

		if (!err) {
			// Check whether 'Order' is already cached
			retrievedOrder = (Order) ConfigOdysseyTransportation.orderCache.get(String.valueOf(orderNum));
			if (retrievedOrder != null) {

				System.out.println("Data found in cache");
				// Value is present, return with data.
				jsonString = gson.toJson(retrievedOrder);

			} else {
				System.out.println("going to retrieve data");	
				// Value not present in Cache, get it from DataBase
				LookUpOrderService lookUpService = new LookUpOrderService();
				retrievedOrder = lookUpService.getOrderById(orderNum);

				// Save this retrieved order to Cache
				ConfigOdysseyTransportation.orderCache.put(
						String.valueOf(retrievedOrder.getOrderId()),
						retrievedOrder);

				// Convert this 'Order' object into a JSON object.
				jsonString = gson.toJson(retrievedOrder);

			}

			req.setAttribute("jsonString", jsonString.toString());

		}
		
		req.setAttribute("hasReturned", "lookup");
		RequestDispatcher dispatcher = req
				.getRequestDispatcher("view/OdysseOrderHome.jsp");
		dispatcher.forward(req, resp);

	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("Inside post method of look up order ");

	}

}
