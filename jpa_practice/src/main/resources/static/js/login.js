// login.js

const emailInput = document.getElementById("login-email");
const emailHelper = document.getElementById("login-email-helper");

const pwInput = document.getElementById("login-password");
const pwHelper = document.getElementById("login-password-helper");

const btnLogin = document.getElementById("btnLogin");

// 유효성 검사
function isValidEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

// 로그인은 패스워드 규칙을 강하게 검사할 필요 없음
// (회원가입에서 이미 강한 규칙 적용됨)
function isNotEmpty(str) {
    return str && str.trim().length > 0;
}

// helper 표시
function showError(helperEl, msg) {
    helperEl.textContent = msg;
    helperEl.classList.add("form-helper--error");
}

function clearError(helperEl) {
    helperEl.textContent = "";
    helperEl.classList.remove("form-helper--error");
}

// 버튼 활성화
function updateBtn() {
    const email = emailInput.value.trim();
    const pw = pwInput.value.trim();

    const ok = isValidEmail(email) && isNotEmpty(pw);
    btnLogin.disabled = !ok;
}

// 입력 이벤트
emailInput.addEventListener("input", () => {
    const email = emailInput.value.trim();

    if (!email) {
        clearError(emailHelper);
    } else if (!isValidEmail(email)) {
        showError(emailHelper, "올바른 이메일 주소를 입력해주세요. 예: example@example.com");
    } else {
        clearError(emailHelper);
    }

    updateBtn();
});

pwInput.addEventListener("input", () => {
    const pw = pwInput.value.trim();

    if (!pw) {
        clearError(pwHelper);
    } else {
        clearError(pwHelper);
    }

    updateBtn();
});

// 로그인 버튼 클릭
btnLogin.addEventListener("click", async () => {
    const email = emailInput.value.trim();
    const pw = pwInput.value.trim();

    // 마지막 방어
    if (!isValidEmail(email)) {
        showError(emailHelper, "올바른 이메일 주소를 입력해주세요.");
        return;
    }
    if (!isNotEmpty(pw)) {
        showError(pwHelper, "비밀번호를 입력해주세요.");
        return;
    }

    // 아직 백엔드는 없으니까 성공 처리만
    // TODO: POST /api/v1/auth/login 으로 교체
    alert("로그인 성공!");
    window.location.href = "/posts";
});

