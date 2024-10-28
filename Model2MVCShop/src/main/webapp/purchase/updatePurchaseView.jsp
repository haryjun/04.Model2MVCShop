<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<%--
<%@page import="com.model2.mvc.service.domain.Purchase"%>
<% Purchase purchase = (Purchase)request.getAttribute("purchase");%>
 --%>


<html>
<head>
<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript" src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="../javascript/calendar.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" >
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" >
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>
	
	<!-- jQueryCalendar -->
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>	
	
	<!-- Bootstrap Dropdown Hover CSS -->
   <link href="/css/animate.min.css" rel="stylesheet">
   <link href="/css/bootstrap-dropdownhover.min.css" rel="stylesheet">
    <!-- Bootstrap Dropdown Hover JS -->
   <script src="/javascript/bootstrap-dropdownhover.min.js"></script>
   
   
   <!-- jQuery UI toolTip ��� CSS-->
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <!-- jQuery UI toolTip ��� JS-->
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	
	<script type="text/javascript">
		$(function(){
			$('#updatePurchase').on('click', function(){
				$('form').attr("method", "POST").attr("action", "/purchase/updatePurchase?tranNo=${purchase.tranNo}").submit();
			})
			$('td.ct_btn01:contains("���")').on('click', function(){
				history.go(-1);
			})
		})	
	
	
	
	
	</script>
		<style>
 		body {
            padding-top : 50px;
        }
     </style>
<script type="text/javascript">
$(function(){
	$('#divyDate').datepicker({
		dateFormat: 'yy-mm-dd'
	})
})
</script>

<title>�������� ����</title>

<script type="text/javascript" src="../javascript/calendar.js">
</script>

</head>

<body>

	<!-- ToolBar Start /////////////////////////////////////-->
	<jsp:include page="/layout/toolbar.jsp" />
   	<!-- ToolBar End /////////////////////////////////////-->
	
	<!--  ȭ�鱸�� div Start /////////////////////////////////////-->
	<div class="container">
		<div class="page-header text-info">
	       <h3>������������</h3>
	    </div>
		<!-- form Start /////////////////////////////////////-->
		<form class="form-horizontal">
		  <div class="form-group">
		    <label for="prodName" class="col-sm-offset-1 col-sm-3 control-label">��ǰ��ȣ</label>
		    <div class="col-sm-4">
		      <u id="prodName" name="prodNo">${purchase.purchaseProd.prodNo}</u>
		    </div>
		  </div>
		 <div class="form-group">
		    <label for="prodName" class="col-sm-offset-1 col-sm-3 control-label">��ǰ��</label>
		    <div class="col-sm-4">
		      <u id="prodName" name="prodName">${purchase.purchaseProd.prodName}</u>
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="prodDetail" class="col-sm-offset-1 col-sm-3 control-label">��ǰ������</label>
		    <div class="col-sm-4">
		      <u id="prodDetail" name="prodDetail" >${purchase.purchaseProd.prodDetail}</u>
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="manuDate" class="col-sm-offset-1 col-sm-3 control-label">��������</label>
		    <div class="col-sm-4">
		      <u id="manuDate" name="manuDate">${purchase.purchaseProd.manuDate}</u>
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="price" class="col-sm-offset-1 col-sm-3 control-label">���Ű���</label>
		    <div class="col-sm-4">
		      <u id="originalPrice" name="price">${purchase.soldPrice}��</u>
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="paymentOption" class="col-sm-offset-1 col-sm-3 control-label">���Ź��</label>
		    <div class="col-sm-4">
		      <select id="paymentOption" name="paymentOption" class="form-control">
				<option value="1" selected="selected">���ݱ���</option>
				<option value="2">�ſ뱸��</option>
			  </select>
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="receiverPhone" class="col-sm-offset-1 col-sm-3 control-label">����ó</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" name="receiverPhone" value="${purchase.receiverPhone}" />
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="divyAddr" class="col-sm-offset-1 col-sm-3 control-label">�����ּ�</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" name="divyAddr" value="${purchase.divyAddr}" />
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="divyRequest" class="col-sm-offset-1 col-sm-3 control-label">���ſ�û����</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" name="divyRequest" />
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="divyDate" class="col-sm-offset-1 col-sm-3 control-label">����������</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" id="divyDate" name="divyDate" value="${purchase.divyDate}">
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <div class="col-sm-offset-4  col-sm-4 text-center">
		      <button id="updatePurchase" type="button" class="btn btn-primary"  >����</button>
		      <button id="reset" type="button" class="btn btn-primary"  >���</button>
		    </div>
		  </div>
		  
		</form>
		<!-- form end /////////////////////////////////////-->

</div>
</body>
</html>