<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>

<html>
<head>
<meta charset="EUC-KR">
<title>��ǰ �����ȸ</title>

<!-- jQuery autoComplete -->
<link rel="stylesheet" href="/css/admin.css" type="text/css">
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<!-- Bootstrap Dropdown Hover CSS -->
<link href="/css/animate.min.css" rel="stylesheet">
<link href="/css/bootstrap-dropdownhover.min.css" rel="stylesheet">
<!-- Bootstrap Dropdown Hover JS -->
<script src="/javascript/bootstrap-dropdownhover.min.js"></script>


<!-- jQuery UI toolTip ��� CSS-->
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<!-- jQuery UI toolTip ��� JS-->
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<style>
body {
	padding-top: 50px;
}
</style>
                <style>
                    .thumbnail{
                       // min-width: 240px;
                    }
                </style>
<script type="text/javascript">

$(document).ready(function() {
    var userRole = "${user.role}";
    if (userRole === 'admin' || userRole === 'manager') {
        // ��ǰ ���� ��ư Ŭ�� �� �̺�Ʈ ó��
        $(".updateProductButton").on("click", function() {
            var prodNo = $(this).data('prodNo'); // ��ǰ ��ȣ�� data attribute���� ������
            window.location.href = "/product/updateProductView?prodNo=" + prodNo;
        });
    } else {
        $(".updateProductButton").hide();
    }
});


/* $.ajax({
    url: "/product/json/autoCompleteUser",
    method: "POST",
    data: JSON.stringify({
        "searchKeyword": searchKeyword,
        "searchCondition": searchCondition
    }),
    dataType: "json",
    contentType: "application/json",
    success: function(data) {
        $('input[name="searchKeyword"]').autocomplete({
            source: data
        });
    },
    error: function(xhr, textStatus, errorThrown) {
        console.error("Error occurred during auto-completion: " + textStatus + ", " + errorThrown);
    }
}); */


// �˻� Ű���忡 �������ø� ��� �߰�
$('input[name="searchKeyword"]').on("keyup", function() {
    var searchKeyword = $(this).val().trim(); // ���� ����
    var searchCondition = $('select[name=searchCondition]').val();

    if (searchKeyword.length < 2) {
        return; // �ּ� 2���� �̻� �Է� �ÿ��� �˻��ϵ��� ���� �߰�
    }

    $.ajax({
        url: "/product/json/autoCompleteUser",
        method: "POST",
        data: JSON.stringify({
            "searchKeyword": searchKeyword,
            "searchCondition": searchCondition
        }),
        dataType: "json",
        contentType: "application/json",
        success: function(data) {
            if (data && data.length > 0) {
                $('input[name="searchKeyword"]').autocomplete({
                    source: data
                });
            } else {
                console.log("No suggestions found");
                $('input[name="searchKeyword"]').autocomplete({
                    source: []
                });
            }
        },
        error: function(xhr, textStatus, errorThrown) {
            console.error("Error occurred during auto-completion: " + textStatus + ", " + errorThrown);
            console.error("HTTP Status Code: " + xhr.status);
            console.error("Response Text: " + xhr.responseText);
        }
    });
});

	// �˻� / page �ΰ��� ��� ��� Form ������ ���� JavaScrpt �̿�  
	function fncGetUserList(currentPage) {
		$("#currentPage").val(currentPage);
		var uri = "/product/listProduct?menu=${param.menu}";
		uri = $("form").attr("method", "POST").attr("action", uri);
		uri.submit();
	}

	$(function() {
		// �˻���ư ������ �� �̺�Ʈ ó��
		$('button.btn-block:contains("�˻�"),input[value="prodNo"],input[value="priceASC"],input[value="priceDESC"],input[value="recently"]').on("click",
			function() {
				$("#currentPage").val('1');
				if($("#searchKeyword1").val()==''){
					$("#searchKeyword1").val(0)
				}
				if($("#searchKeyword2").val()==''){
					$("#searchKeyword2").val(0)
				}
				var uri = "/product/listProduct?menu=${param.menu}";
				$("form").attr("method", "POST")
						.attr("action", uri).submit();
			});
	});

</script>
</head>

<body>

	<!-- ToolBar Start /////////////////////////////////////-->
	<jsp:include page="/layout/toolbar.jsp" />
	<!-- ToolBar End /////////////////////////////////////-->

