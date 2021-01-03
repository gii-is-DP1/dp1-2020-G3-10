<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="cliente">

    <h2>Perfil Usuario</h2>


    <table class="table table-striped">
        <tr>
            <th>Nombre y Apellidos</th>
            <td><b><c:out value="${cliente.nombre} ${cliente.apellidos}"/></b></td>
        </tr>
        <tr>
            <th>Identificación</th>
            <td><b><c:out value="${cliente.dni}"/></b></td>
        </tr>
         <tr>
            <th>Correo Electrónico</th>
            <td><b><c:out value="${cliente.email}"/></b></td>
        </tr>
         <tr>
            <th>Fecha de Nacimiento</th>
            <td><b><c:out value="${cliente.fechaNacimiento}"/></b></td>
        </tr>
        <tr>
            <th>Dirección</th>
            <td><b><c:out value="${cliente.direccion} ${cliente.codigoPostal}"/></b></td>
        </tr>
         <tr>
            <th>Telefono</th>
            <td><b><c:out value="${cliente.telefono}"/></b></td>
        </tr>
        <tr>
            <th>Cartera</th>
            <td><b><c:out value="${cliente.cartera}"/> € </b></td>
        </tr>
        
    </table>

    <spring:url value="{clienteId}/edit" var="editUrl">
        <spring:param name="clienteId" value="${cliente.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Cliente</a>

    <spring:url value="{clienteId}/reproductores" var="listaReproductoresUrl">
        <spring:param name="clienteId" value="${cliente.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(listaReproductoresUrl)}" class="btn btn-default">Ver Reproductores</a>

    <br/>
    <br/>
    <br/>

</petclinic:layout>
