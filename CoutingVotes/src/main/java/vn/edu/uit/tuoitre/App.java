package vn.edu.uit.tuoitre;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import vn.edu.uit.tuoitre.BusinessLogicLayer.BUS.ParameterBUS;
import vn.edu.uit.tuoitre.BusinessLogicLayer.DTO.ParameterDTO;

public class App {
	
	public static void main(String args[]){
		//ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
		ApplicationContext context = new ClassPathXmlApplicationContext("app-context.xml");
	
		ParameterBUS bus = context.getBean(ParameterBUS.class);
		
		ParameterDTO param = new ParameterDTO();
		param.setName("Test");
		param.setValue("123");
		
		bus.save(param);
		
	}
	
}
