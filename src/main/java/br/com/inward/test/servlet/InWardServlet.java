package br.com.inward.test.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.inward.test.controle.ExecutarTestes;
import br.com.inward.test.controle.ExibirTestes;

/**
 * 
 * <p>
 * Servlet principal que inicia toda as execuções de teste.
 * </p>
 * <p>
 * Este servlet deve ser adicionar no web.xml da aplicação que for utilizada para rodar os testes unitários.
 * </p>
 * 
 * @since 12/12/2014
 * @author Marcelo de Souza Vieira
 * @changelog
 */
public class InWardServlet extends HttpServlet {
	private static final long serialVersionUID = 193193439700309869L;

	private final static String PAR_CLASSES = "classes";  
	private final static String PAR_METODOS = "metodos";
	private final static String PAR_EXIBIR_TELA = "exibirTestes";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		controleExecusao(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		controleExecusao(req, resp);
	}

	/**
	 * 
	 * <p>
	 * Método de controle que identifica qual é a execução.
	 * Identifica também os parâmetros que foram enviados.
	 * </p>
	 *
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @since 12/12/2014
	 * @author Marcelo de Souza Vieira
	 * @changelog
	 */
	private void controleExecusao(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		
		if( "S".equals(req.getParameter(PAR_EXIBIR_TELA))){
			execute(resp, new ExibirTestes());
			return;
		}

		String classes = req.getParameter(PAR_CLASSES);
		String metodos = req.getParameter(PAR_METODOS);
		
		if( (classes==null && metodos==null) || ("".equals(classes) && "".equals(metodos) ) ){
			execute(resp, new ExibirTestes());
			return;
		}
		
		execute(resp, new ExecutarTestes(classes, metodos));
	}
	
	/**
	 * 
	 * <p>
	 * Método privado que executa e retorna a resposta no formato HTML.
	 * É chamado pelo servlet.
	 * </p>
	 *
	 * @param resp
	 * @param execucao
	 * @throws IOException
	 * @since 12/12/2014
	 * @author Marcelo de Souza Vieira
	 * @changelog
	 */
	private void execute(HttpServletResponse resp, ExecutarTestes execucao) throws IOException{
		String html = execucao.execute();
		
		PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<body>");
        out.println(html);
        out.println("</body>");
        out.println("</html>");
	}
}
