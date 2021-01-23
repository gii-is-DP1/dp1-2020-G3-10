<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="productosVendedor">


	<h2>Productos</h2>

	<table id="productos" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Imagen</th>
				<th style="width: 150px;">Nombre</th>
				<th style="width: 150px;">Descripción</th>
				<th style="width: 150px;">Precio</th>
				<th style="width: 150px;"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${peliculas}" var="pelicula">
				<tr>

					<td><img src=<c:out value="${pelicula.imagen}"/> width="250"
						height="350"></td>
					<td><c:out value="${pelicula.nombre}" /></td>
					<td><c:out value="${pelicula.descripcion}" /></td>
					<td><c:out value="${pelicula.precio}" /></td>
					<td><spring:url value="/peliculas/delete/{peliculaId}"
							var="peliculaUrl">
							<spring:param name="peliculaId" value="${pelicula.id}" />
						</spring:url> <a href="${fn:escapeXml(peliculaUrl)}" class="btn btn-default">Eliminar Pelicula</a>


				</tr>
			</c:forEach>
		</tbody>
	</table>

</petclinic:layout>