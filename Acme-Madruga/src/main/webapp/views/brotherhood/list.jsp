<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<display:table name="brotherhoods" id="row"
	requestURI="brotherhood/list.do" pagesize="5"
	class="displaytag">

	<security:authorize access="hasRole('MEMBER')">
	<jstl:if test="${ok}">
		<display:column>
			<a href="enrolment/create.do">
				<spring:message code="brotherhood.enrolment.edit" />
			</a>
		</display:column>
	</jstl:if>
	</security:authorize>

	<display:column property="name" titleKey="actor.name" />
	
	<display:column property="middleName" titleKey="actor.middleName" />
	
	<display:column property="surname" titleKey="actor.surname" />
	
	<display:column property="photo" titleKey="actor.photo" />
	
	<display:column property="email" titleKey="actor.email" />
	
	<display:column property="phone" titleKey="actor.phone" />
	
	<display:column property="address" titleKey="actor.address" />
	
	<display:column property="score" titleKey="actor.spammer" />
	
	<display:column property="title" titleKey="actor.title" />
	
	<acme:dataTableColumn code="actor.date" property="date" />
	
	<display:column property="pictures" titleKey="actor.pictures" />
	
	<display:column property="area.name" titleKey="actor.area" />
	
	<display:column>
		<a href="brotherhood/displayTabla.do?brotherhoodId=${row.id}"> <spring:message
				code="brotherhood.display" />
		</a>
	</display:column>


</display:table>
