<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="descripcionPelicula">



	<table class="table table-striped">
		<td><img src=<c:out value="${pelicula.imagen}"/> width="250"
			height="350"></td>
		<tr>

			<td><c:out value="${pelicula.nombre}" /></td>
		</tr>
		<tr>
			<td><c:out value="${pelicula.precio}" /></td>
		</tr>
		<tr>
			<td><c:out value="${pelicula.agno}" /></td>
		</tr>
		<tr>
			<td><c:out value="${pelicula.director}" /></td>
		</tr>
		<tr>
			<td><c:out value="${pelicula.duracion}" /></td>
		</tr>
		<tr>
			<td><c:out value="${pelicula.formato}" /></td>
		</tr>
		<tr>
			<td><c:out value="${pelicula.descripcion}" /></td>
		</tr>
	</table>


	<spring:url value="/peliculas/delete/{peliculaId}" var="deleteUrl">
		<spring:param name="peliculaId" value="${pelicula.id}" />
	</spring:url>
	<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default">Borrar pelicula</a>

	<spring:url value="/peliculas/edit/{peliculaId}" var="editUrl">
		<spring:param name="peliculaId" value="${pelicula.id}" />
	</spring:url>
	<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar pelicula</a>
	
	<spring:url value="/pedidos/addCarrito/{productoId}/{tipo}" var="carritoUrl">
		<spring:param name="productoId" value="${pelicula.id}" />
		<spring:param name="tipo" value="${'PELICULA'}" />
	</spring:url>
	<a href="${fn:escapeXml(carritoUrl)}" class="btn btn-default">Insertar al carrito</a>


</petclinic:layout>