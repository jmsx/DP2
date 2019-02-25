
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<jstl:if test="${not empty rol}">
	<jstl:set var="rolURL" value="/${rol}" />
</jstl:if>

<form:form action="request/${rol}/edit.do"
	modelAttribute="request">

	<form:hidden path="id" />
	<form:hidden path="version" />



	<security:authorize access="hasRole('MEMBER')">

		<form:label path="price">
			<spring:message code="request.price" />:
		</form:label>
		<form:input path="price" />
		<form:errors cssClass="error" path="price" />
		<br />

		<form:label path="comments">
			<spring:message code="request.comments" />:
		</form:label>
		<form:textarea path="comments" />
		<form:errors cssClass="error" path="comments" />
		<br />
		<form:label path="procession">
			<spring:message code="request.procession" />:
		</form:label>
		<form:select id="processions" path="procession">
			<form:options items="${processionsAvailable}" itemLabel="ticker"
				itemValue="id" />
			<jstl:if test="${not empty procession}">
				<form:option value="${procession}" label="${procession.ticker}"
					selected="selected" />
			</jstl:if>
		</form:select>
		<form:errors cssClass="error" path="procession" />
		<br />

		<input type="submit" name="save"
			value="<spring:message code="request.save" />" />

	</security:authorize>

	<security:authorize access="hasRole('BROTHERHOOD')">
		<form:hidden path="moment" />
		<form:hidden path="comments" />
		<form:hidden path="procession" />

		<spring:message code="request.moment" />:
		<jstl:out value="${request.moment}" />
		<br />

		<jstl:set var="newPrice" value="${(request.price*vat)/100}" />

		<spring:message code="request.price" />:
		<jstl:out value="${request.price} (+${newPrice}" />
		<spring:message code="request.vat" />)
		<br />

		<spring:message code="request.comments" />:
		<ul>
			<jstl:forEach var="comment" items="${request.comments}">
				<li><jstl:out value="${comment}" /></li>
			</jstl:forEach>
		</ul>
		<br />

		<spring:message code="request.procession.ticker" />:
		<jstl:out value="${request.procession.ticker}" />
		<br />
		<br />

		<jstl:choose>
			<jstl:when test="${mostrarTarjetas eq 'ACCEPTED'}">
				<form:hidden path="status" value="ACCEPTED" />
				<form:label path="creditCard">
					<spring:message code="request.creditCard" />:
			</form:label>
				<form:select id="cards" path="creditCard">
					<jstl:forEach var="card" items="${myCards}">
						<form:option value="${card.id}">
							<jstl:out value="${card.brandName } - number: ${card.number}" />
						</form:option>
					</jstl:forEach>
				</form:select>
				<form:errors cssClass="error" path="creditCard" />
				<br />
				<br />
			</jstl:when>
			<jstl:otherwise>
				<jstl:if test="${request.status eq 'PENDING'}">
					<form:label path="status">
						<spring:message code="request.status" />: </form:label>
					<form:select id="statuss" path="status">
						<form:option value="PENDING">
							<spring:message code="request.status.pending" />
						</form:option>
						<form:option value="ACCEPTED">
							<spring:message code="request.status.accepted" />
						</form:option>
						<form:option value="REJECTED">
							<spring:message code="request.status.rejected" />
						</form:option>

					</form:select>

					<form:errors cssClass="error" path="status" />
					<br />
					<br />

				</jstl:if>
			</jstl:otherwise>
		</jstl:choose>
		<jstl:if test="${request.status eq 'PENDING'}">
			<input type="submit" name="save"
				value="<spring:message code="request.save" />" />
		</jstl:if>

		<%
			if (request.getAttribute("creditCard") != null) {
		%>
		<input type="button" name="creditCardDisplay"
			value="<spring:message code="creditCard.display" />"
			onclick="javascript: relativeRedir('creditCard/customer/display.do?requestId=${request.id}');" />
		<%
			}
		%>
		<form:hidden path="comments" />

	</security:authorize>

	<acme:button url="request/${rol}/list.do" name="cancel" code="request.cancel"/>

</form:form>