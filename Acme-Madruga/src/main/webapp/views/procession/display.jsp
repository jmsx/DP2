<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<style type="text/css">

.PENDING {
	color: grey;
}

.APPROVED {
	color: green;
}

.REJECTED {
	color: orange;
}
</style>

<jstl:if test="${not empty rol}">
	<jstl:set var="rolURL" value="/${rol}" />
</jstl:if>

<spring:message code="procession.title"/>: <jstl:out value="${procession.title}" />
<br/>

<spring:message code="procession.description"/>: <jstl:out value="${procession.description}" />
<br/>

<acme:date value="procession.moment" code="procession.moment"/>

<spring:message code="procession.ticker"/>: <jstl:out value="${procession.ticker}" />
<br/>

<spring:message code="procession.mode"/>: <jstl:out value="${procession.mode}" />
<br/>

<spring:message code="procession.maxRows"/>: <jstl:out value="${procession.maxRows}" />
<br/>

<spring:message code="procession.maxColumns"/>: <jstl:out value="${procession.maxColumns}" />
<br/>


<jstl:choose>
	<jstl:when test="${not empty procession.floats}">
		<jstl:forEach items="${procession.floats}" var="f" varStatus="loop">
			<spring:message code="procession.float"/><jstl:out value=" ${loop.index+1}"/>:
			<a href="float/display.do?floatId=${f.id}">
				<jstl:out value="${f.title}"/>
			</a><br/>
		</jstl:forEach>
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="procession.no.float"/>
	</jstl:otherwise>
</jstl:choose>



<spring:message code="procession.brotherhood"/>: 
	<a href="brotherhood/display.do?brotherhoodId=${procession.brotherhood.id}">
		<jstl:out value="${procession.brotherhood.title}" />
	</a>
<br/>


<br/>


<security:authorize access="hasRole('BROTHERHOOD')">
	<spring:message code="procession.requests"/>:
	<display:table name="requests" id="fila" pagesize="5" class="displaytag" requestURI="procession/brotherhood/display.do?processionId=${procession.id}">
		<jstl:set var="colorStyle" value="${fila.status}" />
		<jstl:choose>
			<jstl:when test="${fila.status eq 'PENDING'}">
				<display:column>
					<a href="request/brotherhood/edit.do?requestId=${fila.id}">
						<spring:message code="procession.request.edit"/>
					</a>
				</display:column>
			</jstl:when>
			<jstl:otherwise>
			</jstl:otherwise>
		</jstl:choose>
		<acme:dataTableColumn property="moment" code="request.moment"/>
		<display:column property="status" titleKey="request.status" class="${colorStyle}"/>
	</display:table>
	<br/>
</security:authorize>


<acme:button url="procession${rolURL}/list.do" name="back" code="procession.back"/>
<br />
