<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>



<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ page import="java.util.*" %>



 
 <!-- TABLE --> 
 <table>

    <tr>
    <td><spring:message code="average.members.brotherhood" var="averageH" /><jstl:out value="${averageH}" /></td>
    <td>${averageMembers}</td>
    </tr>
    <tr>
    <td><spring:message code="min.members.brotherhood" var="minH" /><jstl:out value="${minH}" /></td>
    <td>${minMembers}</td>
    </tr>
    <tr>
    <td><spring:message code="max.members.brotherhood" var="maxH" /><jstl:out value="${maxH}" /></td>
    <td>${maxMembers}</td>
    </tr>
    <tr>
    <td><spring:message code="desviation.members.brotherhood" var="desH" /><jstl:out value="${desH}" /></td>
    <td>${desviationMembers}</td>
    </tr>
    <tr>
    <td><spring:message code="largest.brotherhood" var="largestH" /><jstl:out value="${largestH}" /></td>
    <td>${largest}</td>
    </tr>
    <tr>
    <td><spring:message code="smallest.brotherhood" var="smallestH" /><jstl:out value="${smallestH}" /></td>
    <td>${smallest}</td>
    </tr>
    <tr>
    <td><spring:message code="processions.soon" var="soonH" /><jstl:out value="${soonH}" /></td>
    <td>${soon}</td>
    </tr>
    <tr>
    <td><spring:message code="requests.approved" var="reqaH" /><jstl:out value="${reqaH}" /></td>
    <td>${requestsApproved}</td>
    </tr>
     <tr>
    <td><spring:message code="requests.pending" var="reqpH" /><jstl:out value="${reqpH}" /></td>
    <td>${requestsPending}</td>
    </tr>
     <tr>
    <td><spring:message code="requests.rejected" var="reqrH" /><jstl:out value="${reqrH}" /></td>
    <td>${requestsRejected}</td>
    </tr>
     <tr>
    <td><spring:message code="members.percent" var="memberspH" /><jstl:out value="${memberspH}" /></td>
    <td>${membersPercent}</td>
    </tr>
     <tr>
    <td><spring:message code="average.brotherhood.area" var="averageBH" /><jstl:out value="${averageBH}" /></td>
    <td>${averageBrotherhoods}</td>
    </tr>
    <tr>
    <td><spring:message code="min.brotherhood.area" var="minBH" /><jstl:out value="${minBH}" /></td>
    <td>${minBrotherhoods}</td>
    </tr>
    <tr>
    <td><spring:message code="max.brotherhood.area" var="maxBH" /><jstl:out value="${maxBH}" /></td>
    <td>${maxBrotherhoods}</td>
    </tr>
    <tr>
    <td><spring:message code="desviation.brotherhood.area" var="desBH" /><jstl:out value="${desBH}" /></td>
    <td>${desviationBrotherhoods}</td>
    </tr>
    <td><spring:message code="average.results" var="averageRH" /><jstl:out value="${averageRH}" /></td>
    <td>${averageResults}</td>
    </tr>
    <tr>
    <td><spring:message code="min.results" var="minRH" /><jstl:out value="${minRH}" /></td>
    <td>${minResults}</td>
    </tr>
    <tr>
    <td><spring:message code="max.results" var="maxRH" /><jstl:out value="${maxRH}" /></td>
    <td>${maxResults}</td>
    </tr>
    <tr>
    <td><spring:message code="desviation.results" var="desRH" /><jstl:out value="${desRH}" /></td>
    <td>${desviationResults}</td>
    </tr>
    <tr>
    <td><spring:message code="ratio.finders" var="ratioFH" /><jstl:out value="${ratioFH}" /></td>
    <td>${ratioFinders}</td>
    </tr>
    
    


</table>
