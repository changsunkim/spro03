<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../includes/header.jsp"%>
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">게시글 상세 화면</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">게시글 상세 내용</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-lg-6">
						<!--   <form role="form" action="/board/get"> -->
						<div class="form-group">
							<label>번호</label> <input class="form-control" name="bno"
								value='<c:out value="${board.bno }"/>' readonly="readonly">
						</div>
						<div class="form-group">
							<label>제목</label> <input class="form-control" name="title"
								value='<c:out value="${board.title }"/>' readonly="readonly">
						</div>
						<div class="form-group">
							<label>내용</label>
							<textarea class="form-control" rows="3" name="content"
								readonly="readonly"><c:out value="${board.content }" /></textarea>
						</div>
						<div class="form-group">
							<label>작성자</label> <input class="form-control"
								placeholder="Enter text" name="writer"
								value='<c:out value="${board.writer }"/>' readonly="readonly">
						</div>
						<button data-oper='modify' class="btn btn-default">수정</button>
						<button data-oper='list' class="btn btn-default">목록</button>
						<form id="operForm" action="/board/modify" method="get">
							<input type='hidden' id="bno" name="bno"
								value='<c:out value="${board.bno }"/>'> <input
								type='hidden' name="pageNum"
								value='<c:out value="${cri.pageNum }"/>'> <input
								type='hidden' name="amount"
								value='<c:out value="${cri.amount }"/>'> <input
								type='hidden' name='type' value='<c:out value="${cri.type }"/>'>
							<input type='hidden' name='keyword'
								value='<c:out value="${cri.keyword }"/>'>
						</form>
						<!--  </form> -->
					</div>
					<!-- /.col-lg-6 (nested) -->
				</div>
				<!-- /.row (nested) -->
				<div class="panel-heading">
					<i class="fa fa-comments fa-fw"></i>reply
					<!-- 아이콘 -->
					<button id="addReplyBtn" class="btn btn-primary btn-xs pull-right">New
						Reply</button>
				</div>
			</div>
			<!-- /.panel-body -->
		</div>
		<!-- /.panel -->
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<!-- 댓글 -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<i class="fa fa-comments fa-fw"></i>Reply
			</div>

			<div class="panel-body">
				<ul class="chat">
					<!-- start reply -->
					<li class="left clearfix" data-rno='12'>
						<div>
							<div class="header">
								<strong class="primary-font">user00</strong> <small
									class="pull-right text-muted">2021-07-15 15:18</small>
							</div>
							<p>Good Job!</p>
						</div>
					</li>
				</ul>
			</div>
			<!-- /.panel-body -->
			
			<div class="panel-footer"></div>
			
		</div>
		<!-- /.panel panel-default -->
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">댓글 등록</h4>
			</div>
			<div class="modal-body">
				<div class="modal-group">
					<label>댓글</label> <input class="form-control" name="reply"
						value="new reply!!">
				</div>
				<div class="modal-group">
					<label>작성자</label> <input class="form-control" name="replyer"
						value="Replyer">
				</div>
				<div class="modal-group">
					<label>작성일</label> <input class="form-control" name="replydate"
						value="">
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" id="modalModBtn" class="btn btn-warning">Modify</button>
				<button type="button" id="modalRemoveBtn" class="btn btn-danger">Remove</button>
				<button type="button" id="modalRegisterBtn" class="btn btn-primary">Register</button>
				<button type="button" id="modalCloseBtn" class="btn btn-default"
					data-dismiss="modal">Close</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>


<%@ include file="../includes/footer.jsp"%>

<script type="text/javascript" src="/resources/js/reply.js"></script>

