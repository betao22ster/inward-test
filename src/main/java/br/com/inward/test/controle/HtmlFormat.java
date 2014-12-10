package br.com.inward.test.controle;

class HtmlFormat {
	private StringBuilder htmlRetorno = new StringBuilder();
	private int contErro;
	private int contAcertos;
	
	public HtmlFormat() {
		htmlRetorno.append("<table>");
		htmlRetorno.append("<tr bgcolor='#EEE9E9'><th>Ação</th><th>Status</th><th>Msg</th></tr>");
	}
	
	private void finalTabela(){
		htmlRetorno.append("</table>");
	}

	private void finalizandoFormatacao(){
		
		StringBuilder htmlRetornoTmp = new StringBuilder();
		
		htmlRetornoTmp.append("<table>");
		htmlRetornoTmp.append("<tr><th>");
		htmlRetornoTmp.append("Resultado do teste:  ");
		htmlRetornoTmp.append(contErro > 0 ? "ERRO" : "SUCESSO");
		htmlRetornoTmp.append(" - ");
		htmlRetornoTmp.append(contAcertos);
		htmlRetornoTmp.append("/");
		htmlRetornoTmp.append(contErro);
		
		/*
		if( contAcertos+contErro > 0 ){
			
			htmlRetornoTmp.append(" - ");
			if( contAcertos > 0 ){
				htmlRetornoTmp.append((100 / contAcertos)  * (contAcertos+contErro));
			}else{
				htmlRetornoTmp.append("0");
			}
			htmlRetornoTmp.append("%");
		}
		*/
		
		htmlRetornoTmp.append("</th></tr>");
		htmlRetornoTmp.append("<table><BR>");
		
		finalTabela();
		
		htmlRetornoTmp.append(htmlRetorno);
		htmlRetorno = htmlRetornoTmp;
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
		htmlRetorno.append("<td>").append(msg).append("</td>");
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
	
	private void controleContagem(boolean status) {
		if( status ){
			contAcertos++;
			return;
		}
		contErro++;
	}

	@Override
	public String toString() {
		finalizandoFormatacao();
		
		return htmlRetorno.toString();
	}
}
