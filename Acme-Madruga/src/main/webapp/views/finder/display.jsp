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

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jstl:choose>
	<jstl:when test="${lang eq 'en' }">
		<spring:message code="finder.minDate" />: <fmt:formatDate
			value="${finder.minDate}" type="both" pattern="yyyy-MM-dd" />
		<spring:message code="finder.maxDate" />: <fmt:formatDate
			value="${finder.maxDate}" type="both" pattern="yyyy-MM-dd" />
	</jstl:when>
	<jstl:otherwise>
	<spring:message code="finder.minDate" />: <fmt:formatDate
			value="${finder.minDate}" type="both" pattern="dd-MM-yyyy" />
	<spring:message code="finder.maxDate" />: <fmt:formatDate
			value="${finder.maxDate}" type="both" pattern="dd-MM-yyyy" />
	</jstl:otherwise>
</jstl:choose>
<br>

<acme:display code="finder.keyword" value="${finder.keyword}"/>
<acme:display code="finder.areaName" value="${finder.areaName}"/>

<display:table name="processions" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<display:column property="title" titleKey="procession.title" />
	
	<display:column property="ticker" titleKey="procession.ticker" />

	<acme:dataTableColumn code="procession.moment" property="moment" />
	
	<display:column titleKey="procession.brotherhood">
		<a href="brotherhood/display.do?brotherhoodId=${row.brotherhood.id}">
			<jstl:out value="${row.brotherhood.title}" />
		</a>
	</display:column>
	
	<display:column>
		<acme:link url="procession${rolURL}/display.do?processionId=${row.id}"
			code="procession.display" />
	</display:column>
	
	
</display:table>

<input type="submit" name="clear" value="<spring:message code="general.clear" />"/>