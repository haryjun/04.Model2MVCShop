<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<%@ page import="java.util.*"%>
<%@ page import="com.model2.mvc.common.*"%>
<%@ page import="com.model2.mvc.service.domain.*"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%-- <%
	Map<String, Object> map = (Map<String, Object>) request.getAttribute("map");
	Search search = (Search) request.getAttribute("search");
   
   int total = 0;
   List<Purchase> list = null;
   if (map != null) {
      total = ((Integer) map.get("count")).intValue();
      list = (List<Purchase>) map.get("list");
   }
   
   int currentPage = search.getCurrentPage();
   
   int totalPage = 0;
   if (total > 0) {
      totalPage = total / search.getPageSize();
      if (total % search.getPageSize() > 0)
         totalPage += 1;
   }
%> --%>

<html>
<head>
<title>구매 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
	function fncGetUserList() {
		document.detailForm.submit();
	}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

	<div style="width: 98%; margin-left: 10px;">

		<form name="detailForm" action="/listUser.do" method="post">

			<table width="100%" height="37" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"
						width="15" height="37"></td>
					<td background="/images/ct_ttl_img02.gif" width="100%"
						style="padding-left: 10px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="93%" class="ct_ttl01">구매 목록조회</td>
							</tr>
						</table>
					</td>
					<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"
						width="12" height="37"></td>
				</tr>
			</table>

			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>
					<td colspan="11">전체 ${resultPage.totalCount} 건수, 현재 ${resultPage.currentPage} 페이지
					</td>
				</tr>
				<tr>
					<td class="ct_list_b" width="100">No</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">회원ID</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">회원명</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">전화번호</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">배송현황</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">정보수정</td>
				</tr>
				<tr>
					<td colspan="11" bgcolor="808285" height="1"></td>
				</tr>

			
			
			<%-- <%if(total==0) {%> --%>
			<tr class="ct_list_pop"> 
				<td align="center" colspan="100%">???? ?????? ???????.</td>
			</tr>
			<tr>
				<td colspan="11" bgcolor="D6D7D6" height="1"></td>
			</tr>
			<%-- <%
				} else {
			%>
			<%
				int no=list.size();
				               for(int i=0; i<list.size(); i++) {
				                  Purchase purchase = (Purchase)list.get(i);
			%> --%>

				<tr class="ct_list_pop"> 
					<td align="center"><a href="/getPurchase.do?tranNo=${purchase.tranNo}">${purchase.tranNo}</a>
					</td>
					<td></td>
					<td align="left"><a href="/getUser.do?userId=${purchase.buyer.userId}">${purchase.buyer.userId}</a></td>
					<td></td>
					<td align="left">${purchase.buyer.userName}</td>
					<td></td>
					<td align="left">${purchase.buyer.phone}</td>
					<td></td>
					<td align="left">
						<c:choose>
					        <c:when test="${purchase.tranCode == '1'}">
					            구매완료 // 배송 준비중입니다.
					        </c:when>
					        <c:when test="${purchase.tranCode == '2'}">
					            구매하신 상품이 배송중입니다.
					        </c:when>
					        <c:when test="${purchase.tranCode == '3'}">
					            [상품 수령 완료] == 상품 거래가 끝났습니다.
					        </c:when>
					    </c:choose>
					<%-- <% if(purchase.getTranCode().equals("1")) { %>
					구매완료 // 배송 준비중입니다.
					<% } else if(purchase.getTranCode().equals("2")) { %>
					구매하신 상품이 배송중입니다.
					<% } else if(purchase.getTranCode().equals("3")) { %>
					[상품 수령 완료] == 상품 거래가 끝났습니다.
					<% } %>  --%>
					</td>
					<td></td>
					<td align="left">
					<%-- <% if(purchase.getTranCode().equals("2")) { %>
					<a href="updateTranCode.do?tranNo=<%= purchase.getTranNo() %>&tranCode=3"/>물건 도착(수령 확인)</a>
					<% } %>  --%>
					</td>
				</tr>
				<tr>
					<td colspan="11" bgcolor="D6D7D6" height="1"></td>
				</tr>
			<%-- <%} %>
			<%} %> --%>
			</table>
			
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>
					<td align="center">
					<a href="/listPurchase.do?page=${resultPage.currentPage}">${resultPage.currentPage} </a>
					<%--<% for(int i=1;i<=totalPage;i++){ %> 
					<ahref="/listPurchase.do?page=<%=i%>"><%=i %></a> <% } %> --%>
						
						<jsp:include page="../common/pageNavigator.jsp"/>	
					</td>
				</tr>
			</table>
			<!--  페이지 Navigator 끝 -->
			
		</form>

	</div>

</body>
</html>