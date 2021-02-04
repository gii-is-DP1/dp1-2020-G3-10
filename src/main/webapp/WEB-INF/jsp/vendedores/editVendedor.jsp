<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="vendedor">
    <jsp:attribute name="customScript">
        
    </jsp:attribute>
    <jsp:body>
        <h2>Vendedor</h2>
        <form:form modelAttribute="vendedor" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="nombre" name="nombre"/>
                <petclinic:inputField label="apellidos" name="apellidos"/>
                <petclinic:inputField label="Valoracion" name="valoracion"/>
                <petclinic:inputField label="Vacaciones" name="vacaciones"/>
                <petclinic:inputField label="nombre tienda" name="nombreTienda"/>
                <petclinic:inputField label="direccion tienda" name="direccionTienda"/>
                <petclinic:inputField label="telefono" name="telefono"/>
                <petclinic:inputField label="dni" name="dni"/>
                <petclinic:inputField label="email" name="email"/>
                <petclinic:inputField label="fechaNacimiento" name="fechaNacimiento"/>             
                <petclinic:inputField label="Username" name="user.username"/>
                <petclinic:inputField label="Password" name="user.password"/>
                
                
                
            </div>
            
            
            <div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
				
				
				 <button class="btn btn-default" type="submit">Actualizar Cliente</button>
				
							
               
				</div>
			</div>

            <div class="form-group">
            
            
            
               
            </div>
        </form:form>

        
    </jsp:body>

</petclinic:layout>
