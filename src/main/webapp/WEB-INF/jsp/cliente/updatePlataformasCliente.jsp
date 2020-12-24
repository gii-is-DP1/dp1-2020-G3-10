<%@ page session="false" trimDirectiveWhitespaces="true" %> <%@ taglib
prefix="spring" uri="http://www.springframework.org/tags" %> <%@ taglib
prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib
prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> <%@ taglib
prefix="form" uri="http://www.springframework.org/tags/form" %> <%@
taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="cliente">

	<h2>Plataformas del Cliente</h2>
	<form:form modelAttribute="plataformasCliente" class="form-horizontal"
		id="add-cliente-form">
		<div class="form-group has-feedback">

			<table>

				<tr>
					<td>Escoge tus plataformas :</td>
					<td><form:checkboxes items="${plataformas}"
							path="plataformasEscogidas" /></td>
				</tr>

			</table>
			
			<button class="btn btn-default" type="submit">Actualizar Plataformas</button>

		</div>

	</form:form>

</petclinic:layout>
