<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<petclinic:layout pageName="descripcionPelicula">



	<table class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Imagen</th>
				<th style="width: 150px;">Nombre</th>
				<th style="width: 150px;">Precio</th>
				<th style="width: 150px;">Año</th>
				<th style="width: 150px;">Director</th>
				<th style="width: 150px;">Duración</th>
				<th style="width: 150px;">Fecha de Estreno</th>
				<th style="width: 150px;">Edicion</th>
				<th style="width: 150px;">Formato</th>
				<th style="width: 150px;">Sinopsis</th>
				<th style="width: 150px;"></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><img src=<c:out value="${pelicula.imagen}"/> width="250px"
					height="350px"></td>


				<td><c:out value="${pelicula.nombre}" /></td>

				<td><c:out value="${pelicula.precio}" /></td>

				<td><c:out value="${pelicula.agno}" /></td>

				<td><c:out value="${pelicula.director}" /></td>

				<td><c:out value="${pelicula.duracion}" /></td>

				<td><c:out value="${pelicula.fechaSalida}" /></td>
				
				<td><c:out value="${pelicula.edicion}" /></td>

				<td><c:out value="${pelicula.formato}" /></td>


				<td><c:out value="${pelicula.descripcion}" /></td>
				<td>
					<sec:authorize access="hasAuthority('cliente')">
						<spring:url value="/pedidos/addCarrito/{productoId}/{tipo}"
							var="carritoUrl">
							<spring:param name="productoId" value="${pelicula.id}" />
							<spring:param name="tipo" value="${'PELICULA'}" />
						</spring:url> <a href="${fn:escapeXml(carritoUrl)}" class="btn btn-default">Insertar
							al carrito</a>
					</sec:authorize>
							
					<sec:authorize access="hasAuthority('cliente')">
					<spring:url value="/comentarios/{clienteId}/pelicula/{peliculaId}/new" var="createUrl">
					<spring:param name="peliculaId" value="${pelicula.id}" />
					<spring:param name="clienteId" value="${cliente.id}"/>
				</spring:url>
				<a href="${fn:escapeXml(createUrl)}" class="btn btn-default">Añadir comentario</a>
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