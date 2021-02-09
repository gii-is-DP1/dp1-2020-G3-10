<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="reproductores">

    <h2>reproductores</h2>
	<br>
	<h3>Indica de que reproductores dispones </h3>
    <table class="table table-striped">
            
        <form action="/action_page.php" method="POST">
           <table id="reproductoresTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Nombre</th>
            <th style="width: 200px;">Descripcion</th>
            <th style="width: 200px;">Actions</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${reproductores}" var="reproductor">
            <tr>
                <td>
                    <c:out value="${reproductor.nombre}"/></a>
                </td>
                <td>
                    <c:out value="${reproductor.descripcion}"/>
                </td>
                 <td>
                	<input type="checkbox" name="checkReproductor" value="${plat.id}">
  					<label for="checkPlataforma"> ¡Lo tengo!</label><br>
                </td>
            </tr>
        </c:forEach>
            </form>

    </table>

</petclinic:layout>
