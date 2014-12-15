package br.com.inward.test.util;

import br.com.inward.test.anotacoes.InWardTest;

/**
 * 
 * <p>
 * Classe utilitária para tratar as classes com a acotação inWardTest. 
 * </p>
 *
 * @since 15/12/2014
 * @author Marcelo de Souza Vieira
 * @changelog
 */
public class ClassesUtils {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getNomeClasseOrNomeAnota(Class classe){
		
		InWardTest anotacao = (InWardTest) classe.getAnnotation(InWardTest.class);
		if( anotacao == null ){
			return "";
		}
		
		
		if( !"".equals(anotacao.nome()) ){
			return anotacao.nome();
		}
		
		String nome = tratarNome(classe);
		
		return nome;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getNomeClasse(Class classe){
		
		InWardTest anotacao = (InWardTest) classe.getAnnotation(InWardTest.class);
		if( anotacao == null ){
			return "";
		}
		
		String nome = tratarNome(classe);
		
		return nome;
	}

	@SuppressWarnings("rawtypes")
	private static String tratarNome(Class classe) {
		String[] sep = classe.getName().split("\\.");

		String nome = null;
		if( sep != null ){
			nome = sep[sep.length-1];
		}
		return nome;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getDescricaoClasse(Class classe){
		
		InWardTest anotacao = (InWardTest) classe.getAnnotation(InWardTest.class);
		if( anotacao == null ){
			return "";
		}
		
		return anotacao.descricao();
	}
	
	
}
