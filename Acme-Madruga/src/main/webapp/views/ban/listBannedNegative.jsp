<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>



<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table pagesize="10" class="displayspammer" keepStatus="true"
	name="actors" requestURI="ban/administrator/listBannedSpammer.do" id="row">
	
	<!-- Action links -->

<security:authorize access="hasRole('ADMIN')">
		
		<spring:message code="actor.unban.header" var="unbanHeader"></spring:message>
		<display:column title="${unbanHeader}" sortable="false">
			<spring:url value="ban/administrator/unban.do" var="unbanURL">
				<spring:param name="actorId" value="${row.id}"/> <!-- es el parámetro que va a acompañar a la url -->
			</spring:url>
			<a href="${unbanURL}"><spring:message code="unban.edit"/></a>
		</display:column>	
		</security:authorize>		
	
	
		<!-- Attributes of the positions -->
 
		<spring:message code="actor.surname" var="surnameHeader" />
		<display:column property="surname" title="${surnameHeader}" sortable="false" />
		

	
</display:table>