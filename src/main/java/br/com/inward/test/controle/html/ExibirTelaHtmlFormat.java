package br.com.inward.test.controle.html;

import br.com.inward.test.util.ClassesUtils;

public class ExibirTelaHtmlFormat {
	private StringBuilder htmlRetorno = new StringBuilder();

	public ExibirTelaHtmlFormat() {
		htmlRetorno.append("<table>");
	}

	@SuppressWarnings("rawtypes")
	public void appendClasse(Class classe) {
		htmlRetorno.append("<tr bgcolor='#EEE9E9'>");
		htmlRetorno.append("<td>");
		htmlRetorno.append(ClassesUtils.getNomeClasseOrNomeAnota(classe));
		htmlRetorno.append("<br>");
		htmlRetorno.append(ClassesUtils.getDescricaoClasse(classe));
		htmlRetorno.append("</td>");
		htmlRetorno.append("<td>");
		htmlRetorno.append("<a href='InWardExec?classes=");
		htmlRetorno.append(ClassesUtils.getNomeClasse(classe));
		htmlRetorno.append("' target='_blank'>Executar</a>");
		htmlRetorno.append("</td>");
		htmlRetorno.append("</tr>");
	}

	@SuppressWarnings("rawtypes")
	public void appendMetodo(Class classe, String metodo) {
		
		htmlRetorno.append("<tr bgcolor='#98FB98'>");
		htmlRetorno.append("<td>");
		htmlRetorno.append(metodo);
		htmlRetorno.append("</td>");
		htmlRetorno.append("<td>");
		htmlRetorno.append("<a href='InWardExec?classes=");
		htmlRetorno.append(ClassesUtils.getNomeClasse(classe));
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
