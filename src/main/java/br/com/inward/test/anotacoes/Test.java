package br.com.inward.test.anotacoes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention (RetentionPolicy.RUNTIME)
public @interface Test {
	// pode ser definido a ordem da execução
	int ordem() default -1;
}
