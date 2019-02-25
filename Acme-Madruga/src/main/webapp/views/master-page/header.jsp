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
	<a href="#"><img src="images/logo.png" alt="Acme Madruga Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/action-1.do"><spring:message code="master.page.administrator.action.1" /></a></li>
					<li><a href="administrator/action-2.do"><spring:message code="master.page.administrator.action.2" /></a></li>
					<li><a href="folder/list.do"><spring:message code="folder.list" /></a></li>					
					<li><a href="administrator/create.do"><spring:message code="master.page.administrator.create" /></a></li>					
					<li><a href="administrator/create.do"><spring:message code="master.page.administrator.create" /></a></li>					
				</ul>
			</li>
			<li><a href="brotherhood/listAll.do"><spring:message code="master.page.brotherhood.allBrotherhoods" /></a></li>
			
		</security:authorize>
		
		<security:authorize access="hasRole('BROTHERHOOD')">
			<li><a class="fNiv"><spring:message	code="master.page.brotherhood" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="brotherhood/edit.do"><spring:message code="master.page.brotherhood.edit" /></a></li>
					<li><a href="brotherhood/display.do"><spring:message code="master.page.brotherhood.display" /></a></li>					
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.member" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="member/list.do"><spring:message code="master.page.member.list" /></a></li>
				</ul>
			</li>
			<li><a href="procession/list.do"><spring:message code="master.page.brotherhood.edit" /></a></li>
		</security:authorize>
				
		<security:authorize access="hasRole('MEMBER')">
			<li><a class="fNiv"><spring:message	code="master.page.member" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="member/edit.do"><spring:message code="master.page.member.edit" /></a></li>
					<li><a href="member/display.do"><spring:message code="master.page.member.display" /></a></li>					
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.brotherhood" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="brotherhood/list.do"><spring:message code="master.page.brotherhood.list" /></a></li>
					<li><a href="brotherhood/allBrotherhoodsFree.do"><spring:message code="master.page.brotherhood.allFree" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.request" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="request/list.do"><spring:message code="master.page.request.list" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv" href="brotherhood/create.do"><spring:message code="master.page.brotherhood.register" /></a></li>
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
					<li><a href="profile/action-1.do"><spring:message code="master.page.profile.action.1" /></a></li>
					<li><a href="profile/action-2.do"><spring:message code="master.page.profile.action.2" /></a></li>
					<li><a href="profile/action-3.do"><spring:message code="master.page.profile.action.3" /></a></li>					
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

