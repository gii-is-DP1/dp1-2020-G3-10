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
			<td><c:out value="${merchandasing.tipo}" /></td>
		</tr>
		<tr>
			<td><c:out value="${merchandasing.fabricante}" /></td>
		</tr>
		
	</table>

	<sec:authorize access="hasAuthority('vendedor')">
	<spring:url value="/vendedor/merchandasings/{merchandasingId}/delete" var="deleteUrl">
		<spring:param name="merchandasingId" value="${merchandasing.id}" />
	</spring:url>
	<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default">Eliminar</a>
	</sec:authorize>



</petclinic:layout>