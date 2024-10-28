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
   
   
   <!-- īī��MapAPI -->
   <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=8e5994c534e4c8fe59b584051261f9bf"></script>
	
	<!--  CSS �߰� : ���ٿ� ȭ�� ������ ���� �ذ� :  �ּ�ó�� ��, �� Ȯ��-->
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
            gap: 20px; /* ���� ���� ������ �����մϴ� */
        }

        .map-wrapper {
            flex: 1; /* �� ������ ������ �ʺ� �������� �����մϴ� */
            max-width: 48%; /* ������ �ִ� �ʺ� 48%�� �����Ͽ� ������ ���� Ȯ�� */
        }
   	</style>
   	
   	
   	
     <!--  ///////////////////////// JavaScript ////////////////////////// -->
    <!-- JavaScript -->
    <script>
        var map1, map2; // ������ ������ ���� ����

        // ���� 1 ����
        function initMap1() {
            var container1 = document.getElementById('map1');
            var options1 = {
                center: new kakao.maps.LatLng(37.5665, 126.9780), // ���� ��û ��ǥ ����
                level: 3
            };
            map1 = new kakao.maps.Map(container1, options1);
        }

        // ���� 2 ���� - �� ��ġ�� �߽����� ����
        function getLocation() {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(function(position) {
                    // ������� ���� ��ġ�� �����ɴϴ�.
                    var lat = position.coords.latitude;
                    var lng = position.coords.longitude;

                    // ���� ��ġ�� �߽����� ���� 2 ����
                    var container2 = document.getElementById('map2');
                    var options2 = {
                        center: new kakao.maps.LatLng(lat, lng),
                        level: 3
                    };
                    map2 = new kakao.maps.Map(container2, options2);

                    // ���� ��ġ�� ��Ŀ�� �߰��մϴ�.
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
                alert('���� ������������ geolocation�� �������� �ʽ��ϴ�');
            }
        }

        // ���� 1�� �߽��� �̵���Ű�� �Լ�
        function setCenter() {
            var moveLatLon = new kakao.maps.LatLng(37.5665, 126.9780); // ���� ��û ��ǥ ����
            map1.setCenter(moveLatLon);
        }

        // ���� 1�� �߽��� �ε巴�� �̵���Ű�� �Լ�
        function panTo() {
            var moveLatLon = new kakao.maps.LatLng(37.5665, 126.9780); // ���� ��û ��ǥ ����
            map1.panTo(moveLatLon);
        }

        // ������ �ε� �� ���� 1�� �ʱ�ȭ�մϴ�.
        $(document).ready(function() {
            initMap1();
        });
    </script>    		
	
</head>
	
<body>

	<!-- ToolBar Start /////////////////////////////////////-->
	<jsp:include page="/layout/toolbar.jsp" />
   	<!-- ToolBar End /////////////////////////////////////-->

	<!--  �Ʒ��� ������ http://getbootstrap.com/getting-started/  ���� -->	
   	<div class="container ">
      <!-- Main jumbotron for a primary marketing message or call to action -->
      <div class="jumbotron">
        <h1>Model2MVCShop </h1>
        <p>J2SE , DBMS ,JDBC , Servlet & JSP, Java Framework , HTML5 , UI Framework �н� �� Mini-Project ����</p>
        <p>chrome.exe --user-data-dir="C:/Chrome dev session" --disable-web-security</p>
     </div>
    </div>

	<!-- ���� : http://getbootstrap.com/css/   : container part..... -->
	<!-- <div class="container">
        <h3>���������� �̷��� ���ߴ�.</h3>
        <p>"���� ���� ������ ������ ���� �߸� ���� �ð��� �����̴�."</p>
  	 	<h3>"... �庮�� �����ϰ� ������ �ʴ� ������� �ɷ������� �����մϴ�. �庮��. ����� �ƴ϶� '�ٸ�' ������� ���߰� �Ϸ��� �ű� �ִ� ��������."</h3>
         <h3>��������</h3>
         <p>�ູ�� ���� �����.</p>
         <p>�����ϴ� ���� �ϴ� ���� �Ƹ���,</p>
         <p>���� �ϴ� ���� �����ϴ� ���Դϴ�.</p>
  	 </div> -->
  	 
  <!-- ������ �¿�� ������ ��ġ�� div -->
    <div class="container">
        <div class="map-container">
            <div class="map-wrapper">
                <div id="map1"></div>
                <p>
                    <button onclick="setCenter()">���� �߽���ǥ �̵���Ű��</button> 
                    <button onclick="panTo()">���� �߽���ǥ �ε巴�� �̵���Ű��</button> 
                </p>
            </div>
            <div class="map-wrapper">
                <div id="map2"></div>
                <p>
                    <button onclick="getLocation()">�� ��ġ�� ���� 2 ǥ���ϱ�</button>
                </p>
            </div>
        </div>
    </div>
</body>

</html>