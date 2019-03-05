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

<acme:display code="finder.keyWord" value="${finder.keyWord}"/>
<acme:display code="finder.areaName" value="${finder.areaName}"/>
<acme:display code="finder.minDate" value="${finder.minDate}"/>
<acme:display code="finder.maxDate" value="${finder.maxDate}"/>