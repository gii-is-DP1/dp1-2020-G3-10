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
                <petclinic:inputField label="Nombre" name="nombre"/>
                <petclinic:inputField label="Apellidos" name="apellidos"/>
                <petclinic:inputField label="Vacaciones" name="vacaciones"/>
                <petclinic:inputField label="Nombre tienda" name="nombreTienda"/>
                <petclinic:inputField label="Direccion tienda" name="direccionTienda"/>
                <petclinic:inputField label="Teléfono" name="telefono"/>
                <petclinic:inputField label="Dni" name="dni"/>
                <petclinic:inputField label="Email" name="email"/>
                <petclinic:inputField label="Fecha de Nacimiento" name="fechaNacimiento"/>             
                <petclinic:inputField label="Usuario" name="user.username"/>
                <petclinic:inputField label="Contaseña" name="user.password"/>
                
                
                
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
