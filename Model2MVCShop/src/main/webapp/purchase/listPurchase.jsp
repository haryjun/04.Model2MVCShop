<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<%--
<%@page import="com.model2.mvc.common.util.CommonUtil"%>
<%@page import="com.model2.mvc.common.Search"%>
<%@page import="com.model2.mvc.common.Page"%>
<%@page import="com.model2.mvc.service.domain.Purchase"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%
	List<Purchase> list= (List<Purchase>)request.getAttribute("list");
	Page resultPage=(Page)request.getAttribute("resultPage");
	
	Search search = (Search)request.getAttribute("search");
	//==> null �� ""(nullString)���� ����
	String searchCondition = CommonUtil.null2str(search.getSearchCondition());
	String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
%>
--%>

<html>
<head>
<title>���� �����ȸ</title>

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
    </style>
    
<script type="text/javascript" src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript">
	function fncGetUserList(currentPage) {
		$("#currentPage").val(currentPage);
	   	$('form[name=detailForm]').attr("method", "POST").attr("action", "/purchase/listPurchase").submit();		
	}
	
	function deletePurchase(tranNo){
		if(confirm('���� ����Ͻðڽ��ϱ�? \n����� ���������� ������ �� �����ϴ�.')){
			self.location="/purchase/deletePurchase?tranNo="+tranNo+""
		}else{
			return;
		}
	}
	
	$(function(){
		$('tr td[title]').on("click", function(){
			//alert("");
			var tranNo = $($(this).next('td').html()).val();
			//location="/purchase/getPurchase?tranNo="+tranNo;
			//alert(tranNo);
			//console.log($("#"+tranNo+"").html())
			if($( "#"+tranNo+"" ).html().length!=0){
				$( "#"+tranNo+"" ).empty();
			}else{$.ajax({
				url:"/purchase/json/getPurchase/"+tranNo,
				method:"POST",
				dataType:"json",
				headers:{
					"Accept" : "application/json",
					"Content-Type":"application/json"
				},
				success: function(JSONData, status){
					//alert(status);
					//alert("JSONData : \n"+JSONData);
				
					var displayValue= "<td></td><td colspan='7'><h5>"+"�ֹ���: "+JSONData.orderDate+"<br>"
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
					if(JSONData.tranCode=='2  '){
						displayValue += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:deletePurchase("+JSONData.tranNo+")'>"+JSONData.purchaseProd.prodName+" ���� ���</a>";
						displayValue += "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='/purchase/updatePurchase?tranNo="+JSONData.tranNo+"'>������������</a>";
					}
					displayValue += "</td></h5>"
					$("#"+tranNo+"").html(displayValue);

				},
				error:function(jqXHR){
					alert("error : "+jqXHR.status);
				}
			})
			}
		})
		
	
		
		
		$('u:contains("���ǵ���")').on("click", function(){
			var tranNo = $($('tbody tr td:nth-child(3) input')[$('u').index(this)]).val();
			console.log($('u').index(this));
			console.log(tranNo);
			location="/purchase/updateTranCode?tranNo="+tranNo+"&tranCode=4";
		})
	})
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

	
	<!-- ToolBar Start /////////////////////////////////////-->
	<jsp:include page="/layout/toolbar.jsp" />
   	<!-- ToolBar End /////////////////////////////////////-->
			<form>
      <input type="hidden" id="currentPage" name="currentPage" value=""/>
      </form>
	<!--  ȭ�鱸�� div Start /////////////////////////////////////-->
	<div class="container">
	
		<div class="page-header text-info">
	       <h3>���Ÿ����ȸ</h3>
	    </div>
	    
      <!--  table Start /////////////////////////////////////-->
      <table class="table table-hover table-striped" >
      
        <thead>
          <tr>
            <th align="center">No</th>
            <th align="left" >��ǰ��</th><th></th><th></th>
            <th align="left">�ֹ�����</th>
            <th align="left">��û����</th>
            <th align="left">�����Ȳ</th>
			<th align="left">����Ȯ��</th>
          </tr>
        </thead>
       
		<tbody>
		
		  <c:set var="i" value="0" />
		  <c:forEach var="purchase" items="${list}">
			<c:set var="i" value="${ i+1 }" />
			<tr>
			  <td align="center">${ i }</td>
			  <td align="left" title="Click : �������� Ȯ��">${purchase.purchaseProd.prodName}</td>
			  <td><input type="hidden" value="${purchase.tranNo}"><td>
			  <td align="left">${purchase.orderDate}</td>
			  <td align="left">${purchase.divyRequest}</td>
			  <td align="left">
			  	����
				
				<c:choose>
				<c:when test="${purchase.tranCode=='2  '}">
				���� �Ϸ�
				</c:when>
				<c:when test="${purchase.tranCode=='3  '}">
				�����
				</c:when>
				<c:when test="${purchase.tranCode=='4  '}">
				��� �Ϸ�
				</c:when>
			</c:choose>
				���� �Դϴ�.
			  </td>
			  <td align="left"><u>
			  <c:if test="${purchase.tranCode=='3  '}">
				���ǵ���
				</c:if>
			</u>
			<c:if test="${purchase.tranCode=='4  '}">
				��ۿϷ�
			</c:if></td>
			</tr>
			 <tr id="${purchase.tranNo}"></tr>
			 <tr></tr>
          </c:forEach>
        
        </tbody>

	  </table>
	  <!--  table End /////////////////////////////////////-->
	  
 	</div>
 	<!--  ȭ�鱸�� div End /////////////////////////////////////-->
 	
 	
 	<!-- PageNavigation Start... -->
	<jsp:include page="../common/pageNavigator_new.jsp" />
	<!-- PageNavigation End... -->
	
</body>

	

</html>