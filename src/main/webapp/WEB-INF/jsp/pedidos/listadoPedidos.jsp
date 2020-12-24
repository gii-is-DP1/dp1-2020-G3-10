<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="pedidos">
    <h2>Pedidos</h2>

    <table id="pedidosTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Estado</th>
            <th style="width: 200px;">precio Total</th>
            <th style="width: 120px">Fecha</th>
            <th style="width: 120px">direccionEnvio</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pedidos}" var="pedido">
            <tr>
                
                <td>
                    <c:out value="${pedido.estado}"/>
                </td>
                <td>
                    <c:out value="${pedido.precioTotal}"/>
                </td>
                <td>
                    <c:out value="${pedido.fecha}"/>  
                </td>
                <td>
                    <c:out value="${pedido.direccionEnvio}"/>  
                </td>
                
                
             <td>
                    <spring:url value="/pedidos/delete/{pedidoId}" var="pedidoUrl">
                        <spring:param name="pedidoId" value="${pedido.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(pedidoUrl)}">Delete</a>
                </td>   
                
                         
                
            </tr>
            
            
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>