<%@ page session="false" trimDirectiveWhitespaces="true" %> <%@ taglib
prefix="spring" uri="http://www.springframework.org/tags" %> <%@ taglib
prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib
prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> <%@ taglib
prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="plataformas">
	<h2>Plataformas</h2>

	<table id="plataformasTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Nombre</th>
				<th style="width: 200px;">Descripcion</th>
				<th style="width: 200px;">Actions</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${plataformas}" var="plataforma">
				<tr>
					<td><c:out value="${plataforma.nombre}" /></a></td>
					<td><c:out value="${plataforma.descripcion}" /></td>
					<td><spring:url value="/plataformas/delete/{plataformaId}"
							var="plataformaURL">
							<spring:param name="plataformaId" value="${plataforma.id}" />
						</spring:url> <a href="${fn:escapeXml(plataformaURL)}">Delete</a></td>

				</tr>
			</c:forEach>

		</tbody>

	</table>
	<a href="/plataformas/new" class="btn btn-default">Nueva Plataforma</a>

</petclinic:layout>
