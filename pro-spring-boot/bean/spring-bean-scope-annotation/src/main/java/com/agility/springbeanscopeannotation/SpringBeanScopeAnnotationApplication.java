package com.agility.springbeanscopeannotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// https://grokonez.com/spring-framework/spring-core/spring-bean-scope-using-annotation-singleton-prototype-request-session-global-session-application

// Proxy Mode TARGET_CLASS:  is used for creating AOP proxy of the bean. We must do it when injecting short lived bean scope (request, session…) to long lived bean scope (singleton, prototype) or injecting prototype to singleton scope.
// singleton: only one instance is created (default scope)
// prototype: new instance is created everytime prototype bean is referenced
// request: one instance for a single HTTP request
// session: one instance for an HTTP session
// globalSession: one instance for a global HTTP Session
// application: Scopes a single bean definition to the lifecycle of a ServletContext
// @RequestScope: is a annotation that acts as a shortcut for @Scope("request") with the proxyMode set to TARGET_CLASS
// @SessionScope: is a annotation that acts as a shortcut for @Scope("session") with the proxyMode set to TARGET_CLASS
// @ApplicationScope: is a annotation that acts as a shortcut for @Scope("application") with the proxyMode set to TARGET_CLASS
@SpringBootApplication
public class SpringBeanScopeAnnotationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBeanScopeAnnotationApplication.class, args);
	}
}
