<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="videojuegos">
	<h2>VIDEOJUEGOS</h2>



	<table id="videojuegosTable">

		<tbody>
			<c:forEach items="${videojuegos}" var="vid">


				<div class="col-sm-6 col-md-4">
					<div class="thumbnail">
						<img src=<c:out value="${vid.imagen}"/> width="250" height="350">

						<spring:url value="/videojuegos/{videojuegoId}" var="videojuegoUrl">
							<spring:param name="videojuegoId" value="${vid.id}" />
						</spring:url>
						<center><a href="${fn:escapeXml(videojuegoUrl)}"><c:out
								value="${vid.nombre}" /></a></center>

					</div>   
				</div>

			</c:forEach>
		</tbody>
	</table>

	 <spring:url value="/videojuegos/new" var="addVideojuegoUrl">
	</spring:url>
	<a href="${fn:escapeXml(addVideojuegoUrl)}" class="btn btn-default">Nuevo
		Videojuego</a>





</petclinic:layout>