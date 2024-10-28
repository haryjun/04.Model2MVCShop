<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page pageEncoding="EUC-KR"%>

<!--  ///////////////////////// JSTL  ////////////////////////// -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="ko">
	
<head>
	<meta charset="EUC-KR">
	
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
	<!--   jQuery , Bootstrap CDN  -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" >
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" >
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>
	
	<!-- Bootstrap Dropdown Hover CSS -->
   <link href="/css/animate.min.css" rel="stylesheet">
   <link href="/css/bootstrap-dropdownhover.min.css" rel="stylesheet">
   
    <!-- Bootstrap Dropdown Hover JS -->
   <script src="/javascript/bootstrap-dropdownhover.min.js"></script>
   
   
   <!-- 카카오MapAPI -->
   <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=8e5994c534e4c8fe59b584051261f9bf"></script>
	
	<!--  CSS 추가 : 툴바에 화면 가리는 현상 해결 :  주석처리 전, 후 확인-->
	<style>
        body {
            padding-top : 70px;
        }
        
        #map1, #map2 {
            width: 100%;
            height: 400px;
            margin-bottom: 20px;
        }

        .map-container {
            display: flex;
            justify-content: space-between;
            gap: 20px; /* 지도 간의 간격을 설정합니다 */
        }

        .map-wrapper {
            flex: 1; /* 두 지도가 동일한 너비를 가지도록 설정합니다 */
            max-width: 48%; /* 지도의 최대 너비를 48%로 설정하여 적절한 공간 확보 */
        }
   	</style>
   	
   	
   	
     <!--  ///////////////////////// JavaScript ////////////////////////// -->
    <!-- JavaScript -->
    <script>
        var map1, map2; // 지도를 저장할 변수 선언

        // 지도 1 생성
        function initMap1() {
            var container1 = document.getElementById('map1');
            var options1 = {
                center: new kakao.maps.LatLng(37.5665, 126.9780), // 서울 시청 좌표 예시
                level: 3
            };
            map1 = new kakao.maps.Map(container1, options1);
        }

        // 지도 2 생성 - 내 위치를 중심으로 생성
        function getLocation() {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(function(position) {
                    // 사용자의 현재 위치를 가져옵니다.
                    var lat = position.coords.latitude;
                    var lng = position.coords.longitude;

                    // 현재 위치를 중심으로 지도 2 생성
                    var container2 = document.getElementById('map2');
                    var options2 = {
                        center: new kakao.maps.LatLng(lat, lng),
                        level: 3
                    };
                    map2 = new kakao.maps.Map(container2, options2);

                    // 현재 위치에 마커를 추가합니다.
                    var markerPosition = new kakao.maps.LatLng(lat, lng);
                    var marker = new kakao.maps.Marker({
                        position: markerPosition
                    });
                    marker.setMap(map2);

                }, function(error) {
                    console.error(error);
                }, {
                    enableHighAccuracy: false,
                    maximumAge: 0,
                    timeout: Infinity
                });
            } else {
                alert('현재 브라우저에서는 geolocation을 지원하지 않습니다');
            }
        }

        // 지도 1의 중심을 이동시키는 함수
        function setCenter() {
            var moveLatLon = new kakao.maps.LatLng(37.5665, 126.9780); // 서울 시청 좌표 예시
            map1.setCenter(moveLatLon);
        }

        // 지도 1의 중심을 부드럽게 이동시키는 함수
        function panTo() {
            var moveLatLon = new kakao.maps.LatLng(37.5665, 126.9780); // 서울 시청 좌표 예시
            map1.panTo(moveLatLon);
        }

        // 페이지 로딩 시 지도 1을 초기화합니다.
        $(document).ready(function() {
            initMap1();
        });
    </script>    		
	
</head>
	
<body>

	<!-- ToolBar Start /////////////////////////////////////-->
	<jsp:include page="/layout/toolbar.jsp" />
   	<!-- ToolBar End /////////////////////////////////////-->

	<!--  아래의 내용은 http://getbootstrap.com/getting-started/  참조 -->	
   	<div class="container ">
      <!-- Main jumbotron for a primary marketing message or call to action -->
      <div class="jumbotron">
        <h1>Model2MVCShop </h1>
        <p>J2SE , DBMS ,JDBC , Servlet & JSP, Java Framework , HTML5 , UI Framework 학습 후 Mini-Project 진행</p>
        <p>chrome.exe --user-data-dir="C:/Chrome dev session" --disable-web-security</p>
     </div>
    </div>

	<!-- 참조 : http://getbootstrap.com/css/   : container part..... -->
	<!-- <div class="container">
        <h3>나폴레옹은 이렇게 말했다.</h3>
        <p>"오늘 나의 불행은 언젠가 내가 잘못 보낸 시간의 보복이다."</p>
  	 	<h3>"... 장벽은 절실하게 원하지 않는 사람들을 걸러내려고 존재합니다. 장벽은. 당신이 아니라 '다른' 사람들을 멈추게 하려고 거기 있는 것이지요."</h3>
         <h3>혜광스님</h3>
         <p>행복한 삶의 비결은.</p>
         <p>좋아하는 일을 하는 것이 아리라,</p>
         <p>지금 하는 일을 좋아하는 것입니다.</p>
  	 </div> -->
  	 
  <!-- 지도를 좌우로 나란히 배치할 div -->
    <div class="container">
        <div class="map-container">
            <div class="map-wrapper">
                <div id="map1"></div>
                <p>
                    <button onclick="setCenter()">지도 중심좌표 이동시키기</button> 
                    <button onclick="panTo()">지도 중심좌표 부드럽게 이동시키기</button> 
                </p>
            </div>
            <div class="map-wrapper">
                <div id="map2"></div>
                <p>
                    <button onclick="getLocation()">내 위치로 지도 2 표시하기</button>
                </p>
            </div>
        </div>
    </div>
</body>

</html>