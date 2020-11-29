<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="plataformas">

    <h2>Plataformas</h2>
	<br>
	<h3>Indica de que plataformas dispones </h3>
    <table class="table table-striped">
    
    	<!--  Aqui quiero hacer un iterable que recorra todas las plataformas disponibles y si 
    	el cliente tiene alguna de esas plataformas que aparezca el circulito ya marcado, si quiere añadir alguna mas que lo marque, 
    	luego este formulario se procesará y actualizara los datos del usuario con las plataformas indicadas 
    	
    	 De seguro tendre que hacer un boton Enviar que envie los datos del formulario al controlador, este los procesara y llamara al servicio
        que de validarlo todo llamara al repositorio para que haga un update de los datos" --> 
        
        <form action="/action_page.php" method="POST">
           <table id="plataformasTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Nombre</th>
            <th style="width: 200px;">Descripcion</th>
            <th style="width: 200px;">Actions</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${plataformas}" var="plataforma">
            <tr>
                <td>
                    <c:out value="${plataforma.nombre}"/></a>
                </td>
                <td>
                    <c:out value="${plataforma.descripcion}"/>
                </td>
                 <td>
                	<input type="checkbox" name="checkPlataforma" value="${plat.id}">
  					<label for="checkPlataforma"> ¡Lo tengo!</label><br>
                </td>
            </tr>
        </c:forEach>
            </form>

    </table>

</petclinic:layout>
