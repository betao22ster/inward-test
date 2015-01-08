package br.com.inward.test.controle.html;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.rmi.server.LogStream;


public class HtmlFormat {
	private StringBuilder htmlRetorno = new StringBuilder();
	private int contErro;
	private int contAcertos;
	
	
	
	private void compilarHtml(){
		// guardando os dados já incluidos
		StringBuilder tmp = htmlRetorno;
		
		htmlRetorno = new StringBuilder();

		cabecalho();
		inicioRelatorio();
		
		htmlRetorno.append(tmp);
		
		finalTabela();
	
	}
	
	private void inicioRelatorio() {
		htmlRetorno.append("<table width='100%' border='0'>");
		htmlRetorno.append("<tr><th width='50%'>");
		
		htmlRetorno.append("<table>");
		htmlRetorno.append("<tr bgcolor='#EEE9E9'><th>Ação</th><th>Status</th><th>Msg</th></tr>");
		
	}

	private void finalTabela(){
		htmlRetorno.append("</table>");
		
		htmlRetorno.append("</th><th>");
		
		gerarGrafico();
		
		htmlRetorno.append("</th></tr>");
		htmlRetorno.append("</table>");
	}

	private void gerarGrafico() {
		StringBuilder grafico = new StringBuilder();
		grafico.append("  <div id=\"piechart\" style=\"width: 900px; height: 500px;\"></div> \n");
		
		
		grafico.append("<script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script> \n");
		grafico.append("<script type=\"text/javascript\"> \n\n\n");
		
		grafico.append("   google.load(\"visualization\", \"1\", {packages:[\"corechart\"]}); \n\n\n");
		
		grafico.append("  function drawChart() { \n");
		grafico.append("       var data = google.visualization.arrayToDataTable([ \n");
		grafico.append("        ['Stats', 'Speakers (in millions)'], \n");
		grafico.append("        ['Sucesso',  ").append(contAcertos).append("], \n");
		grafico.append("        ['Erro',  ").append(contErro).append("] \n");
		grafico.append("      ]); \n\n\n");

		grafico.append("    var options = { \n");
		grafico.append("      legend: 'none', \n");
		grafico.append("      pieSliceText: 'label',\n ");
		grafico.append("      title: 'inward-test unit', \n");
		grafico.append("      pieStartAngle: 100, \n");
		grafico.append("    }; \n\n\n ");

		grafico.append("      var chart = new google.visualization.PieChart(document.getElementById('piechart')); \n\n\n");
		grafico.append("      chart.draw(data, options); \n\n\n");
		
		grafico.append("    } \n");
		
		
		grafico.append(" window.onload = drawChart; \n");
		
		grafico.append("  </script> \n");
	      
		htmlRetorno.append(grafico);
	}

	private void cabecalho(){
		
		htmlRetorno.append("<table>");
		htmlRetorno.append("<tr><th>");
		htmlRetorno.append("Resultado do teste:  ");
		htmlRetorno.append(contErro > 0 ? "ERRO" : "SUCESSO");
		htmlRetorno.append(" - ");
		htmlRetorno.append(contAcertos);
		htmlRetorno.append("/");
		htmlRetorno.append(contErro+contAcertos);
		
		htmlRetorno.append("</th></tr>");
		htmlRetorno.append("<table><BR>");
		
		
	}
	
	public void append(String acao){
		htmlRetorno.append("<tr>");
		htmlRetorno.append("<td cospan='3'>").append(acao).append("</td>");
		htmlRetorno.append("</tr>");
	}
	
	public void append(String acao, boolean status, String msg){
		htmlRetorno.append("<tr bgcolor='").append(status ? "#98FB98" : "#FA8072").append("'>");
		htmlRetorno.append("<td>").append(acao).append("</td>");
		htmlRetorno.append("<td>").append(status ? "OK" : "Erro").append("</td>");
		htmlRetorno.append("<td>").append(msg).append("<br>").append("</td>");
		htmlRetorno.append("</tr>");
		
		controleContagem(status);
	}
	
	public void append(String acao, boolean status){
		htmlRetorno.append("<tr bgcolor='").append(status ? "#98FB98" : "#FA8072").append("'>");
		htmlRetorno.append("<td>").append(acao).append("</td>");
		htmlRetorno.append("<td colspan='2'>").append(status ? "OK" : "Erro").append("</td>");
		htmlRetorno.append("</tr>");
		
		controleContagem(status);
	}
	
	public void append(String acao, boolean status, long tempoExecCodigo){
		htmlRetorno.append("<tr bgcolor='").append(status ? "#98FB98" : "#FA8072").append("'>");
		htmlRetorno.append("<td>").append(acao).append("</td>");
		htmlRetorno.append("<td colspan='2'>").append(status ? "OK" : "Erro").append("</td>");
		htmlRetorno.append("<td>").append( String.format( "%.3f ms%n", (tempoExecCodigo / 1000d) ) ).append("</td>");
		
		htmlRetorno.append("</tr>");
		
		controleContagem(status);
	}
	
	private void controleContagem(boolean status) {
		if( status ){
			contAcertos++;
			return;
		}
		contErro++;
	}

	@Override
	public String toString() {
		compilarHtml();
		
		return htmlRetorno.toString();
	}
}
