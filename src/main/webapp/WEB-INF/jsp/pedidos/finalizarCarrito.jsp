<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<petclinic:layout pageName="finalizarPedido">
    <h1>DATOS DE FACTURACION</h1>
    <c:out value="${mensaje}" />
    <table class="table table-striped">
        <thead>
            <tr>
                <th style="width: 150px;">Nombre</th>
                <th style="width: 200px;">Apellidos</th>
                <th style="width: 120px">Email</th>
                <th style="width: 120px">Teléfono</th>


            </tr>
        </thead>
        <tbody>
                    <td><c:out value="${cliente.nombre}" /></td>
                    <td><c:out value="${cliente.apellidos}" /></td>
                    <td><c:out value="${cliente.email}" /></td>
                    <td><c:out value="${cliente.telefono}" /></td>




        </tbody>
    </table>
    <form:form modelAttribute="pedido" class="form-horizontal" action="/pedidos/pagar">
                <div class="form-group has-feedback">
                    <petclinic:inputField label="Dirección de Envío" name="direccionEnvio" />
                    <input type="hidden" name="id" value="${pedido.id}" />
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">

                        <button class="btn btn-default" type="submit">Pagar</button>

                    </div>
                </div>
            </form:form>


</petclinic:layout>
</body>
</html>