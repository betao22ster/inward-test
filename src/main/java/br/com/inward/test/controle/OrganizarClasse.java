package br.com.inward.test.controle;

import java.lang.reflect.Method;

import org.junit.BeforeClass;

import br.com.inward.test.anotacoes.Test;
import br.com.inward.test.controle.bean.ControleBean;

class OrganizarClasse {

	private Class clazz;

	public OrganizarClasse(Class clazz) {
		this.clazz = clazz;
	}

	public ControleBean getBean() {

		ControleBean bean = new ControleBean(clazz);
		
		Method[] metodos = clazz.getMethods();
		for (Method method : metodos) {
			if (method.isAnnotationPresent(BeforeClass.class)) {
				bean.addMetodoBefore(method);
				continue;
			}
			
			if (method.isAnnotationPresent(br.com.inward.test.anotacoes.Test.class)) {
				Test teste = method.getAnnotation(Test.class);
				if( !teste.disabilitar() ){
					bean.addMetodo(teste.ordem(), method);
				}
			}
		}
		
		return bean;
	}

}
