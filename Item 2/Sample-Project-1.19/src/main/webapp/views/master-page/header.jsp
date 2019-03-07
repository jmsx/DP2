<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="${bannerURL}" alt="Acme Madruga Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		
		<security:authorize access="isAuthenticated()">
			<li><a href="folder/list.do"><spring:message code="master.page.folder.list" /></a></li>
		</security:authorize>
		
		<!-- ========================================================================================================= -->
		<!-- ========================================  ADMINISTRATOR  ================================================ -->
		<!-- ========================================================================================================= -->
		
		<security:authorize access="hasRole('ADMIN')">
			

			
			
		</security:authorize>
		
		
		<!-- ========================================================================================================= -->
		<!-- ============================================   MEMBER   ================================================= -->
		<!-- ========================================================================================================= -->
		
		<security:authorize access="hasRole('ADMIN')">
			

			
			
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv" href="member/create.do"><spring:message code="master.page.member.register" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('ADMIN')">
					<li><a href="administrator/edit.do"><spring:message code="master.page.member.edit" /></a></li>
					<li><a href="administrator/display.do"><spring:message code="master.page.member.display" /></a></li>
					</security:authorize>
					<li><a href="socialProfile/list.do"><spring:message code="master.page.actor.socialProfiles" /></a></li>
					<security:authorize access="hasRole('MEMBER')">
					<li><a href="member/edit.do"><spring:message code="master.page.member.edit" /></a></li>
					<li><a href="member/display.do"><spring:message code="master.page.member.display" /></a></li>
					</security:authorize>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

