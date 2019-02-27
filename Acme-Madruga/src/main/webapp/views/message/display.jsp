<%--
  ~ Copyright � 2017. All information contained here included the intellectual and technical concepts are property of Null Point Software.
  --%>

<%--
  Created by IntelliJ IDEA.
  User: mruwzum
  Date: 1/3/17
  Time: 11:46
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>



<spring:message code="mezzage.subject" var="subject1"/>
<h3><jstl:out value="${subject1}"/></h3>
<jstl:out value="${subject}"/>


<spring:message code="mezzage.body" var="body1"/>
<h3><jstl:out value="${body1}"/></h3>
<jstl:out value="${body}"/>


<spring:message code="mezzage.sender" var="sender1"/>
<h3><jstl:out value="${sender1}"/></h3>
<jstl:out value="${recipients}"/>

<spring:message code="mezzage.priority" var="priority1"/>
<h3><jstl:out value="${priority1}"/></h3>
<jstl:out value="${priority}"/>

<br>
<input type="button" class="btn btn-danger" name="folder"
       value="<spring:message code="general.cancel" />"
       onclick="relativeRedir('folder/list.do');"/>