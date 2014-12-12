package br.com.inward.test.controle;

import java.util.List;

import br.com.inward.test.controle.filtro.FiltroClasses;
import br.com.inward.test.scanner.InWardCPScanner;

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
		
		/*for(Class<?> clazz: classes){
		   Method[] metodos = clazz.getMethods();
		   for (Method method : metodos) {
			   if( method.isAnnotationPresent(br.com.inward.test.anotacoes.Test.class) ){
				   Object x;
				try {
					x = clazz.newInstance();
					System.out.println("Executando teste...");
					method.invoke(x, null);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				   
			   }
		   }
		}
		*/
	}
}
