package br.com.inward.test.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.inward.test.controle.ExecutarTestes;


public class InWardServlet extends HttpServlet {
	private static final long serialVersionUID = 193193439700309869L;

	private final static String PAR_CLASSES = "classes";  
	private final static String PAR_METODOS = "metodos";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		execute(resp, req.getParameter(PAR_CLASSES), req.getParameter(PAR_METODOS));
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		execute(resp, req.getParameter(PAR_CLASSES), req.getParameter(PAR_METODOS));
	}
	
	private void execute(HttpServletResponse resp, String classes, String metodos) throws IOException{
		System.out.println("servlet...");
		
		String html =  new ExecutarTestes(classes, metodos).execute();
		
		PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<body>");
        out.println(html);
        out.println("</body>");
        out.println("</html>");
	}
}
