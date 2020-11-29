<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>PELICULAS</title>
</head>
<body>
        
        <petclinic:layout pageName="peliculas">
        	 <h2>PELICULAS</h2>

  			  <table id="peliculasTable" class="table table-striped">
       			 <thead>
       				 <tr>
          				  <th>Nombre</th>
        			   	<th>Director</th>
       				 </tr>
       			 </thead>
       			 </table>
       			 </petclinic:layout>
       			
  

</body>
</html>