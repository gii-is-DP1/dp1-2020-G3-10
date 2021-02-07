<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Pedido Completado</title>
</head>
<body>
<petclinic:layout pageName="finalizarPedido">
    <h1>PEDIDO COMPLETADO</h1>
    <h2>Gracias por su compra</h2>
    </br>
    </br>
    <h2>Resumen del pedido:</h2>
    </br>
    <h3>Número de pedido: <c:out value="${pedido.id}"/></h3>
    <h3>Fecha: <c:out value="${pedido.fecha}"/></h3>
    <h3>Dirección de Envío: <c:out value="${pedido.direccionEnvio}"/></h3>
    </br>
    <c:forEach items="${pedido.peliculas}" var="pelicula">					
	<h3>Producto: <c:out value="${pelicula.nombre}" /> - Precio: <c:out value="${pelicula.precio}" /></h3>
	</c:forEach>
	<c:forEach items="${pedido.videojuegos}" var="videojuego">					
	<h3>Producto: <c:out value="${videojuego.nombre}" /> - Precio: <c:out value="${videojuego.precio}" /></h3>
	</c:forEach>
	<c:forEach items="${pedido.merchandasings}" var="merchandasing">					
	<h3>Producto: <c:out value="${merchandasing.nombre}" /> - Precio: <c:out value="${merchandasing.precio}" /></h3>
	</c:forEach>
    </br>
	<h3>Precio Total: <c:out value="${pedido.precioTotal}"/></h3>

</petclinic:layout>
</body>
</html>