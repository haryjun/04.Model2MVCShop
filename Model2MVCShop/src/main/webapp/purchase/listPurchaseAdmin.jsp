<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<link rel="stylesheet" href="/css/admin.css" type="text/css">
	
	<!--  ///////////////////////// Bootstrap, jQuery CDN ////////////////////////// -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" >
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" >
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>
	
	<!-- Bootstrap Dropdown Hover CSS -->
   <link href="/css/animate.min.css" rel="stylesheet">
   <link href="/css/bootstrap-dropdownhover.min.css" rel="stylesheet">
    <!-- Bootstrap Dropdown Hover JS -->
   <script src="/javascript/bootstrap-dropdownhover.min.js"></script>
   
   <!-- jQuery UI toolTip 사용 CSS-->
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <!-- jQuery UI toolTip 사용 JS-->
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	
	<!--  ///////////////////////// CSS ////////////////////////// -->
	<style>
	  body {
            padding-top : 50px;
        }
      .underline {
            text-decoration: underline;
            cursor: pointer;
        }
    </style>
    
<script type="text/javascript">
	function fncGetUserList(currentPage) {
		$("#currentPage").val(currentPage);
	   	$('form[name=detailForm]').attr("method", "POST").attr("action", "/purchase/listPurchase").submit();		
	}
	
	$(function(){
		$('tr td[title]').on("click", function(){
			var tranNo = $($(this).next('td').html()).val();
			if($( "#"+tranNo+"" ).html().length!=0){
				$( "#"+tranNo+"" ).empty();
			}else{
				$.ajax({
					url:"/purchase/json/getPurchase/"+tranNo,
					method:"POST",
					dataType:"json",
					headers:{
						"Accept" : "application/json",
						"Content-Type":"application/json"
					},
					success: function(JSONData, status){
						var displayValue= "<td></td><td colspan='8'><h5>"+"주문일: "+JSONData.orderDate+"<br>"
								+"상품명: "+JSONData.purchaseProd.prodName+"<br>"
								+"구매자: "+JSONData.buyer.userId+"<br>";
					   displayValue += "배송 받는 분: ";
					   displayValue += (JSONData.receiverName==null) ? "<br>" : JSONData.receiverName+"<br>"
						   
					   displayValue +=  "연락처: ";	   
					   displayValue += (JSONData.receiverPhone==null) ? "<br>" : JSONData.receiverPhone+"<br>"
						   
					   displayValue += "구매가격: "+JSONData.soldPrice+"&nbsp;(구매수량 : "+JSONData.amount+")<br>"
					   
					   displayValue +=  "배송 받으실 날짜: ";	   
					   displayValue += (JSONData.divyDate==null) ? "<br>" : JSONData.divyDate+"<br>"
						   
					   displayValue +=   "요청사항: ";
					   displayValue += (JSONData.divyRequest==null) ? "<br>" : JSONData.divyRequest+"<br>"
					   displayValue += "<hr>";

					displayValue += "</td></h5>"
					$("#"+tranNo+"").html(displayValue);

				},
				error:function(jqXHR){
					alert("error : "+jqXHR.status);
				}
			})
			}
		})
		
		$('select[name="searchCondition"]').on("change", function(){
			$('form').attr('method', 'POST').attr('action', '/purchase/listPurchase').submit();
		})
		
		$('.underline:contains("배송하기")').on("click", function(){
			var tranNo = $(this).closest('tr').find('input[type="hidden"]').val();
			location="/purchase/updateTranCode?tranNo="+tranNo+"&tranCode=3";
		})
	})
</script>
</head>

<body>
	<!-- ToolBar Start /////////////////////////////////////-->
	<jsp:include page="/layout/toolbar.jsp" />
    <!-- ToolBar End /////////////////////////////////////-->
    
    <form>
      <input type="hidden" id="currentPage" name="currentPage" value=""/>
    </form>

	<!--  화면구성 div Start /////////////////////////////////////-->
	<div class="container">
		<div class="page-header text-info">
	       <h3>(관리자용) 구매목록조회</h3>
	    </div>
	    
	    <form>
			<select name="searchCondition" class="form-control" style="width: 160px;float: right">
				<option value="8" ${empty search.searchCondition || !empty search.searchCondition && search.searchCondition=='8'? "selected" : ""}>전체보기</option>
				<option value="4" ${!empty search.searchCondition && search.searchCondition=='4'? "selected" : ""}>구매완료</option>
				<option value="5" ${!empty search.searchCondition && search.searchCondition=='5'? "selected" : ""}>배송중</option>
				<option value="6" ${!empty search.searchCondition && search.searchCondition=='6'? "selected" : ""}>배송완료</option>
			</select>
		</form>

		<!--  table Start /////////////////////////////////////-->
		<table class="table table-hover table-striped" >
			<thead>
				<tr>
					<th align="center">No</th>
					<th align="left" >상품명</th><th></th><th></th>
					<th align="left">유저아이디</th>
					<th align="left">주문일자</th>
					<th align="left">요청사항</th>
					<th align="left">배송현황</th>
					<th align="left">구매상태</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="i" value="0" />
				<c:forEach var="purchase" items="${list}">
					<c:set var="i" value="${ i+1 }" />
					<tr>
						<td align="center">${ i }</td>
						<td align="left" title="Click : 구매정보 확인">${purchase.purchaseProd.prodName}</td>
						<td><input type="hidden" value="${purchase.tranNo}"></td>
						<td align="left">${purchase.buyer.userId}</td>
						<td align="left">${purchase.orderDate}</td>
						<td align="left">${purchase.divyRequest}</td>
						<td align="left">
							현재
							<c:choose>
								<c:when test="${fn:trim(purchase.tranCode) == '2'}">
									구매 완료
								</c:when>
								<c:when test="${fn:trim(purchase.tranCode) == '3'}">
									배송중
								</c:when>
								<c:when test="${fn:trim(purchase.tranCode) == '4'}">
									배송 완료
								</c:when>
							</c:choose>
							상태 입니다.
						</td>
						<td align="left">
							<c:if test="${fn:trim(purchase.tranCode) == '2'}">
								구매완료
								<span class="underline">배송하기</span>
							</c:if>
							<c:if test="${fn:trim(purchase.tranCode) == '3'}">
								배송중
							</c:if>
							<c:if test="${fn:trim(purchase.tranCode) == '4'}">
								배송완료
							</c:if>
						</td>
					</tr>
					<tr id="${purchase.tranNo}"></tr>
				</c:forEach>
			</tbody>
		</table>
		<!--  table End /////////////////////////////////////-->
	</div>
	<!--  화면구성 div End /////////////////////////////////////-->

  <!-- PageNavigation Start... -->
	<jsp:include page="../common/pageNavigator_new.jsp"/>
	<!-- PageNavigation End... -->
</body>
</html>
