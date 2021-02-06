<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<petclinic:layout pageName="descripcionPelicula">



	<table class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Imagen</th>
				<th style="width: 150px;">Nombre</th>
				<th style="width: 150px;">Precio</th>
				<th style="width: 150px;">Año</th>
				<th style="width: 150px;">Director</th>
				<th style="width: 150px;">Duración</th>
				<th style="width: 150px;">Formato</th>
				<th style="width: 150px;">Sinopsis</th>
				<th style="width: 150px;"></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><img src=<c:out value="${pelicula.imagen}"/> width="110%"
					height="110%"></td>


				<td><c:out value="${pelicula.nombre}" /></td>

				<td><c:out value="${pelicula.precio}" /></td>

				<td><c:out value="${pelicula.agno}" /></td>

				<td><c:out value="${pelicula.director}" /></td>

				<td><c:out value="${pelicula.duracion}" /></td>


				<td><c:out value="${pelicula.formato}" /></td>


				<td><c:out value="${pelicula.descripcion}" /></td>
				<td>
					<sec:authorize access="hasAuthority('cliente')">
						<spring:url value="/pedidos/addCarrito/{productoId}/{tipo}"
							var="carritoUrl">
							<spring:param name="productoId" value="${pelicula.id}" />
							<spring:param name="tipo" value="${'PELICULA'}" />
						</spring:url> <a href="${fn:escapeXml(carritoUrl)}" class="btn btn-default">Insertar
							al carrito</a>
					</sec:authorize>
				</td>
			</tr>
		</tbody>
	</table>




</petclinic:layout>