<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="peliculas">
	<h2>PELICULAS</h2>



	<table id="peliculasTable" class="table table-striped">
		<thead>
			<tr>
				<th>Nombre</th>
				<th>Año</th>
				<th>Duracion</th>
				<th>Director</th>
			</tr>
		</thead>

		
	</table>


	<a class="btn btn-default">Editar Pelicula</a>
	<a class="btn btn-default">Eliminar Pelicula</a>


</petclinic:layout>