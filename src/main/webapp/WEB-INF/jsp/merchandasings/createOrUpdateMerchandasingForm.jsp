<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>

<petclinic:layout pageName="merchandasingsAdd">
	<jsp:attribute name="customScript">
        <script>
			$(function() {
			$("#fechaSalida").datepicker({dateFormat : 'yy/mm/dd'});});
		</script>
    </jsp:attribute>
	<jsp:body>
        <h2>
            <c:if test="${merchandasing['new']}">Nuevo </c:if> merchandasing
        </h2>
        <form:form modelAttribute="merchandasing"
			class="form-horizontal">
            
            <div class="form-group has-feedback">
            
            <petclinic:inputField label="Nombre" name="nombre" />
        		<petclinic:inputField label="Imagen" name="imagen" />
                <petclinic:inputField label="fabricante" name="Fabricante" />
                <petclinic:inputField label="Precio" name="precio" />
                <petclinic:inputField label="Descripcion" name="descripcion" />
                <petclinic:inputField label="Fecha Salida" name="fechaSalida" />
					
				<div class="control-group">
                    <petclinic:selectField name="tipo" label="Tipo" names="${tipos}" size="3"/>
                </div>
             
        </div>
        
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${merchandasing['new']}">
                            <button class="btn btn-default"
								type="submit">Añadir Merchandising</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default"
								type="submit">Actualizar Merchandising</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
        <c:if test="${!merchandasing['new']}">
        </c:if>
    </jsp:body>
</petclinic:layout>