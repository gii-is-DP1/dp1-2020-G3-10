<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="reproductores">
   
    <jsp:body>
        <h2>Reproductor</h2>

        <form:form modelAttribute="reproductor" class="form-horizontal" action="/reproductores/save">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Nombre" name="nombre"/>
                <petclinic:inputField label="Descripcion" name="descripcion"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="reproductorId" value="${reproductor.id}"/>
                    <button class="btn btn-default" type="submit">Save Reproductor</button>
					<a href="/reproductores" class="btn btn-default"> Atras</a>
                </div>
            </div>
        </form:form>

    </jsp:body>

</petclinic:layout>
