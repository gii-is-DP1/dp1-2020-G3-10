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
		<thead>
			<tr>
				<th style="width: 150px;">Imagen</th>
				<th style="width: 150px;">Nombre</th>
				<th style="width: 150px;">Tipo</th>
				<th style="width: 150px;">Fabricante</th>
				<th style="width: 150px;">Descripcion</th>
				<th style="width: 150px;">Precio</th>
				<th style="width: 150px;">Fecha de publicación</th>
				<th style="width: 150px;"></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><img src=<c:out value="${merchandasing.imagen}"/>
					width="250px" height="350px"></td>
				<td><c:out value="${merchandasing.nombre}" /></td>
				<td><c:out value="${merchandasing.tipo}" /></td>
				<td><c:out value="${merchandasing.fabricante}" /></td>
				<td><c:out value="${merchandasing.descripcion}" /></td>
				<td><c:out value="${merchandasing.precio}" /></td>
				<td><c:out value="${merchandasing.fechaSalida}" /></td>
				<td><sec:authorize access="hasAuthority('cliente')">
						<spring:url value="/pedidos/addCarrito/{productoId}/{tipo}"
							var="carritoUrl">
							<spring:param name="productoId" value="${merchandasing.id}" />
							<spring:param name="tipo" value="${'MERCHANDASING'}" />
						</spring:url>
						<a href="${fn:escapeXml(carritoUrl)}" class="btn btn-default">Insertar
							al carrito</a>
							
						<spring:url value="/comentarios/{clienteId}/merchandasing/{merchandasingId}/new" var="createUrl">
					<spring:param name="merchandasingId" value="${merchandasing.id}" />
					<spring:param name="clienteId" value="${cliente.id}"/>
				</spring:url>
				<a href="${fn:escapeXml(createUrl)}" class="btn btn-default">Añadir comentario</a>
					</sec:authorize></td>
			</tr>
		</tbody>
	</table>
</petclinic:layout>