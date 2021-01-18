<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="pedido">
	<h1>CESTA DE LA COMPRA</h1>
		<c:out value="${mensaje}"/>
	<table id="peliculasTable">

		<tbody>
			<c:forEach items="${productos}" var="producto">


				<div class="col-sm-6 col-md-4">
					<div class="thumbnail">
						

					</div>
				</div>

			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>