<!-- div container start... -->
	<div class="container">

		<div class="page-header text-info">
			<c:if test="${!empty param.menu && param.menu=='manage'}">
				<h3>��ǰ����</h3>
			</c:if>
			<c:if test="${!empty param.menu && param.menu=='search'}">
				<h3>��ǰ ��� ��ȸ</h3>
			</c:if>
		</div>
		<div class="row">
		<form class="form-inline">
			<div class="col-md-3 text-left">
				<p class="text-primary">��ü ${resultPage.totalCount } �Ǽ�, ����
					${resultPage.currentPage} ������</p>
	
					<div class="panel panel-default">
						<div class="panel-heading" style="height: 50px;">
							<h5 align="justify">���ı���</h5>
						</div>
						<div class="panel-body" style="height: 120px; padding: 10">
							<input type=radio name="sorting" value="prodNo"
								${empty search.sorting || (!empty search.sorting && search.sorting=='prodNo') ? "checked" : ""}>��ǰ��ȣ<br>
							<input type=radio name="sorting" value="priceASC"
								${!empty search.sorting && search.sorting=='priceASC' ? "checked" : ""}>���ݳ�����<br>
							<input type=radio name="sorting" value="priceDESC"
								${!empty search.sorting && search.sorting=='priceDESC' ? "checked" : ""}>���ݳ�����<br>
							<input type=radio name="sorting" value="recently"
								${!empty search.sorting && search.sorting=='recently' ? "checked" : ""}>�ֽŵ�ϼ�<br>
						</div>
					</div>
					
					<div class="panel panel-default">
					    <div class="panel-heading">
					        <h3 class="panel-title">��������</h3>
					    </div>
					    <div class="panel-body">
					        <input style="width: 160px" class="form-control inline"
					            type="text" id="searchKeyword1" name="searchKeyword1"
					            value="${search.searchKeyword1 != null ? search.searchKeyword1 : '0'}">��
					        ~ <input style="width: 160px" class="form-control" type="text"
					            name="searchKeyword2" id="searchKeyword2"
					            value="${search.searchKeyword2 != null ? search.searchKeyword2 : '0'}">��
					    </div>
					</div>
										

					<!-- �˻� ���� �г� -->
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">
								<i class="glyphicon glyphicon-asterisk"></i>�˻�����
							</h3>
						</div>
						<div class="panel-body">
							<select name="searchCondition" class="form-control"
								style="width: 160px">
								<option value="0"
									${!empty search.searchCondition && search.searchCondition=='0' ? "selected" : ""}>��ǰ��ȣ</option>
								<option value="1"
									${!empty search.searchCondition && search.searchCondition=='1' ? "selected" : ""}>��ǰ��</option>
								<option value="3"
									${!empty search.searchCondition && search.searchCondition=='3'? "selected" : ""}>�Ǹ���</option>
								<c:if test="${user.role=='admin'}">
								<option value="4"
										${!empty search.searchCondition && search.searchCondition=='4'? "selected" : ""}>���ſϷ�</option>
								<option value="5"
										${!empty search.searchCondition && search.searchCondition=='5'? "selected" : ""}>�����</option>
								<option value="6"
										${!empty search.searchCondition && search.searchCondition=='6'? "selected" : ""}>��ۿϷ�</option>
								</c:if>
							</select> <input type="text" class="form-control" id="searchKeyword" name="searchKeyword"
								placeholder="�˻� Ű���带 �Է��ϼ���." style="width: 230px" value="${!empty search.searchKeyword ? search.searchKeyword : ''}">

						</div>
					</div>
					
					<button type="button" class="btn btn-warning btn-block">�˻�</button>
				</div>
				<input type="hidden" id="currentPage" name="currentPage" value="" />
			</form>

			<div class="col-md-9">
			<div class="panel panel-default">
                        <div class="panel-heading">
                            ��ȸ ���
                        </div>
                        <!--panel-body start--->
                        <div class="panel-body">
				<c:set var="i" value="0" />
				<c:forEach var="product" items="${list}">
                                <div class=" col-md-4">
									<!--thumbnail start--->
                                  <div class="thumbnail">
										<c:if test="${empty product.fileNames && !empty product.fileName}">
											<p><img src="../images/uploadFiles/${product.fileName}" width="200" height="200" /></p>
										 </c:if> 
										 <c:if test="${!empty product.fileNames}">
											<p><img src="../images/uploadFiles/${product.fileNames[0]}" onerror="this.src='http://placehold.it/200x200'" width="200" height="200" /></p>
										 </c:if> 
										 <c:if test="${empty product.fileName}">
											<p><img src="http://placehold.it/200x200" /></p>
										 </c:if>
                                    <div class="caption">
                                      <h3>${product.prodName}</h3>
                                      <p>��ǰ��ȣ: ${product.prodNo}<br>����:${product.price}<br>
                                      	���: ${product.stock} &nbsp;&nbsp;&nbsp;&nbsp;�Ǹŷ�: ${product.quantity-product.stock}</p>
                                      <p>
                                      	<div class="row">
									    <!-- �� ������ ��ũ -->
									    <c:if test="${param.menu != 'manage' || !(user.role == 'admin' || user.role == 'manager')}">
									        <div class="col-md-6 text-right">
									            <a href="/product/getProduct?prodNo=${product.prodNo}&menu=search" class="btn btn-warning btn-block" role="button">��������</a>
									        </div>
									    </c:if>
									    
									    <!-- ��ǰ ���� ���� ��ư -->
									    <c:if test="${param.menu == 'manage' && (user.role == 'admin' || user.role == 'manager')}">
									        <div class="col-md-6 text-left">
									            <button type="button" class="btn btn-warning btn-block updateProductButton" data-prod-no="${product.prodNo}">
									                ��ǰ ���� ����
									            </button>
									        </div>
									    </c:if>
									</div>
									 </p>
                                    </div>
                                  </div>
								  <!--thumbnail end--->
                                </div>
				</c:forEach>
						</div>
						<!--panel-body end--->
					</div>
			</div>
		</div>

	<!-- PageNavigation Start... -->
	<jsp:include page="../common/pageNavigator_new.jsp" />
	<!-- PageNavigation End... -->


	</div>
</body>
</html>
