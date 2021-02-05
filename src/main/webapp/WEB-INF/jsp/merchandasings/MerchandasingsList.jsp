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

<petclinic:layout pageName="merchandasings">
	
	<h2>MERCHANDASINGS</h2>
		
		</br>
		</br>

	<table class="table table-stripped" id="merchandasingTable">

		<tbody>
			<c:forEach items="${merchandasings}" var="merchandasing">


				<div class="col-sm-6 col-md-4">
					<div class="thumbnail">
						<spring:url value="/merchandasings/{merchandasingId}" var="merchandasingUrl">
							<spring:param name="merchandasingId" value="${merchandasing.id}" />
						</spring:url>
						<a href="${fn:escapeXml(merchandasingUrl)}"><img src=<c:out value="${merchandasing.imagen}"/> width="250" height="350"></a>

						<spring:url value="/merchandasings/{merchandasingId}" var="merchandasingUrl">
							<spring:param name="merchandasingId" value="${merchandasing.id}" />
						</spring:url>
						<center> <a href="${fn:escapeXml(merchandasingUrl)}"><c:out value="${merchandasing.nombre}"/></a></center>

					</div>
				</div>

			</c:forEach>
		</tbody>
	</table>




	<sec:authorize access="hasAuthority('vendedor')">
		<spring:url value="/vendedor/merchandasings/new"
			var="createMerchandasingUrl"></spring:url>
		<a class="btn btn-default" href="${fn:escapeXml(createMerchandasingUrl)}">Crear nuevo</a>
	</sec:authorize>
	
</petclinic:layout>
