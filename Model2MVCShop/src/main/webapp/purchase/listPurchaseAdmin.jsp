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
   
   <!-- jQuery UI toolTip ��� CSS-->
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <!-- jQuery UI toolTip ��� JS-->
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
						var displayValue= "<td></td><td colspan='8'><h5>"+"�ֹ���: "+JSONData.orderDate+"<br>"
								+"��ǰ��: "+JSONData.purchaseProd.prodName+"<br>"
								+"������: "+JSONData.buyer.userId+"<br>";
					   displayValue += "��� �޴� ��: ";
					   displayValue += (JSONData.receiverName==null) ? "<br>" : JSONData.receiverName+"<br>"
						   
					   displayValue +=  "����ó: ";	   
					   displayValue += (JSONData.receiverPhone==null) ? "<br>" : JSONData.receiverPhone+"<br>"
						   
					   displayValue += "���Ű���: "+JSONData.soldPrice+"&nbsp;(���ż��� : "+JSONData.amount+")<br>"
					   
					   displayValue +=  "��� ������ ��¥: ";	   
					   displayValue += (JSONData.divyDate==null) ? "<br>" : JSONData.divyDate+"<br>"
						   
					   displayValue +=   "��û����: ";
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
		
		$('.underline:contains("����ϱ�")').on("click", function(){
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

	<!--  ȭ�鱸�� div Start /////////////////////////////////////-->
	<div class="container">
		<div class="page-header text-info">
	       <h3>(�����ڿ�) ���Ÿ����ȸ</h3>
	    </div>
	    
	    <form>
			<select name="searchCondition" class="form-control" style="width: 160px;float: right">
				<option value="8" ${empty search.searchCondition || !empty search.searchCondition && search.searchCondition=='8'? "selected" : ""}>��ü����</option>
				<option value="4" ${!empty search.searchCondition && search.searchCondition=='4'? "selected" : ""}>���ſϷ�</option>
				<option value="5" ${!empty search.searchCondition && search.searchCondition=='5'? "selected" : ""}>�����</option>
				<option value="6" ${!empty search.searchCondition && search.searchCondition=='6'? "selected" : ""}>��ۿϷ�</option>
			</select>
		</form>

		<!--  table Start /////////////////////////////////////-->
		<table class="table table-hover table-striped" >
			<thead>
				<tr>
					<th align="center">No</th>
					<th align="left" >��ǰ��</th><th></th><th></th>
					<th align="left">�������̵�</th>
					<th align="left">�ֹ�����</th>
					<th align="left">��û����</th>
					<th align="left">�����Ȳ</th>
					<th align="left">���Ż���</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="i" value="0" />
				<c:forEach var="purchase" items="${list}">
					<c:set var="i" value="${ i+1 }" />
					<tr>
						<td align="center">${ i }</td>
						<td align="left" title="Click : �������� Ȯ��">${purchase.purchaseProd.prodName}</td>
						<td><input type="hidden" value="${purchase.tranNo}"></td>
						<td align="left">${purchase.buyer.userId}</td>
						<td align="left">${purchase.orderDate}</td>
						<td align="left">${purchase.divyRequest}</td>
						<td align="left">
							����
							<c:choose>
								<c:when test="${fn:trim(purchase.tranCode) == '2'}">
									���� �Ϸ�
								</c:when>
								<c:when test="${fn:trim(purchase.tranCode) == '3'}">
									�����
								</c:when>
								<c:when test="${fn:trim(purchase.tranCode) == '4'}">
									��� �Ϸ�
								</c:when>
							</c:choose>
							���� �Դϴ�.
						</td>
						<td align="left">
							<c:if test="${fn:trim(purchase.tranCode) == '2'}">
								���ſϷ�
								<span class="underline">����ϱ�</span>
							</c:if>
							<c:if test="${fn:trim(purchase.tranCode) == '3'}">
								�����
							</c:if>
							<c:if test="${fn:trim(purchase.tranCode) == '4'}">
								��ۿϷ�
							</c:if>
						</td>
					</tr>
					<tr id="${purchase.tranNo}"></tr>
				</c:forEach>
			</tbody>
		</table>
		<!--  table End /////////////////////////////////////-->
	</div>
	<!--  ȭ�鱸�� div End /////////////////////////////////////-->

  <!-- PageNavigation Start... -->
	<jsp:include page="../common/pageNavigator_new.jsp"/>
	<!-- PageNavigation End... -->
</body>
</html>
