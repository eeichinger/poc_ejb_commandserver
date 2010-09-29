package com.ibm.websphere.ejb3sample.counter;

// This program may be used, executed, copied, modified and distributed
// without royalty for the purpose of developing, using, marketing, or distributing.

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * This servlet demonstrates an EJB3 counter bean with JPA.
 */

public class EJBCount extends HttpServlet {

    private static final long serialVersionUID = -5983708570653958619L;
    
    // Use injection to get the ejb
    @EJB private LocalCounter statelessCounter;
    
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String msg = null;
		int ejbCount = 0;
		
		try {
			ejbCount = statelessCounter.getTheValue();
		} 
		catch (RuntimeException e) {
			msg = "Error - getTheValue() method on EJB failed!";
        	e.printStackTrace();
		}
		msg = "EJB Count value for Stateless Bean with JPA: " + ejbCount;
		
		// Set attibutes and dispatch the JSP.
        req.setAttribute("msg", msg);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/EJBCount.jsp");
        rd.forward(req, res);
	}
    
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String msg = null;
		int ejbCount = 0;
		
		try {
			ejbCount = statelessCounter.increment();
		} 
		catch (RuntimeException e) {
			msg = "Error - increment() method on EJB failed!";
        	e.printStackTrace();
		}
		msg = "EJB Count value for Stateless Bean with JPA: " + ejbCount;
		
		// Set attibutes and dispatch the JSP.
        req.setAttribute("msg", msg);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/EJBCount.jsp");
        rd.forward(req, res);
	}
    

}