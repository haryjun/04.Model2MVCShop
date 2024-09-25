<%@ page contentType="text/html; charset=euc-kr"%>
<%@ page pageEncoding="EUC-KR"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- <c:set var="list" value="${result.list}" />
<c:set var="resultPage" value="${result}" /> --%>

<!DOCTYPE html>
<html>
<head>
<title>��ǰ �����ȸ</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
 
<script type="text/javascript">
    function fncGetUserList(currentPage) {
        $("#currentPage").val(currentPage);
        var url = "/product/listProduct?menu=${param.menu}";
        $("form").attr("method", "POST").attr("action", url).submit();
    }

    $(function() {
        $("td.ct_btn01:contains('�˻�')").on("click", function() {		 
            fncGetUserList(1);
        });

        $(".ct_list_pop td:nth-child(3)").on("click", function() {
            var prodNo = $(this).text().trim();
            $.ajax({
                url: "/product/json/getProduct/" + prodNo,
                method: "GET",
                dataType: "json",
                headers: {
                    "Accept": "application/json",
                    "Content-Type": "application/json"
                },
                success: function(JSONData) {
                    var displayValue = "<h3>"
                        + "��ǰ ��ȣ : " + JSONData.prodNo + "<br/>"
                        + "��ǰ �� : " + JSONData.prodName + "<br/>"
                        + "�� ���� : " + JSONData.prodDetail + "<br/>"
                        + "���� : " + JSONData.price + "<br/>"
                        + "���� ���� : " + JSONData.manuDate + "<br/>"
                        + "��� �� : " + JSONData.regDate + "<br/>"
                        + "</h3>";
                    $("#" + prodNo).html(displayValue);
                },
                error: function(jqXHR) {
                    alert(prodNo);
                    alert(jqXHR.status);
                }
            });
        });

        $(".ct_list_pop td:nth-child(3)").css("color", "red");
        $("h6").css("color", "red");
        $(".ct_list_pop:nth-child(4n+6)").css("background-color", "whitesmoke");
    });
</script>

</head>

