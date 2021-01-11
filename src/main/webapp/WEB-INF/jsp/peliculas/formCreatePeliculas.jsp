<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>

<petclinic:layout pageName="peliculasAdd">
   
   
    <jsp:body>
        <h2>
            <c:if test="${pelicula['new']}">New </c:if> Pelicula
        </h2>
        <form:form modelAttribute="pelicula"
                   class="form-horizontal">
                  
            <div class="form-group has-feedback">
                <petclinic:inputField label="Nombre" name="Nombre"/>
                <petclinic:inputField label="Precio" name="Precio"/>
                <petclinic:inputField label="Año" name="Agno"/>
                <petclinic:inputField label="Director" name="Director"/>
                <petclinic:inputField label="Duración" name="Duracion"/>
                <petclinic:inputField label="Edición" name="Edicion"/>
                <petclinic:inputField label="Formato" name="Formato"/>
                <petclinic:inputField label="Imagen" name="Imagen"/>
                <petclinic:inputField label="Descripcion" name="Descripcion"/>
                <petclinic:inputField label="FechaSalida" name="FechaSalida"/>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${pelicula['new']}">
                            <button class="btn btn-default" type="submit">Add Pelicula</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Update Pet</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
        <c:if test="${!pelicula['new']}">
        </c:if>
    </jsp:body>
</petclinic:layout></html>