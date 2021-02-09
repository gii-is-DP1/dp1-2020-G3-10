<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Detalles del Pedido</title>
</head>
<body>
<petclinic:layout pageName="detallesPedido">
    </br>
    <h3>Número de pedido: <c:out value="${pedido.id}"/></h3>
    <h3>Fecha: <c:out value="${pedido.fecha}"/></h3>
    <h3>Dirección de Envío: <c:out value="${pedido.direccionEnvio}"/></h3>
    </br>
   
	<table class="table table-stripped" style="text-align: center;"
				id="pedidosTable">
				<thead>
					<tr>
						<th style="width: 25%;"><center>Producto</center></th>
						<th style="width: 25%;"><center>Nombre</center></th>
						<th style="width: 25%;"><center>Cantidad</center></th>
						<th style="width: 25%;"><center>Precio</center></th>

					</tr>
				</thead>
				<tbody>

						<c:forEach items="${pedido.peliculas}" var="pelicula">
							<tr>

								<td><img src=<c:out value="${pelicula.imagen}"/>
									width="70%" height="70%"></td>
								<td><c:out value="${pelicula.nombre}" /></td>
								<td><c:out value="1" /></td>
								<td><c:out value="${pelicula.precio}" /></td>

							</tr>
						</c:forEach>
					
						<c:forEach items="${pedido.videojuegos}" var="videojuego">
							<tr>

								<td><img src=<c:out value="${videojuego.imagen}"/>
									width="70%" height="70%"></td>
								<td><c:out value="${videojuego.nombre}" /></td>
								<td><c:out value="1" /></td>
								<td><c:out value="${videojuego.precio}" /></td>

							</tr>
						</c:forEach>

						<c:forEach items="${pedido.merchandasings}" var="merchandasing">
							<tr>

								<td><img src=<c:out value="${merchandasing.imagen}"/>
									width="70%" height="70%"></td>
								<td><c:out value="${merchandasing.nombre}" /></td>
								<td><c:out value="1" /></td>
								<td><c:out value="${merchandasing.precio}" /></td>

							</tr>
						</c:forEach>
					

				</tbody>
			</table>
			
	<h3>Precio Total: <c:out value="${pedido.precioTotal}"/></h3>
	
	<sec:authorize access="hasAuthority('vendedor')">
	<c:if test="${pedido.estado == 'PENDIENTE'}">
		<spring:url value="/pedidos/enviado/{pedidoId}" var="pedidoEnviadoUrl">
			<spring:param name="pedidoId" value="${pedido.id}" />
		</spring:url> 
		<a href="${fn:escapeXml(pedidoEnviadoUrl)}" class="btn btn-default">Marcar como Enviado</a>
	</c:if>
	</sec:authorize>
	
	<sec:authorize access="hasAuthority('cliente')">
	<c:if test="${pedido.estado == 'ENVIADO'}">
		<spring:url value="/pedidos/entregado/{pedidoId}" var="pedidoEntregadoUrl">
			<spring:param name="pedidoId" value="${pedido.id}" />
		</spring:url> 
		<a href="${fn:escapeXml(pedidoEntregadoUrl)}" class="btn btn-default">Marcar como Entregado</a>
	</c:if>
	</sec:authorize>

	

</petclinic:layout>
</body>
</html>