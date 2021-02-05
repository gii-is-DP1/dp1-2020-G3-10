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
			<h3>
				<c:out value="${mensaje}" />
			</h3>

			<spring:url value="/peliculas/new" var="addPeliculaUrl"></spring:url>
			<a href="${fn:escapeXml(addPeliculaUrl)}" class="btn btn-default">Nueva
				Película</a>
			<spring:url value="/videojuegos/new" var="addVideojuegoUrl"></spring:url>
			<a href="${fn:escapeXml(addVideojuegoUrl)}" class="btn btn-default">Nuevo
				Videojuego</a>


			<c:forEach items="${peliculas}" var="pelicula">
				<tr>
					<td><spring:url value="/peliculas/{peliculaId}"
							var="peliculaUrl">
							<spring:param name="peliculaId" value="${pelicula.id}" />
						</spring:url> <a href="${fn:escapeXml(peliculaUrl)}"><img
							src=<c:out value="${pelicula.imagen}"/> width="250" height="350"></a></td>
					<td><c:out value="${pelicula.nombre}" /></td>
					<td><c:out value="${pelicula.descripcion}" /></td>
					<td><c:out value="${pelicula.precio}" /></td>
					<td><spring:url value="/peliculas/delete/{peliculaId}"
							var="peliculaUrl">
							<spring:param name="peliculaId" value="${pelicula.id}" />
						</spring:url> <a href="${fn:escapeXml(peliculaUrl)}" class="btn btn-default">Eliminar
							Pelicula</a> <spring:url value="/peliculas/edit/{peliculaId}"
							var="editUrl">
							<spring:param name="peliculaId" value="${pelicula.id}" />
						</spring:url> <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar
							pelicula</a></td>
				</tr>
			</c:forEach>
			<c:forEach items="${videojuegos}" var="videojuego">
				<tr>
					<td><spring:url value="/videojuegos/{videojuegoId}"
							var="videojuegoUrl">
							<spring:param name="videojuegoId" value="${videojuego.id}" />
						</spring:url> <a href="${fn:escapeXml(videojuegoUrl)}"><img
							src=<c:out value="${videojuego.imagen}"/> width="250"
							height="350"></a></td>
					<td><c:out value="${videojuego.nombre}" /></td>
					<td><c:out value="${videojuego.descripcion}" /></td>
					<td><c:out value="${videojuego.precio}" /></td>
					<td><spring:url value="/videojuegos/delete/{videojuegoId}"
							var="deleteUrl">
							<spring:param name="videojuegoId" value="${videojuego.id}" />
						</spring:url> <a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default">Borrar
							videojuego</a> <spring:url value="/videojuegos/edit/{videojuegoId}"
							var="editUrl">
							<spring:param name="videojuegoId" value="${videojuego.id}" />
						</spring:url> <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar
							videojuego</a></td>
				</tr>
			</c:forEach>
			<c:forEach items="${merch}" var="merch">
				<tr>
					<td><img src=<c:out value="${merch.imagen}"/> width="250"
						height="350"></td>
					<td><c:out value="${merch.nombre}" /></td>
					<td><c:out value="${merch.descripcion}" /></td>
					<td><c:out value="${merch.precio}" /></td>
					<td><spring:url
							value="/vendedor/merchandasings/{merchandasingId}/delete"
							var="merchadasingUrl">
							<spring:param name="merchandasingId" value="${merch.id}" />
						</spring:url> <a href="${fn:escapeXml(merchadasingUrl)}"
						class="btn btn-default">Eliminar Merchandasing</a>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</petclinic:layout>