<script type="text/javascript">
	$(document)
			.ready(
					function() {

						console.log("===============")
						console.log("JS TEST")

						var bnoValue = '<c:out value = "${board.bno}"/>';
						var replyUL = $(".chat");

						showList(1);

						function showList(page) {
							console.log("show list " + page);
							replyService.getList(
											{
												bno : bnoValue,
												page : page||1
											},
											function(replyCnt,list) {
												
												console.log("replyCnt" + replyCnt);
												console.log("list" +	list);
												console.log(list);
												
												if(page==-1){
													pageNum = Math.ceil(replyCnt/10.0);
													showList(pageNum);
													return;
												}
												
												var str = "";
												if (list == null
														|| list.length == 0) {
													replyUL.html("");
													return;
												}
												for (var i = 0, len = list.length || 0; i < len; i++) {
													str += "<li class='left clearfix' data-rno='"+ list[i].rno + "'>";
													str += "<div><div class='header'><strong class='primary-font'>"
															+ list[i].replyer
															+ " </strong>";
													str += "<small class='pull-right text-muted'>"
															+ replyService
																	.displayTime(list[i].replydate)
															+ "</small></div>"
													str += "<p>"
															+ list[i].reply
															+ "</p></div></li>";
												}

												replyUL.html(str);
												showReplyPage(replyCnt);
											}); // end fuction

						} // end showList
					
						
						
						var modal = $(".modal");
						var modalInputReply = modal.find("input[name='reply']");
						var modalInputReplyer = modal
								.find("input[name='replyer']");
						var modalInputReplydate = modal
								.find("input[name='replydate']");

						var modalModBtn = $("#modalModBtn");
						var modalRemoveBtn = $("#modalRemoveBtn");
						var modalRegisterBtn = $("#modalRegisterBtn");
						var modalCloseBtn = $("#modalCloseBtn");

						$('#addReplyBtn').on("click", function(e) {
							modal.find("input").val("");
							modalInputReplydate.closest("div").hide();
							modal.find("button[id != 'modalCloseBtn']").hide();
							modalRegisterBtn.show();

							$(".modal").modal("show");
						})
						modalRegisterBtn.on("click", function(e){
							var reply = {
									reply : modalInputReply.val(),//사용자가 입력한 댓글
									replyer : modalInputReplyer.val(),
									bno : bnoValue
							};
							replyService.add(reply, function(result){
								alert(result);
								modal.find("input").val("")
								modal.modal("hide");//모달창 닫음
								showList(-1);//창을 닫고 바로 화면을 보여줌
								//-1한이유 새 댓글 추가시 마지막 페이지로 이동 
							});
						});
						
						$(".chat").on("click", "li", function(e){
							var rno = $(this).data("rno");
							
							replyService.get(rno, function(reply){
								modalInputReply.val(reply.reply);//reply.reply= 댓글 내용
								modalInputReplyer.val(reply.replyer);
								modalInputReplydate.val(replyService.displayTime(reply.replydate)).attr("readonly","readonly");//수정못하게 읽을 수만 있겍 attr처리	
								modal.data("rno", reply.rno);
								
								modal.find("button[id !='modalCloseBtn']").hide();
								modalInputReplydate.closest("div").show();
								modalModBtn.show();
								modalRemoveBtn.show();
								
								$(".modal").modal("show");
							});
						});
						
					
						modalModBtn.on("click", function(e){
							var reply = {rno:modal.data("rno"),reply:modalInputReply.val()};
							
							replyService.update(reply, function(result){
								alert(result);
								modal.modal("hide");
								showList(pageNum);
								
							});
						});
				
						
						modalRemoveBtn.on("click", function(e){
							var rno = modal.data("rno");
							
							replyService.remove(rno, function(result){
								alert(result);
								modal.modal("hide");
								showList(pageNum);
							})
						})
						
						var pageNum = 1;
						var replyPageFooter = $(".panel-footer");
						
						function showReplyPage(replyCnt){
							var endNum = Math.ceil(pageNum / 10.0)*10;
							var startNum = endNum - 9;
							//prev = 화살표
							var prev = startNum != 1;
							var next = false;
							
							if(endNum * 10 >= replyCnt){
								endNum = Math.ceil(replyCnt / 10.0)
							}if(endNum * 10 < replyCnt){
								next = true;
							}
							var str = "<ul class='pagination pull-right'>";
							if(prev){
								str += "<li class='page-item'><a class='page-link' href='"+(startNum - 1)+"'>Previous</a></li>";
							}
							for(var i = startNum ; i<=endNum; i++){
								var active = pageNum==i?"active" : " ";//active 진하게
								str += "<li class='page-item'" + active + "'><a class='page-link' href='"+i+"'>" + i + "</a></li>"
							}
							str +="</ul>";
							console.log(str);
							replyPageFooter.html(str);
							
						}
						
						//페이지 클릭했을때 다음 페이지로 넘기기
						replyPageFooter.on("click","li a", function(e){
							e.preventDefault();
							
							console.log("page click");h
							
							var targetPageNum = $(this).attr("href");
							console.log("targetPageNum : " + targetPageNum);
							pageNum = targetPageNum;
							showList(pageNum);
						});
						
						// for replyService add Test
						/* replyService.add(
						{reply:"JS TEST", replyer:"tester", bno:bnoValue},
						function(result){
						   alert("RESULT : " + result);
						}); */

						/* replyService.getList({bno:bnoValue, page:1}, function(list){
						                  
						for(var i=0, len=list.length||0; i <len; i++){
						   console.log(list[i]);
						}
						}); */

						//for replyService delete Test
						/* replyService.remove(82, function(count){
						   console.log(count);
						      if(count=="success"){
						         alert("REMOVE 성공했음.");
						      }
						   },function(err){
						      alert("REMOVE ERROR");   
						   }); */

						// 댓글 수정
						/* replyService.update({
						   rno : 6,
						   bno : bnoValue,
						   reply : "Modified Reply(AJAX)..."
						}, function(result){
						   alert("수정 완료...");
						   }); */

						replyService.get(4, function(data) {
							console.log("댓글 한건 읽기 테스트 잘되어라 비아니다" + data);
						});

						console.log(replyService)
					})
</script>

<script type="text/javascript">
	$(document).ready(function() {
		var operForm = $("#operForm");
		$("button[data-oper = 'modify']").on('click', function(e) {
			operForm.attr("action", "/board/modify").submit();
		});

		$("button[data-oper = 'list']").on('click', function(e) {
			operForm.find("#bno").remove();
			operForm.attr("action", "/board/list");
			operForm.submit();
		});

	});
</script>