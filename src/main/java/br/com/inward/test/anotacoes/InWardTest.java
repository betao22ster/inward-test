package br.com.inward.test.anotacoes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention (RetentionPolicy.RUNTIME)  
public @interface InWardTest {
	
	String nome() default "";
	
}
