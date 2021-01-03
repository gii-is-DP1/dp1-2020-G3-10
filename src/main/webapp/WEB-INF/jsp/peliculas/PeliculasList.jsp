<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="peliculas">

	<spring:url value="/peliculas/new" var="addPeliculaUrl">
	</spring:url>
	<a href="${fn:escapeXml(addPeliculaUrl)}" class="btn btn-default">Nueva Película</a>
		
		</br>
		</br>
		</br>
		</br>
		
	<h2>PELÍCULAS</h2>

	<table id="peliculasTable">

		<tbody>
			<c:forEach items="${peliculas}" var="pel">


				<div class="col-sm-6 col-md-4">
					<div class="thumbnail">
						<img src=<c:out value="${pel.imagen}"/> width="250" height="350">

						<spring:url value="/peliculas/{peliculaId}" var="peliculaUrl">
							<spring:param name="peliculaId" value="${pel.id}" />
						</spring:url>
						<a href="${fn:escapeXml(peliculaUrl)}"><c:out
								value="${pel.nombre}" /></a>

					</div>
				</div>

			</c:forEach>
		</tbody>
	</table>
	
</petclinic:layout>