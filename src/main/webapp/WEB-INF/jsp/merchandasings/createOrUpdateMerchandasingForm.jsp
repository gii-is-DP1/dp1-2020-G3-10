<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>

<petclinic:layout pageName="merchandasingsAdd">

	<jsp:body>
        <h2>
            <c:if test="${merchandasing['new']}">Nuevo </c:if> merchandasing
        </h2>
        <form:form modelAttribute="merchandasing"
			class="form-horizontal">
            
            <div class="form-group has-feedback">
                
                
                
                <label class="col-sm-2 control-label">Tipo </label>

        <div class="col-sm-10">
            <select id="tipo" name="tipo" class="form-control" size="3"
						required>
            <option value="FIGURA">figura</option>
            <option value="ROPA">ropa</option>
            <option value="POSTER">poster</option>
            
					</select>
            
                <span
						class="glyphicon glyphicon-ok form-control-feedback"
						aria-hidden="true"></span>
            
            
        </div>
                <petclinic:inputField label="fabricante"
					name="Fabricante" />
       
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${merchandasing['new']}">
                            <button class="btn btn-default"
								type="submit">Añadir</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default"
								type="submit">Actualizar</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
        <c:if test="${!merchandasing['new']}">
        </c:if>
    </jsp:body>
</petclinic:layout>