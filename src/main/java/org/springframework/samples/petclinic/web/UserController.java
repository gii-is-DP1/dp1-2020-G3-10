/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

	private static final String VIEWS_ELEGIR_USUARIO = "users/elegirTipoUserForm";

	@Autowired
	public UserController() {
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value="/users/select")
    private String selectUser() {
        
		//Si no hago uso del controlador para redirigir a la vista no funciona, no puedo pasar el "/users/selectUser" desde el botón porque casca un 404
		
		return "/users/selectUser";
    }

	@PostMapping(value = "/users/new")
	public String processCreationForm(String tipoUsuario, BindingResult result) {
		System.out.println("LOG: Llega al save user.");
		if (result.hasErrors()) {
			System.out.println("LOG: error en el save user." + result.getAllErrors());
			return VIEWS_ELEGIR_USUARIO;
		} else {
			// Redirigimos según necesitemos
			System.out.println("LOG:llega redirigir usuario.");

			if (tipoUsuario == "Cliente") {

				return "redirect: /clientes/new";
				
			} else if (tipoUsuario == "Vendedor") {

				return "redirect: /vendedores/new";
			}else {
				
				return "redirect: /error";
			}
			
		}
	}

}
