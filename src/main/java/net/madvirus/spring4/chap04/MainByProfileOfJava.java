package net.madvirus.spring4.chap04;

import net.madvirus.spring4.chap04.config.ApplicationConfig;
import net.madvirus.spring4.chap04.config.ApplicationContextConfig;
import net.madvirus.spring4.chap04.config.DataSourceDevConfig;
import net.madvirus.spring4.chap04.config.DataSourceProdConfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainByProfileOfJava {

	public static void main(String[] args) {
		useConfigurationProfile();
		useNestedConfigurationProfile();
	}

	private static void useConfigurationProfile() {
		System.out.println("===== @Configuration profile을 이용한 설정 ==== ");
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.getEnvironment().setActiveProfiles("prod");
		// Environment : 설정 파일이나 클래스 수정 없이 설정 정보의 일부를 변경할 수 있다.
		// 시스템 프로퍼티에서 값을 가져옴!
		// 스프링은 환경변수와 시스템 프로퍼티만 Environment의 프로퍼티로 사용한다. 
		// 따라서 프로퍼티 파일을 추가해주고 싶다면 그에 맞는 PropertySource를 추가해야 한다.
		context.register(ApplicationConfig.class, DataSourceDevConfig.class, DataSourceProdConfig.class);
		context.refresh();

		ChargeCalculator cal = context.getBean(ChargeCalculator.class);
		cal.calculate();
		context.close();
	}

	private static void useNestedConfigurationProfile() {
		System.out.println("===== 중첩 @Configuation을 이용한 profile 설정 ==== ");
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//		context.getEnvironment().setActiveProfiles("prod");
		context.register(ApplicationContextConfig.class);
		context.refresh();

		ChargeCalculator cal = context.getBean(ChargeCalculator.class);
		cal.calculate();
		context.close();
	}
}
