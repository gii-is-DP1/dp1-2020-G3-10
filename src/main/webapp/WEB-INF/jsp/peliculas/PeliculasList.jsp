<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="peliculas">

	<h3>
		<c:out value="${mensaje}" />
	</h3>

	<h2>PELÍCULAS</h2>

	</br>
	</br>

	<table class="table table-stripped" id="peliculasTable">

		<tbody>
			<c:forEach items="${peliculas}" var="pel">


				<div class="col-sm-6 col-md-4">
					<div class="thumbnail">
						<spring:url value="/peliculas/{peliculaId}" var="peliculaUrl">
							<spring:param name="peliculaId" value="${pel.id}" />
						</spring:url>
						<a href="${fn:escapeXml(peliculaUrl)}"><img
							src=<c:out value="${pel.imagen}"/> width="70%" height="70%"></a>

						<spring:url value="/peliculas/{peliculaId}" var="peliculaUrl">
							<spring:param name="peliculaId" value="${pel.id}" />
						</spring:url>
						<center>
							<a href="${fn:escapeXml(peliculaUrl)}"><c:out
									value="${pel.nombre}" /></a>
						</center>

					</div>
				</div>

			</c:forEach>
		</tbody>
	</table>


</petclinic:layout>