<%@ page contentType="text/html; charset=euc-kr" pageEncoding="euc-kr"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<html>
<head>
<meta charset="euc-kr" />
<title>열어본 상품 보기</title>
</head>

<body>
	당신이 열어본 상품을 알고 있다
	<br>
	<br>
	<%-- <%
	request.setCharacterEncoding("euc-kr");
	response.setCharacterEncoding("euc-kr");
	
	String history = null;
	
	Cookie[] cookies = request.getCookies();
	
	if (cookies!=null && cookies.length > 0) {
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("history")) {
				history = cookie.getValue();
			}
		}
		if (history != null) {
			String[] h = history.split(",");
			for (int i = 0; i < h.length; i++) {
				if (!h[i].equals("null")) {
%> --%>
		<c:if test="${cookie.history.name =='history' }">
			<c:set var="hSplit" value="${fn:split(cookie.history.value,'/') }" />
			<c:forEach var="h" items="${hSplit }">
			<c:set var="noNameSplit" value="${fn:split(h,':') }"/>
				<a href="/getProduct.do?prodNo=${noNameSplit[0]}&menu=search" target="rightFrame">${noNameSplit[1]}</a><br/>
			</c:forEach>
		</c:if>

	<%--<a href="/getProduct.do?prodNo=<%=h[i]%>&menu=search"	target="rightFrame"><%=h[i]%></a>
<br>
 <%
				}
			}
		}
	}
%> --%>

</body>
</html>