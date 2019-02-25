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


<display:table name="members" id="row"
	requestURI="member/list.do" pagesize="5"
	class="displaytag">

	<security:authorize access="hasRole('BROTHERHOOD')">
	<jstl:if test="${ok}">
		<display:column>
			<a href="brotherhood/dropOut.do?memberId=${row.id}">
				<spring:message code="brotherhood.dropOut" />
			</a>
		</display:column>
	</jstl:if>
	</security:authorize>

	<display:column property="name" titleKey="member.name" />
	
	<display:column property="middleName" titleKey="member.middleName" />
	
	<display:column property="surname" titleKey="member.surname" />
	
	<display:column property="photo" titleKey="member.photo" />
	
	<display:column property="email" titleKey="member.email" />
	
	<display:column property="phone" titleKey="member.phone" />
	
	<display:column property="address" titleKey="member.address" />
	
	<display:column property="score" titleKey="member.spammer" />
	
	<display:column>
		<a href="member/displayTabla.do?memberId=${row.id}"> <spring:message
				code="member.display" />
		</a>
	</display:column>


</display:table>
