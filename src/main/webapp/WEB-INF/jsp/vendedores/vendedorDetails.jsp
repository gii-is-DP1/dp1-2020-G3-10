<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="vendedor">

    <h2>Perfil Vendedor</h2>
    


    <table class="table table-striped">
        <tr>
            <th>Nombre y Apellidos</th>
            <td>
              <c:out value="${vendedor.nombre} ${vendedor.apellidos}"/>
            </td>
        </tr>
        <tr>
            <th>Vacaciones</th>
            <td><c:out value="${vendedor.vacaciones}"/></td>
        </tr>
         <tr>
            <th>Valoracion</th>
            <td><c:out value="${vendedor.valoracion / vendedor.votos}"/></td>
        </tr>
         <tr>
            <th>Nombre Tienda</th>
            <td><c:out value="${vendedor.nombreTienda}"/></td>
        </tr>
        <tr>
            <th>Dirección</th>
            <td><c:out value="${vendedor.direccionTienda}"/></td>
        </tr>
        <tr>
            <th>DNI</th>
            <td><c:out value="${vendedor.dni}"/></td>
        </tr>
        <tr>
            <th>Email</th>
            <td><c:out value="${vendedor.email}"/></td>
        </tr>
        <tr>
            <th>Fecha de Nacimiento</th>
            <td><c:out value="${vendedor.fechaNacimiento}"/></td>
        </tr>
        <tr>
            <th>Teléfono</th>
            <td><c:out value="${vendedor.telefono}"/></td>
        </tr>
        
        
        
    </table>

    <spring:url value="{vendedorId}/edit" var="editUrl">
        <spring:param name="vendedorId" value="${vendedor.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Vendedor</a>
    
    <spring:url value="/vendedores/delete/{vendedorId}" var="deleteUrl">
        <spring:param name="vendedorId" value="${vendedor.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default" onclick="return confirm('¿Continuar?') ? true : false;">Eliminar Vendedor</a>


    <br/>
    <br/>
    <br/>

</petclinic:layout>
