<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<style type="text/css">
.PENDING {
	background-color: #d9d9d9;
}

.APPROVED {
	background-color: green;
}

.REJECTED {
	background-color: orange;
}
</style>

<jstl:if test="${not empty rol}">
	<jstl:set var="rolURL" value="/${rol}" />
</jstl:if>

<acme:display code="procession.title" value="${procession.title}" />
<acme:display code="procession.description"
	value="${procession.description}" />
	
<jstl:choose>
	<jstl:when test="${lang eq 'en' }">
		<spring:message code="procession.moment" />: <fmt:formatDate
			value="${procession.moment}" type="both" pattern="yyyy/MM/dd HH:mm" />
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="procession.moment" />: <fmt:formatDate
			value="${procession.moment}" type="both" pattern="dd/MM/yyyy HH:mm" />
	</jstl:otherwise>
</jstl:choose>
</br>

<acme:display code="procession.ticker" value="${procession.ticker}" />
<acme:display code="procession.mode" value="${procession.mode}" />
<acme:display code="procession.maxRows" value="${procession.maxRows}" />
<acme:display code="procession.maxColumns"
	value="${procession.maxColumns}" />

<jstl:forEach items="${procession.floats}" var="f" varStatus="loop">
	<jstl:out value="${loop.index+1} " /><spring:message code="procession.float" />
	: <a href="float/display.do?floatId=${f.id}"><jstl:out value="${f.title}"/></a>
	<br />
</jstl:forEach>

<spring:message code="procession.brotherhood" />:
<a href="brotherhood/display.do?brotherhoodId=${procession.brotherhood.id}"><jstl:out value="${procession.brotherhood.title}"/></a>
<br />
<br />

<security:authorize access="hasRole('BROTHERHOOD')">
	<spring:message code="procession.requests" />:
	<display:table name="requests" id="fila" pagesize="5"
		class="displaytag"
		requestURI="procession/brotherhood/display.do?processionId=${procession.id}">
		<jstl:set var="colorStyle" value="${fila.status}" />
		<jstl:choose>
			<jstl:when test="${fila.status eq 'PENDING'}">
				<display:column>
					<acme:link url="request/brotherhood/edit.do?requestId=${fila.id}"
						code="procession.request.edit" />
				</display:column>
			</jstl:when>
			<jstl:otherwise>
			</jstl:otherwise>
		</jstl:choose>
		<acme:dataTableColumn property="moment" code="request.moment" />
		<display:column property="status" titleKey="request.status"
			class="${colorStyle}" />
	</display:table>
	<br />
</security:authorize>

<acme:button url="procession${rolURL}/list.do" name="back"
	code="procession.back" />
<br />
