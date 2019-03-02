<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<form:form action="area/edit.do"  modelAttribute="area" >

	<form:hidden path="id" />
	<form:hidden path="version" /> 


	
	<security:authorize access="hasRole('ADMIN')">
	
	<form:label path="name">
		<spring:message code="area.name" />
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	
	<form:label path="pictures">
		<spring:message code="area.pictures" />
	</form:label>
	<form:input path="pictures" />
	<form:errors cssClass="error" path="pictures" />
	
	
	

	</security:authorize>

	
	<!-- BOTONES --> 
	
	 <input type="submit" name="save"
		value="<spring:message code="area.save"/>" />&nbsp;
		
	<!--<jstl:if test="${position.id!=0}">
		<input type="submit" name="delete"
			value="<spring:message code="position.delete"/>"
			onclick="javascript: return confirm('<spring:message code="position.confirm.delete"/>')" />&nbsp;
	</jstl:if>-->
		

	
	  <input type="button" name="cancel"
		value="<spring:message code="area.cancel"/>"
		onclick="javascript: window.location.replace('area/list.do')" />
	<br />
		
</form:form>

	<jstl:if test="${area.id!=0}">
	<input type="button" name="delete"
		value="<spring:message code="area.delete"/>"
		onclick="javascript: window.location.replace('area/delete.do?areaId=${area.id}')" />
	<br />
	</jstl:if>