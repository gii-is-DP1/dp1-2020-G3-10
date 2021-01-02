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
							var="reproductorURL">
							<spring:param name="reproductorId" value="${reproductor.id}" />
						</spring:url> <a href="${fn:escapeXml(reproductorURL)}">Delete</a></td>

				</tr>
			</c:forEach>

		</tbody>

	</table>
	<a href="/reproductores/new" class="btn btn-default">Nuevo Reproductor</a>

</petclinic:layout>
