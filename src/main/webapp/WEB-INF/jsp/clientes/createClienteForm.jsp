<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
    
<petclinic:layout pageName="clientes">

	<jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#fechaNacimiento").datepicker({dateFormat: 'yy-mm-dd'});
            });
        </script>
    </jsp:attribute>
    
    <jsp:body>
    
    <h2>
        <c:if test="${cliente['new']}">Nuevo </c:if> Cliente
    </h2>
    <form:form modelAttribute="cliente" class="form-horizontal" id="add-cliente-form" onsubmit="return confirm('¿Continuar?') ? true : false;">
        <div class="form-group has-feedback">
        	<input type="hidden" name="admin" value="0"/>
        	<input type="hidden" name="cartera" value="0.0"/>
            <petclinic:inputField label="DNI" name="dni"/>
            <petclinic:inputField label="Nombre" name="nombre"/>
            <petclinic:inputField label="Apellidos" name="apellidos"/>
            <petclinic:inputField label="Email" name="email"/>
            <petclinic:inputField label="Direccion" name="direccion"/>
            <petclinic:inputField label="Fecha de Nacimiento" name="fechaNacimiento"/>
            <petclinic:inputField label="Tarjeta de Credito" name="tarjetaCredito"/>
            <petclinic:inputField label="Número de Teléfono" name="telefono"/>
            <petclinic:inputField label="Ciudad" name="ciudad"/>
            <petclinic:inputField label="Código Postal" name="codigoPostal"/>
            <petclinic:inputField label="Usuario" name="user.username"/>
            <petclinic:inputField label="Contraseña" name="user.password"/>
        </div>
        <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                <input type="hidden" name="version" value="${cliente.version}"/>
                    <c:choose>
                        <c:when test="${cliente['new']}">
                            <button class="btn btn-default" type="submit">Crea mi perfil!</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Actualiza mis datos!</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
    </form:form>
    
    </jsp:body>
    
</petclinic:layout>

<!-- Es necesario añadir un required a fechaNacimiento ya que NotNull por algún motivo no funciona 
y si se deja en blanco no lo toma como un error de binding por lo que va a la página de error genérica-->
	
    <script>
            $("#fechaNacimiento").attr('required', ''); 
    </script>

   