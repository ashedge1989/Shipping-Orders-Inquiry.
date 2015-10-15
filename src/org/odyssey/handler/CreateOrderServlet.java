package org.odyssey.handler;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.odyssey.config.ConfigOdysseyTransportation;
import org.odyssey.service.CreateOrderService;

public class CreateOrderServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// get the parameters
		String creaOrderFile = req.getParameter("creaOrderFile");
		ServletContext context = req.getServletContext();
		String path = context.getInitParameter("inputFilePath") + creaOrderFile;
		
		if (!creaOrderFile.isEmpty()) {
			// parse the XML file & save the 'Order' object to database
			CreateOrderService service = new CreateOrderService();

			// ServletContext context = req.getServletContext();
			int orderId = service.saveOrder(path);
			
			// return the 'orderId' of the saved 'Order'
			req.setAttribute("savedOrderNumber", orderId);
			
		}

		req.setAttribute("hasReturned", "create");
		RequestDispatcher dispatcher = req.getRequestDispatcher("view/OdysseOrderHome.jsp");
		dispatcher.forward(req, resp);

	}

}
