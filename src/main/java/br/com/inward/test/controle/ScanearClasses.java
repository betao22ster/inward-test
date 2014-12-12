package br.com.inward.test.controle;

import java.util.List;

import br.com.inward.test.controle.filtro.FiltroClasses;
import br.com.inward.test.scanner.InWardCPScanner;

/**
 * 
 * @since 12/12/2014
 * @author Marcelo de Souza Vieira
 * @changelog
 */
public class ScanearClasses {
	
	private final static ScanearClasses instancia = new ScanearClasses();
	
	private ScanearClasses() {
	}
	
	public static ScanearClasses getInstancia(){
		return instancia;
	}
	
	public List<Class<?>> getClasses(String[] classesFilter){
		
		List<Class<?>> classes = InWardCPScanner.scanClasses(FiltroClasses.getInstance().getFiltro(classesFilter));
		
		return classes;
	}
}
