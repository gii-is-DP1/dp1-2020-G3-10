<%@ page session="false" trimDirectiveWhitespaces="true" %> <%@ taglib
prefix="spring" uri="http://www.springframework.org/tags" %> <%@ taglib
prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib
prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> <%@ taglib
prefix="petclinic" tagdir="/WEB-INF/tags" %> <%@ taglib prefix="sec"
uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="reproductores">
	<h2>Reproductores</h2>

	<table id="plataformasTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Nombre</th>
				<th style="width: 200px;">Descripcion</th>
				<th style="width: 400px;">Actions</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${reproductores}" var="reproductor">
				<tr>
					<td><c:out value="${reproductor.nombre}" /></a></td>
					<td><c:out value="${reproductor.descripcion}" /></td>

					<!-- Botón que añadirá un reproductor a la lista de reproductores del usuario / Visible por admin y cliente -->
					
						<td>
						
						<spring:url value="/reproductores/add/{reproductorId}"
							var="addReproductorURL">
							<spring:param name="reproductorId" value="${reproductor.id}" />
						</spring:url> 
						
						<c:choose>
						<c:when test="${MostrarBoton != True}">
						<a href="${fn:escapeXml(addReproductorURL)}">Lo Tengo!</a>
						</c:when>
						</c:choose>
						
					<!-- Botón que eliminará el reproductor de la base de datos / Solo visible por administradores -->
					
					<spring:url value="/reproductores/delete/{reproductorId}"
							var="deleteReproductorURL">
							<spring:param name="reproductorId" value="${reproductor.id}" />
					</spring:url> 
					
					<spring:url value="/reproductores/remove/{reproductorId}"
							var="removeReproductorURL">
							<spring:param name="reproductorId" value="${reproductor.id}" />
					</spring:url> 
						
						<sec:authorize access="hasAuthority('admin')">
						<a href="${fn:escapeXml(deleteReproductorURL)}">Eliminar</a></td>
						</sec:authorize>
						
						<sec:authorize access="hasAuthority('cliente')">
						<c:choose>
						<c:when test="${MostrarBoton == True}">
						<a href="${fn:escapeXml(removeReproductorURL)}">Ya no lo tengo !</a>
						</c:when>
						</c:choose>
						</sec:authorize>

					</td>


				</tr>
			</c:forEach>

		</tbody>

	</table>


	<!-- Botón que llevará a un cliente a la lista de los reproductores que puede añadir a su propia lista-->
	<!-- Determinamos una lógica de botones para avanzar e ir hacia atrás entre los menús -->
	<sec:authorize access="hasAuthority('cliente')">
		<spring:url value="/clientes/{clienteId}/addReproductores"
			var="clienteAddReproductor">
			<spring:param name="clienteId" value="${cliente.id}" />
		</spring:url>

		<c:choose>
			<c:when test="${MostrarBoton == True}">
				<a href="${fn:escapeXml(clienteAddReproductor)}"
					class="btn btn-default">Añadir Reproductor</a>
			</c:when>
			<c:otherwise>
				<spring:url value="/clientes/{clienteId}/reproductores"
					var="listaReproductoresUrl">
					<spring:param name="clienteId" value="${cliente.id}" />
				</spring:url>
				<a href="${fn:escapeXml(listaReproductoresUrl)}"
					class="btn btn-default">Volver</a>
			</c:otherwise>
		</c:choose>
	</sec:authorize>


	<!-- Botón que llevará a crear un nuevo reproductor / Solo visible si eres admin -->
	<sec:authorize access="hasAuthority('admin')">
		<a href="/reproductores/new" class="btn btn-default">Nuevo
			Reproductor</a>
	</sec:authorize>

</petclinic:layout>
