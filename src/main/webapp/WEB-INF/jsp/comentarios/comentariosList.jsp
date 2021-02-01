<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="comentarios">
    <h2>Comentarios</h2>

    <table id="commentsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Titulo</th>
            <th style="width: 200px;">Texto</th>
            <th style="width: 120px">Autor</th>
            <th style="width: 250px">Producto</th>
  
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
                
                <td>
                	 <c:out value="${comentario.pelicula.nombre}"/>
                	 <c:out value="${comentario.videojuego.nombre}"/>
                	 <c:out value="${comentario.merchandasing.nombre}"/>
               
               <td>
                <spring:url value="/comentarios/{clienteId}/comentario/{comentarioId}/delete" var="deleteUrl">
					<spring:param name="comentarioId" value="${comentario.id}" />
					<spring:param name="clienteId" value="${comentario.cliente.id}"/>
				</spring:url>
				<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default">Borrar Comentario</a>
				</td>
				
				<td>
				<spring:url value="/comentarios/{clienteId}/comentario/{comentarioId}/edit" var="editUrl">
					<spring:param name="comentarioId" value="${comentario.id}" />
					<spring:param name="clienteId" value="${comentario.cliente.id}"/>
				</spring:url>
				<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar comentario</a>
                </td>
      
<!--
                <td> 
                    <c:out value="${owner.user.username}"/> 
                </td>
                <td> 
                   <c:out value="${owner.user.password}"/> 
                </td> 
-->
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>