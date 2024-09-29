
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%--
<%@page import="com.model2.mvc.service.domain.Product"%>
<%
	Product product = (Product)request.getAttribute("product");
	//System.out.println("addProduct.jsp : "+product);
%>
--%>
<html lang="ko">
<head>
<meta charset="EUC-KR">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="/css/admin.css" type="text/css">
<script type="text/javascript" src="http://code.jquery.com/jquery-2.1.4.min.js"></script>

	<style>
 		body {
            padding-top : 50px;
        }
     </style>
    
<title>��ǰ���</title>


<script type="text/javascript">
	$(function() {
		$('#addMore').on("click", function() {
			self.location = "/product/addProduct";
		})

		$('button[id!="addMore"]').on("click", function() {
			self.location = "/product/listProduct?menu=manage";
		})
	});
</script>

</head>

<body>
	<!-- ToolBar Start /////////////////////////////////////-->
	<jsp:include page="/layout/toolbar.jsp" />
   	<!-- ToolBar End /////////////////////////////////////-->
	<div class="container">
	
		<div class="page-header">
	       <h3 class=" text-info">��ǰ���</h3>
	       <h5 class="text-muted">��ǰ�� ��ϵǾ����ϴ�.</h5>
	    </div>
	
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>��ǰ��</strong></div>
			<div class="col-xs-8 col-md-4">${product.prodName}</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>��ǰ������</strong></div>
			<div class="col-xs-8 col-md-4">${product.prodDetail}</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>��������</strong></div>
			<div class="col-xs-8 col-md-4">${product.manuDate}</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>����</strong></div>
			<div class="col-xs-8 col-md-4">${product.price}��</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>��ǰ�̹���</strong></div>
			<div class="col-xs-8 col-md-4">	
					<c:if test="${product.fileNames==null}">
						<img src="../images/uploadFiles/${product.fileName}" width="300"/>
					</c:if>
					<c:if test="${product.fileNames!=null}">
						<c:forEach var="i" items="${product.fileNames}">
							<img src="../images/uploadFiles/${i}" width="300"/><br/>
						</c:forEach>
					</c:if>
			</div>
		</div>
		
		<hr/>
		

		<div class="row">
	  		<div class="col-md-12 text-center ">
	  			<button id="addMore" type="button" class="btn btn-primary">�߰����</button>
	  		</div>
	  		<div class="col-md-12 text-right ">
	  			<button type="button" class="btn btn-primary">Ȯ��</button>
	  		</div>
		</div>
		
		<br/>
		
 	</div>
 	<!--  ȭ�鱸�� div Start /////////////////////////////////////-->

</body>
</html>