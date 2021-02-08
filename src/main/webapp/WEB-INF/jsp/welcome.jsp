<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->

<petclinic:layout pageName="home">
	<h1>Bienvenido</h1>
	</br>
	
	<div><c:if test="${message}"> <c:out value="${message}"/> </c:if> </div>
	
	<div class="row">
			<h2>Proyecto Freak Tracker</h2>
			<p>
			<h2>Grupo ${group}</h2>
			<p>
			<ul>
				</br>
				</br>
    
						<c:forEach items="${persons}" var="person">
    						<li>${person.firstName} ${person.lastName}</li>
    					</c:forEach>
    
			</ul>
				</br>
				</br>
    
	</div>
    
    <div class="row">
    	<div class="col-md-12">
    	<!--  
            <spring:url value="/resources/images/pets.png" htmlEscape="true" var="petsImage"/>
            <img class="img-responsive" src="${petsImage}"/>
        -->
        </div>
    </div>

</petclinic:layout>
