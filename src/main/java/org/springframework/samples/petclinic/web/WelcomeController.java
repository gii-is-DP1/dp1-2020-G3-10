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
		  
		  Person person = new Person();
		  person.setFirstName("Luis Miguel");
		  person.setLastName("Bellido");
		  persons.add(person);
		  
		  Person person2 = new Person();
		  person.setFirstName("Marta");
		  person.setLastName("Diaz");
		  persons.add(person2);
		  
		  Person person3 = new Person();
		  person.setFirstName("Jaime");
		  person.setLastName("Lucas");
		  persons.add(person3);
		  
		  Person person4 = new Person();
		  person.setFirstName("Ivan");
		  person.setLastName("Cardenas");
		  persons.add(person4);
		  
		  Person person5 = new Person();
		  person.setFirstName("Guillermo");
		  person.setLastName("Pavon");
		  persons.add(person5);
		  
		  Person person6 = new Person();
		  person.setFirstName("Antonio Javier");
		  person.setLastName("Moreno");
		  persons.add(person6);
		  
		  model.put("persons", persons);
		  model.put("title", "My project");
		  model.put("group", "G3-10");

	    return "welcome";
	  }
}
