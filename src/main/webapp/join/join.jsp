<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    request.setAttribute("activePage", "login");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>会員登録 — ANIVERSE</title>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Zen+Kaku+Gothic+New:wght@400;500;700;900&family=Noto+Sans+JP:wght@400;500;700&family=JetBrains+Mono:wght@400;600&display=swap"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/style.css"
	rel="stylesheet">
</head>
<body>

	<%@ include file="/common/header.jsp"%>

	<section class="section container req-anchor" style="max-width: 480px;">
		<span class="req-id">USER-001 · USER-002 · USER-003 · USER-004</span>
		<div class="surface p-4 p-md-5">
			<h3 class="text-center mb-1">会員登録</h3>
			<p class="text-muted text-center mb-4 small">いくつかの情報を入力するだけで、すぐに始められます</p>

			<!-- USER-001~004 : member/join.do 로 회원가입 요청을 보낸다 -->
			<form id="joinForm" novalidate
				action="${pageContext.request.contextPath}/member/join.do"
				method="post" onsubmit="return validateJoin()">

				<div class="mb-3">
					<label class="form-label-custom">ID</label>
					<div class="d-flex gap-2">
						<input type="text" class="form-control form-control-custom"
							name="userId" id="joinId" value="${param.userId}"
							placeholder="IDを入力" required>
						<button class="btn btn-outline-soft flex-shrink-0" type="button"
							onclick="checkDup()">重複確認</button>
					</div>
					<div class="small mt-1" id="dupResult"></div>
				</div>

				<div class="mb-3">
					<label class="form-label-custom">パスワード</label> <input
						type="password" class="form-control form-control-custom"
						name="userPw" id="joinPw" placeholder="パスワードを入力" required>
				</div>

				<div class="mb-3">
					<label class="form-label-custom">パスワード確認</label> <input
						type="password" class="form-control form-control-custom"
						name="userPwCheck" id="joinPwCheck" placeholder="パスワードを再入力"
						required>
				</div>

				<div class="mb-3">
					<label class="form-label-custom">ニックネーム</label>
					<div class="d-flex gap-2">
						<input type="text" class="form-control form-control-custom"
							name="nickname" id="joinNick" value="${param.nickname}"
							placeholder="ニックネームを入力" required>
						<button class="btn btn-outline-soft flex-shrink-0" type="button"
							onclick="checkNicknameDup()">重複確認</button>
					</div>
					<div class="small mt-1" id="nickDupResult"></div>
				</div>

				<div class="mb-2">
					<label class="form-label-custom">メールアドレス</label>
					<div class="d-flex gap-2">
						<input type="email" class="form-control form-control-custom"
							name="email" id="joinEmail" value="${param.email}"
							placeholder="you@example.com" required>
						<button class="btn btn-outline-soft flex-shrink-0" type="button"
							id="sendCodeBtn" onclick="sendEmailCode()">認証番号を受け取る</button>
					</div>
					<div class="small mt-1" id="emailCodeSentResult"></div>
				</div>

				<div class="mb-4" id="emailCodeInputArea" style="display: none;">
					<label class="form-label-custom">認証番号</label>
					<div class="d-flex gap-2">
						<input type="text" class="form-control form-control-custom"
							id="emailCodeInput" placeholder="6桁の認証番号を入力" maxlength="6"
							inputmode="numeric">
						<button class="btn btn-outline-soft flex-shrink-0" type="button"
							onclick="verifyEmailCode()">確認</button>
					</div>
					<div class="small mt-1" id="emailVerifyResult"></div>
				</div>

				<c:if test="${not empty errorMsg}">
					<div class="alert alert-danger py-2 small" id="joinErrorMsg"
						style="background: #3a1a22; border: 1px solid #6b2532; color: #ffb4c0;">${errorMsg}</div>
				</c:if>

				<button class="btn btn-accent w-100" type="submit">登録する</button>
			</form>

			<!-- SOCIAL-001 : 구글/라인으로 가입 (처음 로그인하는 순간 자동으로 회원가입까지 처리된다) -->
			<div class="d-flex align-items-center gap-2 my-3">
				<hr style="flex: 1; border-color: rgba(255, 255, 255, .15);">
				<span class="text-muted small">または</span>
				<hr style="flex: 1; border-color: rgba(255, 255, 255, .15);">
			</div>
			<div class="d-flex flex-column gap-2">
				<a href="${pageContext.request.contextPath}/oauth/google"
					class="btn w-100 d-flex align-items-center justify-content-center gap-2"
					style="background: #fff; color: #3c4043; border: 1px solid #dadce0; font-weight: 500;">
					<span style="font-weight: 700; color: #4285F4;">G</span> Googleで登録
				</a> <a href="${pageContext.request.contextPath}/oauth/line"
					class="btn w-100 d-flex align-items-center justify-content-center gap-2"
					style="background: #06C755; color: #fff; border: none; font-weight: 500;">
					LINEで登録 </a>
			</div>
		</div>
	</section>

	<%@ include file="/common/footer.jsp"%>
	<script>
    const CTX = "${pageContext.request.contextPath}";

    // 이전 제출 실패 시 떴던 에러 메시지가, 사용자가 다시 입력을 시작했는데도
    // 화면에 그대로 남아있어 혼란을 주지 않도록 입력값이 바뀌면 즉시 숨긴다.
    (function () {
      const errBox = document.getElementById("joinErrorMsg");
      if (!errBox) return;
      document.querySelectorAll("#joinForm input").forEach(function (el) {
        el.addEventListener("input", function () {
          errBox.style.display = "none";
        });
      });
    })();

    let idChecked = false;      // USER-003 : 아이디 중복 확인을 통과했는지
    let idCheckedValue = "";    // 중복 확인을 통과한 시점의 아이디 값
    let nicknameChecked = false;      // USER-003(닉네임) : 닉네임 중복 확인을 통과했는지
    let nicknameCheckedValue = "";    // 중복 확인을 통과한 시점의 닉네임 값
    let emailVerified = false;  // 이메일 인증(데모용 인증번호)을 통과했는지
    let emailVerifiedValue = ""; // 인증을 통과한 시점의 이메일 값
    let codeRequested = false;

    // USER-003 : 아이디 중복 확인 (member/idcheck.do 비동기 호출)
    function checkDup(){
      const id = document.getElementById("joinId").value.trim();
      const res = document.getElementById("dupResult");
      if(!id){ res.innerHTML = '<span style="color:#ffb4c0;">IDを入力してください。</span>'; return; }

      fetch(CTX + "/member/idcheck.do?userId=" + encodeURIComponent(id))
        .then(r => r.text())
        .then(result => {
          if(result === "dup"){
            res.innerHTML = '<span style="color:#ffb4c0;">すでに使用されているIDです。</span>';
            idChecked = false;
          }else if(result === "ok"){
            res.innerHTML = '<span style="color:var(--accent-2);">使用可能なIDです。</span>';
            idChecked = true;
            idCheckedValue = id;
          }else{
            res.innerHTML = '<span style="color:#ffb4c0;">IDを入力してください。</span>';
            idChecked = false;
          }
        })
        .catch(() => {
          res.innerHTML = '<span style="color:#ffb4c0;">確認中にエラーが発生しました。</span>';
        });
    }

    // USER-003(닉네임) : 닉네임 중복 확인 (member/nickcheck.do 비동기 호출)
    function checkNicknameDup(){
      const nickname = document.getElementById("joinNick").value.trim();
      const res = document.getElementById("nickDupResult");
      if(!nickname){ res.innerHTML = '<span style="color:#ffb4c0;">ニックネームを入力してください。</span>'; return; }

      fetch(CTX + "/member/nickcheck.do?nickname=" + encodeURIComponent(nickname))
        .then(r => r.text())
        .then(result => {
          if(result === "dup"){
            res.innerHTML = '<span style="color:#ffb4c0;">すでに使用されているニックネームです。</span>';
            nicknameChecked = false;
          }else if(result === "ok"){
            res.innerHTML = '<span style="color:var(--accent-2);">使用可能なニックネームです。</span>';
            nicknameChecked = true;
            nicknameCheckedValue = nickname;
          }else{
            res.innerHTML = '<span style="color:#ffb4c0;">ニックネームを入力してください。</span>';
            nicknameChecked = false;
          }
        })
        .catch(() => {
          res.innerHTML = '<span style="color:#ffb4c0;">確認中にエラーが発生しました。</span>';
        });
    }

    // 이메일 인증번호 발급 요청 (member/emailcode.do)
    // 데모 모드: 실제 메일 서버가 없으므로, 발급된 인증번호를 응답에서 그대로 받아 화면에 표시한다.
    function sendEmailCode(){
      const email = document.getElementById("joinEmail").value.trim();
      const sentResult = document.getElementById("emailCodeSentResult");
      if(!email){
        sentResult.innerHTML = '<span style="color:#ffb4c0;">メールアドレスを入力してください。</span>';
        return;
      }

      fetch(CTX + "/member/emailcode.do?email=" + encodeURIComponent(email))
        .then(r => r.text())
        .then(result => {
          if(result === "ok"){
            codeRequested = true;
            emailVerified = false;
            document.getElementById("emailCodeInputArea").style.display = "block";
            sentResult.innerHTML = '<span style="color:var(--accent-2);">認証番号を送信しました。メールをご確認ください。</span>';
          }else{
            sentResult.innerHTML = '<span style="color:#ffb4c0;">メールアドレスを入力してください。</span>';
          }
        })
        .catch(() => {
          sentResult.innerHTML = '<span style="color:#ffb4c0;">送信中にエラーが発生しました。</span>';
        });
    }

    // 발급된 인증번호 확인 (member/emailverify.do)
    function verifyEmailCode(){
      const email = document.getElementById("joinEmail").value.trim();
      const code = document.getElementById("emailCodeInput").value.trim();
      const res = document.getElementById("emailVerifyResult");

      if(!code){
        res.innerHTML = '<span style="color:#ffb4c0;">認証番号を入力してください。</span>';
        return;
      }

      fetch(CTX + "/member/emailverify.do?email=" + encodeURIComponent(email)
            + "&code=" + encodeURIComponent(code))
        .then(r => r.text())
        .then(result => {
          if(result === "ok"){
            res.innerHTML = '<span style="color:var(--accent-2);">メール認証が完了しました。</span>';
            emailVerified = true;
            emailVerifiedValue = email;
          }else if(result === "expired"){
            res.innerHTML = '<span style="color:#ffb4c0;">認証番号の有効期限が切れました。再度お受け取りください。</span>';
            emailVerified = false;
          }else if(result === "mismatch"){
            res.innerHTML = '<span style="color:#ffb4c0;">認証番号が一致しません。</span>';
            emailVerified = false;
          }else{
            res.innerHTML = '<span style="color:#ffb4c0;">先に認証番号を受け取ってください。</span>';
            emailVerified = false;
          }
        })
        .catch(() => {
          res.innerHTML = '<span style="color:#ffb4c0;">確認中にエラーが発生しました。</span>';
        });
    }

    // USER-004 : 최종 제출 전 입력값 검증 (서버에서도 동일하게 재검증한다)
    function validateJoin(){
      const id = document.getElementById("joinId").value.trim();
      const pw = document.getElementById("joinPw").value;
      const pw2 = document.getElementById("joinPwCheck").value;
      const nicknameVal = document.getElementById("joinNick").value.trim();
      const email = document.getElementById("joinEmail").value.trim();

      if(!id){
        alert("IDを入力してください。");
        return false;
      }
      if(!pw){
        alert("パスワードを入力してください。");
        return false;
      }
      if(!pw2){
        alert("パスワード確認を入力してください。");
        return false;
      }
      if(!nicknameVal){
        alert("ニックネームを入力してください。");
        return false;
      }
      if(!email){
        alert("メールアドレスを入力してください。");
        return false;
      }

      if(pw !== pw2){
        alert("パスワードが一致しません。");
        return false;
      }
      if(!idChecked || idCheckedValue !== id){
        alert("IDの重複確認をしてください。");
        return false;
      }
      if(!nicknameChecked || nicknameCheckedValue !== nicknameVal){
        alert("ニックネームの重複確認をしてください。");
        return false;
      }
      if(!emailVerified || emailVerifiedValue !== email){
        alert("メール認証を完了してください。");
        return false;
      }
      return true;
    }
  </script>
</body>
</html>
