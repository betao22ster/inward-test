package br.com.inward.test.controle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import br.com.inward.test.controle.bean.ControleBean;
import br.com.inward.test.controle.html.HtmlFormat;

public class ExecutarTestes {

	private List<Class<?>> classesTeste;
	private List<ControleBean> listaOrganizada;
	private HtmlFormat htmlRetorno = new HtmlFormat();
	private String[] classesFilter;
	private String[] metodosFilter;
	
	public ExecutarTestes(String classesFilter, String metodosFilter) {
		this.classesFilter = classesFilter != null ? classesFilter.split(",") : null;
		this.metodosFilter = metodosFilter != null ? metodosFilter.split(",") : null;
	}
	
	public String execute(){
		classesTeste = ScanearClasses.getInstancia().getClasses(classesFilter);
		listaOrganizada = new OrganizarTestes(classesTeste).getClassesOrganizadas();

		executarListaOrganizada();
		
		return getRetornoHtml();
	}

	protected String getRetornoHtml(){
		return htmlRetorno.toString();
	}
	
	protected void executarListaOrganizada() {
		
		for (ControleBean item : listaOrganizada) {
			
			Object objetoCriado = criarClasseTeste(item.getClasse());
			
			htmlRetorno.append("Testando class....".concat(item.getClasse().getName()));
			
			try {
				executeMetodo(objetoCriado, item.getMetodoBefore(), true);
			} catch (Exception e) {
				e.printStackTrace();
				//htmlRetorno.append(item.getMetodoBefore().getName(), false, e.getMessage());
			}
			
			for( Integer pos : item.getOrdemMetodos().keySet() ){
				Method metodo = item.getOrdemMetodos().get(pos);
				try {
					
					executeMetodo(objetoCriado, metodo);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					htmlRetorno.append(metodo.getName(), false, e.getMessage());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					htmlRetorno.append(metodo.getName(), false, e.getMessage());
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					htmlRetorno.append(metodo.getName(), false, e.getMessage());
				}
			}
			
		}
		
	}

	private void executeMetodo(Object objetoCriado, Method metodo)
			throws IllegalAccessException, InvocationTargetException {
		
		executeMetodo(objetoCriado, metodo, false);
		
	}
	
	private void executeMetodo(Object objetoCriado, Method metodo, boolean isSemLog)
			throws IllegalAccessException, InvocationTargetException {
		
		boolean isExecutar = false;
		
		if( metodosFilter == null ){
			metodo.invoke(objetoCriado, null);
			if( !isSemLog ){
				htmlRetorno.append(metodo.getName(), true);
			}
			return;
		}
		
		for (String item : metodosFilter) {
			if( item == null ){
				continue;
			}
			
			if( metodo.getName().toLowerCase().trim().equals(item.toLowerCase().trim())){
				isExecutar = true;
				break;
			}
		}
	
		if( !isExecutar ){
			return;
		}
		
		metodo.invoke(objetoCriado, null);
		if( !isSemLog ){
			htmlRetorno.append(metodo.getName(), true);
		}
	}

	private Object criarClasseTeste(Class classe) {
		try {
			return classe.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException("A classe " + classe.getName() + " não foi possível ser criada."); 
		} catch (IllegalAccessException e) {
			throw new RuntimeException("A classe " + classe.getName() + " não foi possível ser criada.");
		}
	}
	
	protected List<ControleBean> getListaOrganizada(){
		return listaOrganizada;
	}
	
}
