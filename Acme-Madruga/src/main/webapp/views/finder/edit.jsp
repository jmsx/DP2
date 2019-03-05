
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="finder/edit.do" modelAtribute="finder" method="POST">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="processions"/>
	
	<form:label path="keyWord">
		<spring:message code="finder.keyWord" />: </form:label>
	<form:input path="keyWord" />
	<form:errors cssClass="error" path="keyWord" />
	<br />
	<form:label path="areaName">
		<spring:message code="finder.areaName" />: </form:label>
	<form:input path="areaName" />
	<form:errors cssClass="error" path="areaName" />
	<br />
	<form:label path="minDate">
		<spring:message code="finder.minDate" />: </form:label>
	<form:input path="minDate" placeholder = "yyyy-MM-dd" />
	<form:errors cssClass="error" path="minDate" />
	<br />
	<form:label path="maxDate">
		<spring:message code="finder.maxDate" />: </form:label>
	<form:input path="maxDate" placeholder = "yyyy-MM-dd" />
	<form:errors cssClass="error" path="maxDate" />
	<br />
	
	
	<input type="submit" name="save"
           value="<spring:message code="general.search" />"/>

</form:form>