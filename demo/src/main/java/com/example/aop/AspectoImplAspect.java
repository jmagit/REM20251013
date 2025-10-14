package com.example.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AspectoImplAspect {
	@Pointcut("execution(public * com.example.ioc..*.*(..))")
	public void miPuntoDeCorte() {}

//	@Before("miPuntoDeCorte()")
//	public void consejoAntesDelMetodo(JoinPoint jp) {
//		System.err.println(">>> Soy un consejo antesDelMetodo " + jp.getSignature());
//	}
//	
//	@After("miPuntoDeCorte()")
//	public void consejoDespuesDelMetodo(JoinPoint jp) {
//		System.err.println(">>> Soy un consejo despuesDelMetodo " + jp.getSignature());
//	}

//	@AfterReturning(pointcut="execution(int com.example.ioc..*.get*(..))",	returning="retVal")
//	public void consejoDespuesDeGetPropiedad(JoinPoint jp, int retVal) {
//		System.err.println(">>> La funcion '" + jp.getSignature() + "' ha devuelto " + retVal);
//	}
	
//	@Around( "execution(* get*()")
//	public Object consejoQueEnvuelveGetters(ProceedingJoinPoint jp) throws Throwable {
//		System.err.println(">>> Soy el previo consejoQueEnvuelveGetters " + jp.getSignature());
//		int o = (int) jp.proceed();
//		System.err.println(">>> Soy el posterior consejoQueEnvuelveGetters " + jp.getSignature());
//		return o;
//	}

//	@Around("miPuntoDeCorte()")
//	public Object consejoQueEnvuelveAlMetodo(ProceedingJoinPoint jp) throws Throwable {
//		System.err.println(">>> Soy el previo queEnvuelveAlMetodo " + jp.getSignature());
//		var o = jp.proceed();
//		System.err.println(">>> Soy el posterior queEnvuelveAlMetodo " + jp.getSignature());
//		if(o != null ) {
//			System.err.println(">>> Valor devuelto " + o);
//			if(o instanceof Number v)
//				return v.doubleValue() * 2;
//		}
//		return o;
//	}

}
