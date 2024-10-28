<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page pageEncoding="EUC-KR"%>

<!--  ///////////////////////// JSTL  ////////////////////////// -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!-- ToolBar Start /////////////////////////////////////-->
	<head>  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>

<div class="navbar  navbar-inverse navbar-fixed-top">
	
	<div class="container">
	       
		<a class="navbar-brand" href="/index.jsp">Model2 MVC Shop</a>
		
		<!-- toolBar Button Start //////////////////////// -->
		<div class="navbar-header">
		    <button class="navbar-toggle collapsed" data-toggle="collapse" data-target="#target">
		        <span class="sr-only">Toggle navigation</span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		    </button>
		</div>
		<!-- toolBar Button End //////////////////////// -->
		
	    <!--  dropdown hover Start -->
		<div 	class="collapse navbar-collapse" id="target" 
	       			data-hover="dropdown" data-animations="fadeInDownNew fadeInRightNew fadeInUpNew fadeInLeftNew">
	         
	         	<!-- Tool Bar 를 다양하게 사용하면.... -->
	             <ul class="nav navbar-nav">
	             
	              <!--  회원관리 DrowDown -->
	              <li class="dropdown">
	                     <a  href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
	                         <span >회원관리</span>
	                         <span class="caret"></span>
	                     </a>
	                     <ul class="dropdown-menu">
	                         <li><a href="#">개인정보조회</a></li>
	                         
	                         <c:if test="${sessionScope.user.role == 'admin'}">
	                         	<li><a href="#">회원정보조회</a></li>
	                         </c:if>
	                         
	                         <li class="divider"></li>
	                         <li><a href="#">etc...</a></li>
	                     </ul>
	                 </li>
	                 
	              <!-- 판매상품관리 DrowDown  -->
	               <c:if test="${sessionScope.user.role == 'admin'}">
		              <li class="dropdown">
		                     <a  href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
		                         <span >판매상품관리</span>
		                         <span class="caret"></span>
		                     </a>
		                     <ul class="dropdown-menu">
		                         <li><a href="#">판매상품등록</a></li>
		                         <li><a href="#">판매상품관리</a></li>
		                         <li class="divider"></li>
		                         <li><a href="#">구매목록조회</a></li>
		                     </ul>
		                </li>
	                 </c:if>
	                 
	              <!-- 구매관리 DrowDown -->
	              <li class="dropdown">
	                     <a  href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
	                         <span >상품구매</span>
	                         <span class="caret"></span>
	                     </a>
	                     <ul class="dropdown-menu">
	                         <li><a href="#">상 품 검 색</a></li>
	                         
	                         <c:if test="${sessionScope.user.role == 'user'}">
	                           <li><a href="#">구매이력조회</a></li>
	                         </c:if>
	                         
	                         <li><a href="#">최근본상품</a></li>
	                         <li class="divider"></li>
	                         <li><a href="#">etc..</a></li>
	                     </ul>
	                 </li>
	                 
	                 <li><a href="#">쿠폰등록하기</a></li>
	             </ul>
	             
	             <ul class="nav navbar-nav navbar-right">
	                <li><a href="#">로그아웃</a></li>
	            </ul>
		</div>
		<!-- dropdown hover END -->	       
	    
	</div>
<!-- history dialog start....-->
	<div id="dialog" title="최근 본 상품">
  <p>
  <%
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
%>
<a href="/product/getProduct?prodNo=<%=h[i]%>&menu=search"
	><%=h[i]%></a>
<br>
<%
				}
			}
		}
	}
%>
  
  </p>
</div> 	
<!-- history dialog end....-->
<!-- coupon dialog start... -->
<div id="coupon" title="쿠폰 등록하기">
<!-- coupon dialog start... -->
<p>
	쿠폰 번호를 입력하세요.
<br>
<br>
<c:if test="${user.coupon!=null && user.coupon.discountCoupon10=='1'}">
<h5>쿠폰이 발급되었습니다.<br> 중복 발급되지 않습니다.<br><br></h5>
</c:if>
<c:if test="${user.coupon==null}">
<h5>등록된 쿠폰이 없습니다.<br> 쿠폰 번호를 입력해주세요.<br><br></h5>
<input type="text" name="couponId">
</c:if>
<c:if test="${user.coupon!=null && user.coupon.discountCoupon10!='1'}">
<h5>이미 쿠폰을 사용하셨습니다.<br> 중복 발급되지 않습니다.<br><br></h5>
</c:if>


