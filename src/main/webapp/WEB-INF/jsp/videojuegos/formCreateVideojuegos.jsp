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
            <c:if test="${videojuego['new']}">Nuevo </c:if> Videojuego
        </h2>
        <form:form modelAttribute="videojuego"
                   class="form-horizontal">
                  
            <div class="form-group has-feedback">
                <petclinic:inputField label="Nombre" name="nombre"/>
                <petclinic:inputField label="Precio" name="precio"/>
                <petclinic:inputField label="Año" name="agno"/>
                <petclinic:inputField label="Descripcion" name="descripcion"/>
                <petclinic:inputField label="Estudio" name="estudio"/>
                <petclinic:inputField label="Imagen" name="imagen"/>
                <div class="control-group">
                    <petclinic:selectField name="plataforma" label="Plataforma" names="${plataformas}" size="4"/>
                </div>
                
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${videojuego['new']}">
                            <button class="btn btn-default" type="submit">Añadir Videojuego</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Actualizar Videojuego</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
        <c:if test="${!videojuego['new']}">
        </c:if>
    </jsp:body>
</petclinic:layout></html>