<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function gutstbook1_go() {
		location.href="gb1_list.do";
	}
	function gutstbook2_go() {
		location.href="gb2_list.do";
	}
</script>
</head>
<body>
	<div>
		<h2>Guestbook2 메인 페이지</h2>
		<button onclick="gutstbook1_go()">guestbook1 이동</button>
		<button onclick="guestbook2_go()">guestbook2 이동</button>
	</div>
</body>
</html>