<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="vendedores">
    <h2>Vendedores</h2>

    <table id="pedidosTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Nombre</th>
            <th style="width: 200px;">apellido</th>
            <th style="width: 120px">vacaciones</th>
            <th style="width: 120px">valoracion</th>
            <th style="width: 120px">telefono</th>
            <th style="width: 120px">Nombre Tienda</th>
            <th style="width: 120px">Direccion tienda</th>
            <th style="width: 120px">Usuario</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${vendedores}" var="vendedor">
            <tr>
                
                <td>
                    <c:out value="${vendedor.firstName}"/>
                </td>
                <td>
                    <c:out value="${vendedor.lastName}"/>
                </td>
                <td>
                    <c:out value="${vendedor.vacaciones}"/>  
                </td>
                <td>
                    <c:out value="${vendedor.valoracion}"/>  
                </td>
                <td>
                    <c:out value="${vendedor.telefono}"/>  
                </td>
                <td>
                    <c:out value="${vendedor.nombreTienda}"/>  
                </td>
                <td>
                    <c:out value="${vendedor.direccionTienda}"/>  
                </td>
                <td>
                    <c:out value="${vendedor.user.username}"/>  
                </td>
                
                
                <td>
                    <spring:url value="/vendedores/delete/{vendedorId}" var="vendedorUrl">
                        <spring:param name="vendedorId" value="${vendedor.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(vendedorUrl)}">Delete</a>
                </td>
                
                <td>
                    <spring:url value="/vendedores/{vendedorId}" var="vendedorUrl">
                        <spring:param name="vendedorId" value="${vendedor.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(vendedorUrl)}">Detalles</a>
                </td>
                
                      
                
                         
                
            </tr>
            
            
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>