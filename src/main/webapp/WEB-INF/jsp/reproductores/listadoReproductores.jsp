<%@ page session="false" trimDirectiveWhitespaces="true" %> <%@ taglib
prefix="spring" uri="http://www.springframework.org/tags" %> <%@ taglib
prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib
prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> <%@ taglib
prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="reproductores">
	<h2>Reproductores</h2>

	<table id="plataformasTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Nombre</th>
				<th style="width: 200px;">Descripcion</th>
				<th style="width: 200px;">Actions</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${reproductores}" var="reproductor">
				<tr>
					<td><c:out value="${reproductor.nombre}" /></a></td>
					<td><c:out value="${reproductor.descripcion}" /></td>
					<td><spring:url value="/reproductores/delete/{reproductorId}"
							var="deleteReproductorURL">
							<spring:param name="reproductorId" value="${reproductor.id}" />
						</spring:url> <a href="${fn:escapeXml(deleteReproductorURL)}">Delete</a></td>

					<td><spring:url value="/reproductores/add/{reproductorId}"
							var="addReproductorURL">
							<spring:param name="reproductorId" value="${reproductor.id}" />

						</spring:url> <a href="${fn:escapeXml(addReproductorURL)}">Añadir</a></td>


				</tr>
			</c:forEach>

		</tbody>

	</table>
		
	<spring:url value= "/clientes/{clienteId}/addReproductores"
		var="clienteAddReproductor">
		<spring:param name="clienteId" value="${cliente.id}" />
	</spring:url>
	
	<a href="${fn:escapeXml(clienteAddReproductor)}" class="btn btn-default">Añadir Reproductor</a>
	
	<!-- TODO esto tengo que cambiarlo para que solo se vea si eres admin
	<a href="/reproductores/new" class="btn btn-default">Nuevo Reproductor</a>
     -->
</petclinic:layout>
