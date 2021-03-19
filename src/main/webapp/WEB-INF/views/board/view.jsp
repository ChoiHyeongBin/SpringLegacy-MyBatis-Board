<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 조회</title>
</head>
<body>

<div id="nav">
	<%@ include file="../include/nav.jsp" %>
</div>

<h2>${view.title}</h2>

<hr />
<div class="writer">
	<span>작성자 : </span>${view.writer}
</div>

<hr />

<div class="content">
	${view.content}
</div>

<hr />

<div>
	<a href="/board/modify?bno=${view.bno}">게시물 수정</a>, <a href="/board/delete?bno=${view.bno}">게시물 삭제</a>
</div>

<!-- 댓글 시작 -->

<hr />

<ul>
<!-- 
	<li>
		<div>
			<p>첫번째 댓글 작성자</p>
			<p>첫번째 댓글</p>
		</div>
	</li>
	<li>
		<div>
			<p>두번째 댓글 작성자</p>
			<p>두번째 댓글</p>
		</div>
	</li>
	<li>
		<div>
			<p>세번째 댓글 작성자</p>
			<p>세번째 댓글</p>
		</div>
	</li>
 -->
 
	 <c:forEach items="${reply}" var="reply">
	<li>
	    <div>
	        <p>${reply.reply_writer} / <fmt:formatDate value="${reply.regDate}" pattern="yyyy-MM-dd" /></p>	<!-- reply_writer 로 안하면 어떻게 될까? -->
	        <p>${reply.content}</p> <button onclick="location.href='/reply/delete?bno=${reply.bno}&rno=${reply.rno}'">삭제</button>
	    </div>
	</li>    
	</c:forEach>
</ul>

<div>

    <form method="post" action="/reply/write">
    
        <p>
            <label>댓글 작성자</label> <input type="text" id="replyWriter" name="reply_writer">
        </p>
        <p>
            <textarea rows="5" cols="50" name="content" id="replyContent"></textarea>
        </p>
        <p>
        	<input type="hidden" name="bno" value="${view.bno}">
            <button disabled="true" type="submit" id="submitBtn">댓글 작성</button>
        </p>
        
    </form>
    
</div>

<!-- 댓글 끝 -->

<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script type ="text/javascript">                 
	$(function(){
		$("#replyWriter").on('input', function(){		// 작성자는 필수로 입력
			if ($("#replyWriter").val == '') {
				console.log("1");
				$("#submitBtn").attr("disabled", true);
			} else {
				console.log("2");
				$("#submitBtn").attr("disabled", false);
			}
		});
	});
</script>

</body>
</html>