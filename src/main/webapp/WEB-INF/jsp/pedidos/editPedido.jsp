<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="pedido">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#fecha").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2>Pedido</h2>
        <form:form modelAttribute="pedido" class="form-horizontal" action="/pedidos/save">
            <div class="form-group has-feedback">
                <petclinic:inputField label="fecha" name="fecha"/>
                <petclinic:inputField label="precioTotal" name="precioTotal"/>
                <petclinic:inputField label="direccionEnvio" name="direccionEnvio"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="petId" value="${pedido.id}"/>
                    <button class="btn btn-default" type="submit">Add Pedido</button>
                </div>
            </div>
        </form:form>

        
    </jsp:body>

</petclinic:layout>
