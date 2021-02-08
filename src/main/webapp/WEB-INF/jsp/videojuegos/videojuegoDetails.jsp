<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="descripcionVideojuego">



	<table class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Imagen</th>
				<th style="width: 150px;">Nombre</th>
				<th style="width: 150px;">Precio</th>
				<th style="width: 150px;">A�o</th>
				<th style="width: 150px;">Plataforma</th>
				<th style="width: 150px;">Descripci�n</th>
				<th style="width: 150px;">Estudio</th>
				<th style="width: 150px;">Fecha de lanzamiento</th>
				<th style="width: 150px;"></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><img src=<c:out value="${videojuego.imagen}"/> width="250px"
					height="350px"></td>
				<td><c:out value="${videojuego.nombre}" /></td>
				<td><c:out value="${videojuego.precio}" /></td>
				<td><c:out value="${videojuego.agno}" /></td>
				<td><c:out value="${videojuego.plataforma}" /></td>
				<td><c:out value="${videojuego.descripcion}" /></td>
				<td><c:out value="${videojuego.estudio}" /></td>
				<td><c:out value="${videojuego.fechaSalida}" /></td>
				<td>
				<sec:authorize access="hasAuthority('cliente')">
				<spring:url value="/pedidos/addCarrito/{productoId}/{tipo}"
						var="carritoUrl">
						<spring:param name="productoId" value="${videojuego.id}" />
						<spring:param name="tipo" value="${'VIDEOJUEGO'}" />
					</spring:url> <a href="${fn:escapeXml(carritoUrl)}" class="btn btn-default">Insertar
						al carrito</a>
						
					<spring:url value="/comentarios/{clienteId}/videojuego/{videojuegoId}/new" var="createUrl">
					<spring:param name="videojuegoId" value="${videojuego.id}" />
					<spring:param name="clienteId" value="${cliente.id}"/>
				</spring:url>
				<a href="${fn:escapeXml(createUrl)}" class="btn btn-default">A�adir comentario</a>
				</sec:authorize>
				</td>
			</tr>
		</tbody>
	</table>
	
	
	<table id="commentsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Titulo</th>
            <th style="width: 200px;">Texto</th>
            <th style="width: 120px">Autor</th>
  
        </tr>
        </thead>
        <tbody>
      		  <c:forEach items="${comentarios}" var="comentario">
            <tr>
                <td>
                    <c:out value="${comentario.titulo}"/>
                </td>
                <td>
                    <c:out value="${comentario.texto}"/>
                </td>
                <td>
                    <c:out value="${comentario.cliente.email}"/>
                </td>
               
            </tr>
        </c:forEach>
        </tbody>
    </table>

</petclinic:layout>