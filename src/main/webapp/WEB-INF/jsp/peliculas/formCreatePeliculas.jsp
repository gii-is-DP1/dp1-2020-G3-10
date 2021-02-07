<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>

<petclinic:layout pageName="peliculasAdd">
   	<jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#fechaSalida").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
   
    <jsp:body>
        <h2>
            <c:if test="${pelicula['new']}">Nueva</c:if> Pelicula
        </h2>
        <form:form modelAttribute="pelicula"
                   class="form-horizontal">
                  
            <div class="form-group has-feedback">
                <petclinic:inputField label="Nombre" name="nombre"/>
                <petclinic:inputField label="Precio" name="precio"/>
                <petclinic:inputField label="A�o" name="agno"/>
                <petclinic:inputField label="Director" name="director"/>
                <petclinic:inputField label="Duraci�n" name="duracion"/>
                <petclinic:inputField label="Edici�n" name="edicion"/>
                <div class="control-group" >
                    <petclinic:selectField name="formato" label="Formato" names="${formatos}" size="3"/>
                </div>
                <petclinic:inputField label="Imagen" name="imagen"/>
                <petclinic:inputField label="Descripcion" name="descripcion"/>
                <petclinic:inputField label="Fecha Salida" name="fechaSalida"  />
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${pelicula['new']}">
                            <button class="btn btn-default" type="submit">A�adir Pelicula</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Actualizar Pelicula</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
        <c:if test="${!pelicula['new']}">
        </c:if>
    </jsp:body>
</petclinic:layout></html>