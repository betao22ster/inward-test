package br.com.inward.test.controle.html;

public class ExibirTelaHtmlFormat {
	private StringBuilder htmlRetorno = new StringBuilder();

	public ExibirTelaHtmlFormat() {
		htmlRetorno.append("<table>");
	}

	public void appendClasse(String classe) {
		htmlRetorno.append("<tr bgcolor='#EEE9E9'>");
		htmlRetorno.append("<td colsnpan='2'>");
		htmlRetorno.append(classe);
		htmlRetorno.append("</td>");
		htmlRetorno.append("</tr>");
	}

	public void appendMetodo(String classe, String metodo) {
		
		String[] sep = classe.split("\\.");

		String nome = null;
		if( sep != null ){
			nome = sep[sep.length-1];
		}
		
		htmlRetorno.append("<tr bgcolor='#98FB98'>");
		htmlRetorno.append("<td>");
		htmlRetorno.append(metodo);
		htmlRetorno.append("</td>");
		htmlRetorno.append("<td>");
		htmlRetorno.append("<a href='InWardExec?classes=");
		htmlRetorno.append(nome);
		htmlRetorno.append("&metodos=");
		htmlRetorno.append(metodo);
		htmlRetorno.append("' target='_blank'>Executar</a>");
		htmlRetorno.append("</td>");
		htmlRetorno.append("</tr>");
	}

	private void finalizandoFormatacao() {

		StringBuilder htmlRetornoTmp = new StringBuilder();

		htmlRetornoTmp.append("<table>");
		htmlRetornoTmp.append("<tr><th>");
		htmlRetornoTmp.append(" <a href='InWardExec' target='_blank'> ++ Executar todos os teste ++ </a> ");
		htmlRetornoTmp.append("</th></tr>");
		htmlRetornoTmp.append("<table><BR>");
		
		htmlRetornoTmp.append("<table>");
		htmlRetornoTmp.append("<tr><th>");
		htmlRetornoTmp.append("Todos os testes:  ");
		htmlRetornoTmp.append("</th></tr>");
		htmlRetornoTmp.append("<table><BR>");

		finalTabela();

		htmlRetornoTmp.append(htmlRetorno);
		htmlRetorno = htmlRetornoTmp;
	}

	private void finalTabela() {
		htmlRetorno.append("</table>");
	}

	@Override
	public String toString() {
		finalizandoFormatacao();
		return htmlRetorno.toString();
	}
}