</p>
</div>
<!-- coupon dialog end... -->
</div>
		<!-- ToolBar End /////////////////////////////////////-->

   	
   	
   	<script type="text/javascript">
	
		//============= logout Event  처리 =============	
		 $(function() {
			//==> DOM Object GET 3가지 방법 ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
		 	$("a:contains('로그아웃')").on("click" , function() {
				$(self.location).attr("href","/user/logout");
				//self.location = "/user/logout"
			}); 
		 });
		
		//============= 회원정보조회 Event  처리 =============	
		 $(function() {
			//==> DOM Object GET 3가지 방법 ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
		 	$("a:contains('회원정보조회')").on("click" , function() {
				//$(self.location).attr("href","/user/logout");
				self.location = "/user/listUser"
			}); 
		 });
		
		//=============  개인정보조회회 Event  처리 =============	
	 	$( "a:contains('개인정보조회')" ).on("click" , function() {
	 		//==> DOM Object GET 3가지 방법 ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
			$(self.location).attr("href","/user/getUser?userId=${sessionScope.user.userId}");
		});
		
		//============= 구매이력조회 Event  처리 =============	
		 $(function() {
			//==> DOM Object GET 3가지 방법 ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
		 	$("a:contains('구매이력조회')").on("click" , function() {
				$(self.location).attr("href","/purchase/listPurchase");
				//self.location = "/user/logout"
			}); 
		 });
		
			//============= 배송관리 Event  처리 =============	
		 $(function() {
			//==> DOM Object GET 3가지 방법 ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
		 	$("a:contains('구매목록조회')").on("click" , function() {
				$(self.location).attr("href","/purchase/listPurchase");
				//self.location = "/user/logout"
			}); 
		 });
		
			//============= 상 품 검 색 Event  처리 =============	
		 $(function() {
			//==> DOM Object GET 3가지 방법 ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
		 	$("a:contains('상 품 검 색')").on("click" , function() {
				$(self.location).attr("href","/product/listProduct?menu=search");
				//self.location = "/user/logout"
			}); 
		 });
		 
			//============= 판매상품등록 Event  처리 =============	
		 $(function() {
			//==> DOM Object GET 3가지 방법 ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
		 	$("a:contains('판매상품등록')").on("click" , function() {
				$(self.location).attr("href","/product/addProduct");
				//self.location = "/user/logout"
			}); 
		 });
		 
			//============= 판매상품관리 Event  처리 =============	
		 $(function() {
			//==> DOM Object GET 3가지 방법 ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
		 	$("a:contains('판매상품관리')").on("click" , function() {
				$(self.location).attr("href","/product/listProduct?menu=manage");
				//self.location = "/user/logout"
			}); 
		 });
			
		
			//============= 최근본상품 Event  처리 =============	
		$(function(){
			$("#dialog").dialog({
				autoOpen:false
			})
		})
		
		$(function() {
			//==> DOM Object GET 3가지 방법 ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
		 	$("a:contains('최근본상품')").on("click" , function() {
		 		$( "#dialog" ).dialog( "open" );
		 		//self.location = "/user/logout"
			}); 
			console.log("열림");
		 });
			
		//============= coupon Event  처리 =============	
 		$(function(){
			$("#coupon").dialog({
				autoOpen:false,
				buttons:{
					"등록" : function(){
							var inputCouponId = $('input[name="couponId"]').val();
							console.log($('input[name="couponId"]').val())

							// URL 인코딩 -> 한글입력하면 자꾸 에러남
               				 var encodedCouponId = encodeURIComponent(inputCouponId);
							
							$.ajax({
								url : "/json/addCoupon?couponId="+ encodedCouponId,
								//url : "/user/json/addCoupon?couponId="+inputCouponId+"",
								method : "GET",
								dataType : "json",
								headers : {
									"Accept" : "application/json",
									"Content-Type" : "application/json" /*  GET에는 필요없다하는디..*/
								},
								success : function(JSONData, status){
									//alert(status);
									//alert("JSONData : \n"+JSONData.message);
									//alert( "JSON.stringify(JSONData) : \n"+JSON.stringify(JSONData) );
									//alert( JSONData != null );
									if(JSONData.message=="ok"){
										//alert("사용 가능한 아이디입니다.")
										$('h5').html("쿠폰이 발급되었습니다.<br> 중복 발급되지 않습니다.<br><br>");
										$('input').remove()
									}else if(JSONData.message=="no good"){
										$('h5').html("알맞지 않은 쿠폰 번호입니다.<br> 다시 입력해주세요.<br><br>");
									}else if(JSONData.message=="already"){
										$('h5').html("다시 쿠폰을 등록하실 수 없습니다.<br> 쿠폰이 업데이트되면 알려드릴게요!<br><br>");
									}
								},
								error : function(jqXHR, textStatus, errorThrown){
									alert("error: "+textStatus)
									alert(jqXHR.status)
									alert(errorThrown)
								}
							})
						}
				}
			})
		})
		
		$(function() {
			//==> DOM Object GET 3가지 방법 ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
		 	$("a:contains('쿠폰등록하기')").on("click" , function() {
		 		$( "#coupon" ).dialog( "open" );
		 		//self.location = "/user/logout"
			}); 
		 }); 
	</script>  