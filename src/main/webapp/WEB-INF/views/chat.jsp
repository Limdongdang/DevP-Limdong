<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page session="true"%>
<html lang="en" xmlns:th="http://www.thymeleaf.org"
	xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.6.1/sockjs.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<%@include file="sidebar.jsp"%>
<style>
.my-message {
	color: blue; /* 내 메시지 스타일 */
}

.other-message {
	color: green; /* 상대방 메시지 스타일 */
}
.chat-container{
	max-height : 300px;
	overflow-y : auto;
}
</style>
<head>
<title>1대1 채팅</title>
</head>
<body>
	<div class="container">
		<div class="issue-wrapper">
			<div class="mt-5">
				<h1 class="mb-4">${project.projectName}</h1>
				<p class="h3">${receiver.name}</p>
				<div class="d-flex"></div>
				<div class="card text-bg-light mb-3">
					<div class="card-body chat-container" style="min-height: 30vh">
						<!-- 채팅 메시지 목록을 나타내는 부분 -->
						<ul>
							<c:forEach items="${messageHistoryList}" var="message">
								<li
									class="${message.sender eq id ? 'my-message' : 'other-message'}">
									<strong>${message.sender}:</strong> ${message.content}
								</li>
							</c:forEach>
						</ul>

					</div>
				</div>
				<div class="card text-bg-light p-2">
					<div class="form-floating">
						<textarea class="form-control"
							placeholder="Leave a comment here" id="msg" style="height: 10vh"></textarea>
						<label for="floatingcontent">댓글</label>
					</div>
					<div>
						<button type="submit" id="chatbutton" class="btn btn-primary m-3"
							style="float: right">등록</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<th:block
		th:replace="~{/layout/basic :: setContent~{this :: content})}">
		<th:block th:fragment="content">


		</th:block>
	</th:block>
	<script th:inline="javascript">
		var socket = null;
		var isStomp = false;
		const urlParams = new URL(location.href).searchParams;
		const chatRoomId = "${ chatRoomId }"
		const receiveId = urlParams.get('userId');
		const senderId = "${ id }"
		$(document).ready(function() {
			connectStomp()
			$('#chatbutton').on('click', function(evt) {
				evt.preventDefault();
				if (!isStomp && socket.readyState !== 1)
					return;

				let msg = $('textarea#msg').val();
				console.log("message", msg);
				if (isStomp)
					socket.send('/sendMessage', {}, JSON.stringify({
						chatId : chatRoomId,
						sender : senderId,
						receiver : receiveId,
						content : msg
					}));
				$('textarea#msg').val('');
			});
		});

		function connectStomp() {
			sock = new SockJS("/stomp"); // endpoint
			client = Stomp.over(sock);
			isStomp = true;
			socket = client;
			client.connect({}, function() {
				console.log("stomp 연결 성공");
				// 컨트롤러 메세지 매핑, header, message(자유 형식)

				//토픽 구독
				client.subscribe('/topic/' + senderId, function(event) {
					console.log("!!!!!!!!!!event>>", event)
				});
			});
		}

		var $chatListContainer = $(".chat-container");

		// 스크롤을 가장 아래로 이동
		$chatListContainer.scrollTop($chatListContainer[0].scrollHeight);
	</script>
</body>
</html>
