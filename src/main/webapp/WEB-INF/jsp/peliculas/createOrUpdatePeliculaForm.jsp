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
        <form:form modelAttribute="pel"
                   class="form-horizontal">
            
            <div class="form-group has-feedback">
                
                <petclinic:inputField label="nombre" name="Nombre"/>
                <petclinic:inputField label="precio" name="Precio"/>
                <petclinic:inputField label="agno" name="Año"/>
                <petclinic:inputField label="director" name="Director"/>
                <petclinic:inputField label="duracion" name="Duracion"/>
                <petclinic:inputField label="edicion" name="Edicion"/>
                <petclinic:inputField label="formato" name="Formato"/>
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
</petclinic:layout>