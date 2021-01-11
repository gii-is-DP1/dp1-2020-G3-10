<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
</head>
<body>


	<petclinic:layout pageName="cestaCompra">
		<h1>CESTA DE COMPRA</h1>
		<h3><c:out value="${usuario},${mensaje}"/></h3>


		<table class="table table-striped">
			<tr>
				<th>Producto</th>
				<th>Cantidad</th>
				<th>Precio</th>
			</tr>
			<tr>
				<td><c:out value="${producto.nombre}"/></td>
				<td><c:out value="${linea.cantidad}"/></td>
				<td><c:out value="${producto.precio}"/></td>
			</tr>




		</table>
	</petclinic:layout>
</body>
</html>