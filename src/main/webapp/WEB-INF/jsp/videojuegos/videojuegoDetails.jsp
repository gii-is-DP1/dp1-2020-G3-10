<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="descripcionVideojuego">



	<table class="table table-striped">
		<td><img src=<c:out value="${videojuego.imagen}"/> width="250"
			height="350"></td>
		<tr>

			<td><c:out value="${videojuego.nombre}" /></td>
		</tr>
		<tr>
			<td><c:out value="${videojuego.precio}" /></td>
		</tr>
		<tr>
			<td><c:out value="${videojuego.agno}" /></td>
		</tr>
		<tr>
			<td><c:out value="${videojuego.plataforma}" /></td>
		</tr>
		<tr>
			<td><c:out value="${videojuego.descripcion}" /></td>
		</tr>
	</table>


	<spring:url value="/videojuegos/delete/{videojuegoId}" var="deleteUrl">
		<spring:param name="videojuegoId" value="${videojuego.id}" />
	</spring:url>
	<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default">Borrar videojuego</a>

	<spring:url value="/videojuegos/edit/{videojuegoId}" var="editUrl">
		<spring:param name="videojuegoId" value="${videojuego.id}" />
	</spring:url>
	<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar videojuego</a>
	



</petclinic:layout>