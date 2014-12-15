package br.com.inward.test.anotacoes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * <p>
 * Anotação principal que deve ser adicionada nas classes para que o
 * controle possa identificar que a classe é uma classe de teste 
 * </p>
 *
 * @since 12/12/2014
 * @author Marcelo de Souza Vieira
 * @changelog
 */
@Retention (RetentionPolicy.RUNTIME)  
public @interface InWardTest {
	
	String nome() default "";
	String descricao() default "";
	
}
