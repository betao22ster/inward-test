package br.com.inward.test.controle;

import java.util.ArrayList;
import java.util.List;

import br.com.inward.test.controle.bean.ControleBean;

class OrganizarTestes {

	private List<Class<?>> classesTeste;

	public OrganizarTestes(List<Class<?>> classesTeste){
		this.classesTeste = classesTeste;
	}
	
	public List<ControleBean> getClassesOrganizadas(){
		
		List<ControleBean> lista = new ArrayList<ControleBean>();
		
		if( classesTeste == null ){
			return lista;
		}
		
		for (Class clazz : classesTeste) {
			lista.add(new OrganizarClasse(clazz).getBean());
		}
		
		return lista;
	}
	
}
