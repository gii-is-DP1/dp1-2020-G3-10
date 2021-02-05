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



<petclinic:layout pageName="descripcionMerchandasing">



	<table class="table table-striped">

		<tr>
			<td>Nombre: <c:out value="${merchandasing.nombre}" /></td>
		</tr>
		
		
		<td><img src=<c:out value="${merchandasing.imagen}"/> width="20%"
			height="20%"></td>
		
		
		<tr>
			<td>Tipo: <c:out value="${merchandasing.tipo}" /></td>
		</tr>
		
		<tr>
			<td>Fabricante: <c:out value="${merchandasing.fabricante}" /></td>
		</tr>
		
		<tr>
			<td>Descripci&oacuten: <c:out value="${merchandasing.descripcion}" /></td>
		</tr>

		<tr>
			<td>Fecha de salida: <c:out value="${merchandasing.fechaSalida}" /></td>
		</tr>

		<tr>
			<td>Precio: <c:out value="${merchandasing.precio}" /></td>
		</tr>

		

	</table>

	<sec:authorize access="hasAuthority('vendedor')">
		<spring:url value="/vendedor/merchandasings/{merchandasingId}/delete"
			var="deleteUrl">
			<spring:param name="merchandasingId" value="${merchandasing.id}" />
		</spring:url>
		<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default">Eliminar</a>
	</sec:authorize>
	
	<sec:authorize access="hasAuthority('cliente')">
						<spring:url value="/pedidos/addCarrito/{productoId}/{tipo}"
							var="carritoUrl">
							<spring:param name="productoId" value="${merchandasing.id}" />
							<spring:param name="tipo" value="${'MERCHANDASING'}" />
						</spring:url> <a href="${fn:escapeXml(carritoUrl)}" class="btn btn-default">Insertar
							al carrito</a>
					</sec:authorize>



</petclinic:layout>