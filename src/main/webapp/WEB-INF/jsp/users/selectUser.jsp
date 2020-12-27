<%@ page session="false" trimDirectiveWhitespaces="true" %> <%@ taglib
prefix="spring" uri="http://www.springframework.org/tags" %> <%@ taglib
prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib
prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> <%@ taglib
prefix="form" uri="http://www.springframework.org/tags/form" %> <%@
taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="users">

	<h1>Tipos de Usuario</h1>
	<h2>Seleccione el tipo de usuario que desea crear:</h2>
	<table>
		<tr>
			<td>
				<ul>

					<a href="/clientes/new" class = "btn btn-default" > Cliente</a>
				</ul>
				<ul>
					<a href="/vendedores/new" class = "btn btn-default"> Vendedor</a>

				</ul>


			</td>
		</tr>
	</table>
</petclinic:layout>
