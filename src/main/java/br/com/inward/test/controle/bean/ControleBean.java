package br.com.inward.test.controle.bean;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @since 12/12/2014
 * @author Marcelo de Souza Vieira
 * @changelog
 */
public class ControleBean {

	@SuppressWarnings("rawtypes")
	private Class classe;
	private Map<Integer, Method> ordemMetodos;
	private int geradorOrdem = 9999;
	private Method metodoBefore;
	
	@SuppressWarnings("rawtypes")
	public ControleBean(Class classe) {
		super();
		this.classe = classe;
		ordemMetodos = new TreeMap<Integer, Method>();
	}
	
	public void addMetodoBefore(Method metodo){
		this.metodoBefore = metodo;
	}
	
	public void addMetodo(int ordem, Method metodo){
		
		if( ordemMetodos.containsKey(ordem) || ordem == -1){
			// caso a ordem esteja duplciada ou que nao seja definida,
			//é gerado uma ordem 
			ordem = geradorOrdem--;
		}
		
		ordemMetodos.put(ordem, metodo);
	}

	
	@SuppressWarnings("rawtypes")
	public Class getClasse() {
		return classe;
	}
	
	public Map<Integer, Method> getOrdemMetodos() {
		return ordemMetodos;
	}
	
	public Method getMetodoBefore() {
		return metodoBefore;
	}
}
