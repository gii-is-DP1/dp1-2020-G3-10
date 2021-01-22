<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#main-navbar">
				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">

<!--			<petclinic:menuItem active="${name eq 'home'}" url="/"
					title="home page">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					<span>Home</span>
				</petclinic:menuItem>
 -->
				<petclinic:menuItem active="${name eq 'peliculas'}" url="/peliculas"
					title="peliculas">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Peliculas</span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'videojuegos'}"
					url="/videojuegos" title="videojuegos">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Videojuegos</span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'merchandasing'}" url="#"
					title="merchandasing">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Merchandasing</span>
				</petclinic:menuItem>
<!--
				<petclinic:menuItem active="${name eq 'error'}" url="/oups"
					title="trigger a RuntimeException to see how it is handled">
					<span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span>
					<span>Error</span>
				</petclinic:menuItem>
	-->			
				<sec:authorize access="hasAuthority('admin')">
				<petclinic:menuItem active="${name eq 'reproductores'}" url="/reproductores"
					title="reproductores">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Reproductores</span>
				</petclinic:menuItem>
				</sec:authorize>


			</ul>




			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Entrar</a></li>
					<li><a href="<c:url value="/users/select" />">Registrarse</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<sec:authorize access="hasAuthority('cliente')">
					<li><a href="<c:url value="/pedidos" />">Mis Pedidos</a></li>
					<li><a href="<c:url value="/pedidos/mostrarCarrito" />">Carrito</a></li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('vendedor')">
					<li><a href="<c:url value="/pedidos" />">Mis Pedidos</a></li>
					<li><a href="<c:url value="/pedidos/mostrarCarrito" />">Mis Productos</a></li>
					</sec:authorize>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span><strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											<p class="text-left">
												<a href="<c:url value="/logout" />"
													class="btn btn-primary btn-block btn-sm">Cerrar Sesion</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>

							<li>
								<div class="navbar-login navbar-login-session">
									<div class="row">
										<div class="col-lg-12">
											<p>
												<a href="/clientes/miPerfil" class="btn btn-primary btn-block">Mi Perfil</a>
												
												<%-- <spring:url value="/vendedores/{vendedorId}" var="vendedorUrl">
                                                <spring:param name="vendedorId" value="${vendedor.id}"/>
                                                </spring:url>
												<a href="${fn:escapeXml(vendedorUrl)}" class="btn btn-danger btn-block">Perfil</a> 
												  
												  Para ver perfil???--%>
												
												
												<a active="${name eq 'error'}" url="/oups" title="trigger a RuntimeException to see how it is handled" class="btn btn-danger btn-block">Error</a>
											</p>
										</div>
									</div>
								</div>
							</li>

						</ul></li>
				</sec:authorize>
			</ul>
		</div>



	</div>
</nav>