<body bgcolor="#ffffff" text="#000000">
	
	<div style="width:98%; margin-left:10px;">
	
	<form>
	
	<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
		<tr>
			<td width="15" height="37">
				<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
			</td>
			<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="93%" class="ct_ttl01">
						 <c:if test="${!empty param.menu && param.menu=='manage'}">
						 	��ǰ����
						 </c:if>
						 <c:if test="${!empty param.menu && param.menu=='search'}">
						 	��ǰ ��� ��ȸ
						 </c:if>
						</td>
					</tr>
				</table>
			</td>
			<td width="12" height="37">
				<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
			</td>
		</tr>
	</table>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
		<tr>
			<td align="right">
					(��ǰ ���� �˻�? <input type="checkbox" name="priceSearch" ${search.priceSearch ? "checked" : ""}> 
			<input 	type="text" name="searchKeyword1" value="${!empty search.searchKeyword1 ? search.searchKeyword1 : ""}" 
			
								class="ct_input_g" style="width:100px; height:19px" >
								�� ~
			<input 	type="text" name="searchKeyword2" value="${!empty search.searchKeyword2 ? search.searchKeyword2 : ""}" 
			
								class="ct_input_g" style="width:100px; height:19px" >��
								)&nbsp;
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0" ${!empty search.searchCondition && search.searchCondition=='0' ? "selected" : ""}>��ǰ��ȣ</option>
				<option value="1" ${!empty search.searchCondition && search.searchCondition=='1' ? "selected" : ""}>��ǰ��</option>
				<%--<option value="2" ${!empty search.searchCondition && search.searchCondition=='2' ? "selected" : ""}>��ǰ����</option> --%>
				<option value="3" ${!empty search.searchCondition && search.searchCondition=='3'? "selected" : ""}>�Ǹ���</option>
				<c:if test="${user.role=='admin'}">
				<option value="4" ${!empty search.searchCondition && search.searchCondition=='4'? "selected" : ""}>���ſϷ�</option>
				<option value="5" ${!empty search.searchCondition && search.searchCondition=='5'? "selected" : ""}>�����</option>
				<option value="6" ${!empty search.searchCondition && search.searchCondition=='6'? "selected" : ""}>��ۿϷ�</option>
				</c:if>
			</select>
			<input 	type="text" name="searchKeyword" value="${!empty search.searchKeyword ? search.searchKeyword : ""}" 
			
								class="ct_input_g" style="width:200px; height:19px" >
	
		</td>
			<td align="right" width="70">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="17" height="23">
							<img src="/images/ct_btnbg01.gif" width="17" height="23">
						</td>
						<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
							�˻�
						</td>
						<td width="14" height="23">
							<img src="/images/ct_btnbg03.gif" width="14" height="23">
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
	
		
	<td align="right">
			&nbsp;&nbsp;&nbsp;&nbsp;(���ı���
			<input type=radio name="sorting" value="prodNo" ${empty search.sorting || (!empty search.sorting && search.sorting=='prodNo') ? "checked" : ""}>��ǰ��ȣ
			<input type=radio name="sorting" value="priceASC" ${!empty search.sorting && search.sorting=='priceASC' ? "checked" : ""}>���ݳ�����
			<input type=radio name="sorting" value="priceDESC" ${!empty search.sorting && search.sorting=='priceDESC' ? "checked" : ""}>���ݳ�����
			)</td>
		</tr>
	</table>
	
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
		<tr>
			<td colspan="11" >��ü  ${resultPage.totalCount } �Ǽ�, ���� ${resultPage.currentPage } ������</td>
		</tr>
		<tr>
			<td class="ct_list_b" width="100">No</td>
			<td class="ct_line02"></td>
			<td class="ct_list_b" width="150">��ǰ��
			<h6>(Ŭ���û󼼺���)</h6></td>
			<td class="ct_line02"></td>
			<td class="ct_list_b" width="150">����</td>
			<td class="ct_line02"></td>
			<td class="ct_list_b">��ǰ ��ȣ</td>	
			<td class="ct_line02"></td>
			<td class="ct_list_b">�������</td>
			<td class="ct_line02"></td>	
			<td class="ct_list_b">��ǰ �̹���</td>
		</tr>
		<tr>
			<td colspan="11" bgcolor="808285" height="1"></td>
		</tr>
		
	<c:set var="i" value="0" />
	<c:forEach items="${list}" var="product">
		<tr class="ct_list_pop">
			<td align="center">${i+1}</td>
	<c:set var="i" value="${i+1}"/>
			<td></td>
					<td align="left"><h6>${product.prodName}</h6></td>
			
			<td></td>
			<td align="left">${product.price}</td>
			<td></td>
			<td align="left">${product.prodNo}</td>
			<td></td>
			<td align="left" id="tranStaus">
				<c:choose>
					<c:when test="${product.proTranCode=='1'}">
					�Ǹ���
					</c:when>
					<c:when test="${product.proTranCode=='2'&& user.role=='admin'}">
					���� �Ϸ�
						<c:if test="${param.menu=='manage'}">
							<span id="deliver">����ϱ�</span>
							<span><input type="hidden" value=${product.prodNo}></span>
						</c:if>
					</c:when>
					<c:when test="${product.proTranCode=='3'&& user.role=='admin'}">
					�����
					</c:when>
					<c:when test="${product.proTranCode=='4'&& user.role=='admin'}">
					��� �Ϸ�
					</c:when>
					<c:otherwise>
					��� ����
					</c:otherwise>
				</c:choose>
			</td>	
			<td></td>
			<td align="center">
				<c:if test="${!empty product.fileName}">
					<p><img src="../images/uploadFiles/${product.fileName}" width="100" height="100"/></p><%--${product.fileName} --%>
				</c:if>
				<c:if test="${empty product.fileName}">
					<p><img src="http://placehold.it/100x100"/> </p><%--${product.fileName} --%>
				</c:if>
			</td>
		</tr>
		<tr>
			<td id="${product.prodNo}" colspan="11" height="1"></td>
		</tr>	
	</c:forEach>
	
	</table>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
		<tr>
			<td align="center">
			<input type="hidden" id="currentPage" name="currentPage" value=""/>
			<jsp:include page="../common/pageNavigator.jsp"/>	
	    	</td>
		</tr>
	</table>
	<!--  ������ Navigator �� -->
	
	</form>