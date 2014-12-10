package br.com.inward.test.controle.filtro;

import net.sf.corn.cps.ClassFilter;
import br.com.inward.test.anotacoes.InWardTest;

public class FiltroClasses {

	private static FiltroClasses filtro = new FiltroClasses();
	
	private FiltroClasses(){
	}
	
	public static FiltroClasses getInstance(){
		return filtro;
	}
	
	public ClassFilter getFiltro(String[] classes){
		ClassFilter filter = new ClassFilter();
		filter.annotation(InWardTest.class);
		
		if( classes == null ){
			return filter;
		}
		
		for (String clazz : classes) {
			filter.className(clazz);
		}
		
		return filter;
	}
	
	public ClassFilter getFiltro(){
		return new ClassFilter().annotation(InWardTest.class);
	}
}
