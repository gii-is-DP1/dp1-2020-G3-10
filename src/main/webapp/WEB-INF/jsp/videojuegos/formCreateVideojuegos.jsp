<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>

<petclinic:layout pageName="videojuegosAdd">
   
    <jsp:body>
        <h2>
            <c:if test="${videojuego['new']}">New </c:if> Videojuego
        </h2>
        <form:form modelAttribute="videojuego"
                   class="form-horizontal">
                  
            <div class="form-group has-feedback">
                <petclinic:inputField label="Nombre" name="Nombre"/>
                <petclinic:inputField label="Precio" name="Precio"/>
                <petclinic:inputField label="Año" name="Agno"/>
                <petclinic:inputField label="Descripcion" name="Descripcion"/>
                <petclinic:inputField label="Estudio" name="Estudio"/>
                <petclinic:inputField label="Imagen" name="Imagen"/>
                <petclinic:inputField label="Plataforma" name="Plataforma"/>
                
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${videojuego['new']}">
                            <button class="btn btn-default" type="submit">Add videojuego</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Actualizar videojuego</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
        <c:if test="${!videojuego['new']}">
        </c:if>
    </jsp:body>
</petclinic:layout></html>