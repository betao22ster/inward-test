package br.com.inward.test.controle;

import java.lang.reflect.Method;

import br.com.inward.test.controle.bean.ControleBean;
import br.com.inward.test.controle.html.ExibirTelaHtmlFormat;

public class ExibirTestes extends ExecutarTestes {

	private ExibirTelaHtmlFormat htmlFormat = new ExibirTelaHtmlFormat();
	
	public ExibirTestes() {
		super(null, null);
	}

	@Override
	protected void executarListaOrganizada() {
		
		for (ControleBean item : getListaOrganizada()) {
			htmlFormat.appendClasse(item.getClasse().getName());
			
			for( Integer pos : item.getOrdemMetodos().keySet() ){
				Method metodo = item.getOrdemMetodos().get(pos);
				htmlFormat.appendMetodo(item.getClasse().getName(), metodo.getName());
			}
		}
	}
	
	@Override
	protected String getRetornoHtml() {
		return htmlFormat.toString();
	}
}
