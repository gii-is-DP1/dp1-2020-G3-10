<%@ page session="false" trimDirectiveWhitespaces="true" %> <%@ taglib
prefix="spring" uri="http://www.springframework.org/tags" %> <%@ taglib
prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib
prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> <%@ taglib
prefix="form" uri="http://www.springframework.org/tags/form" %> <%@
taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="cliente">

		<h2>
			<c:if test="${cliente['new']}">Nuevo </c:if> Cliente
			
		</h2>
		<form:form modelAttribute="cliente" class="form-horizontal" id="add-cliente-form">
			<div class="form-group has-feedback">

				<petclinic:inputField label="DNI" name="dni" />
				<petclinic:inputField label="Nombre" name="nombre" />
				<petclinic:inputField label="Apellidos" name="apellidos" />
				<petclinic:inputField label="Email" name="email" />
				<petclinic:inputField label="Direccion" name="direccion" />
				<petclinic:inputField label="Fecha de Nacimiento" name="f_nacimiento" />
				<petclinic:inputField label="Tarjeta de Credito" name="tarjeta_credito" />
				
				<input type="hidden" name="cartera" value="${cliente.cartera}" />
				<input type="hidden" name="admin" value="${cliente.admin}" />

			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<c:choose>
						<c:when test="${cliente['new']}">
							<button class="btn btn-default" type="submit">A�adir Cliente</button>
						</c:when>
						<c:otherwise>
							<button class="btn btn-default" type="submit">Actualizar Cliente</button>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</form:form>

</petclinic:layout>