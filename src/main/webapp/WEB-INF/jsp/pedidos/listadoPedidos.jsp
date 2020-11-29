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
            <th style="width: 200px;">Cliente</th>
            <th style="width: 120px">Vendedor</th>
            <th style="width: 120px">Ofertas-ID</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pedidos}" var="pedido">
            <tr>
                
                <td>
                    <c:out value="${pedido.estado}"/>
                </td>
                <td>
                    <c:out value="${pedido.cliente.nombre}"/>
                </td>
                <td>
                    <c:out value="${pedido.vendedor.id}"/>  
                </td>
                <td>
                    <c:forEach var="oferta" items="${pedido.ofertas}">
                        <c:out value="${oferta.id} "/>
                    </c:forEach>
                </td>
                
                
                
                         
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>