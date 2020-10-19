package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WelcomeController {
	
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {	  
		  List<Person> persons = new ArrayList<Person>();
		  
		  Person person1 = new Person();
		  person1.setFirstName("Luis Miguel");
		  person1.setLastName(" Bellido");
		  persons.add(person1);
		  
		  Person person2 = new Person();
		  person2.setFirstName("Marta");
		  person2.setLastName(" Diaz");
		  persons.add(person2);
		  
		  Person person3 = new Person();
		  person3.setFirstName("Jaime");
		  person3.setLastName(" Lucas");
		  persons.add(person3);
		  
		  Person person4 = new Person();
		  person4.setFirstName("Ivan");
		  person4.setLastName(" Cardenas");
		  persons.add(person4);
		  
		  Person person5 = new Person();
		  person5.setFirstName("Guillermo");
		  person5.setLastName(" Pavon");
		  persons.add(person5);
		  
		  Person person6 = new Person();
		  person6.setFirstName("Antonio Javier");
		  person6.setLastName(" Moreno");
		  persons.add(person6);
		  
		  model.put("persons", persons);
		  model.put("title", "My project");
		  model.put("group", "G3-10");

	    return "welcome";
	  }
}
