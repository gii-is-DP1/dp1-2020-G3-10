<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="merchandasings">
	<h2>MERCHANDASINGS</h2>


	<table id="merchandasingsTable" class="table table-striped">
		<thead>
			<tr>
				<th>Fabricante</th>
				<th>Tipo</th>
				<th>Acciones</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${merchandasings}" var="merchandasing">
				<tr>
					<td><spring:url value="/merchandasings/{merchandasingId}"
							var="merchandasingUrl">
							<spring:param name="merchandasingId" value="${merchandasing.id}" />
						</spring:url> <a href="${fn:escapeXml(merchandasingUrl)}">${merchandasing.fabricante}</a>
					</td>

					<td><spring:url value="/merchandasings/{merchandasingId}"
							var="merchandasingUrl">
							<spring:param name="merchandasingId" value="${merchandasing.id}" />
						</spring:url> <a href="${fn:escapeXml(merchandasingUrl)}">${merchandasing.tipo}</a>
					</td>


					<td><sec:authorize access="hasAuthority('vendedor')">
							<spring:url
								value="/vendedor/merchandasings/{merchandasingId}/edit"
								var="merchandasingEditUrl">
								<spring:param name="merchandasingId" value="${merchandasing.id}" />
							</spring:url>
							<a href="${fn:escapeXml(merchandasingEditUrl)}">Editar</a>
						</sec:authorize> <sec:authorize access="hasAuthority('vendedor')">
							<spring:url
								value="/vendedor/merchandasings/{merchandasingId}/delete"
								var="merchandasingDeleteUrl">
								<spring:param name="merchandasingId" value="${merchandasing.id}" />
							</spring:url>
							<a href="${fn:escapeXml(merchandasingDeleteUrl)}">Eliminar</a>
						</sec:authorize></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>




	<sec:authorize access="hasAuthority('vendedor')">
		<spring:url value="/vendedor/merchandasings/new"
			var="createMerchandasingUrl"></spring:url>
		<a class="btn btn-default" href="${fn:escapeXml(createMerchandasingUrl)}">Crear nuevo</a>
	</sec:authorize>
</petclinic:layout>
