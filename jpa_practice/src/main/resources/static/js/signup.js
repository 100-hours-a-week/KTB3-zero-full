// signup.js

const emailInput = document.getElementById("signup-email");
const emailHelper = document.getElementById("signup-email-helper");

const pwInput = document.getElementById("signup-password");
const pwHelper = document.getElementById("signup-password-helper");

const pwConfirmInput = document.getElementById("signup-password-confirm");
const pwConfirmHelper = document.getElementById("signup-password-confirm-helper");

const nickInput = document.getElementById("signup-nickname");
const nickHelper = document.getElementById("signup-nickname-helper");

const btnSignup = document.getElementById("btnSignup");

//유효성 검사 함수
// 이메일 형식 체크
function isValidEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

// 비밀번호 규칙: 8~20자, 대/소/숫자/특수 각각 최소 1개
function isValidPassword(pw) {
    if (!pw) return false;
    if (pw.length < 8 || pw.length > 20) return false;
    const hasUpper = /[A-Z]/.test(pw);
    const hasLower = /[a-z]/.test(pw);
    const hasNumber = /[0-9]/.test(pw);
    const hasSpecial = /[^A-Za-z0-9]/.test(pw);
    return hasUpper && hasLower && hasNumber && hasSpecial;
}

// 닉네임 1~10자
function isValidNickname(nick){
    return nick && nick.length >= 1 && nick.length <= 10;
}

// helper 표시 함수
function showError(helperEl, msg) {
    helperEl.textContent = msg;
    helperEl.classList.add("form-helper--error");
}

function clearError(helperEl) {
    helperEl.textContent = "";
    helperEl.classList.remove("form-helper--error");
}

// 버튼 활성화 처리
function updateBtn() {
    const email = emailInput.value.trim();
    const pw = pwInput.value.trim();
    const pw2 = pwConfirmInput.value.trim();
    const nick = nickInput.value.trim();

    const ok =
        isValidEmail(email) &&
        isValidPassword(pw) &&
        pw === pw2 &&
        isValidNickname(nick);

    btnSignup.disabled = !ok;
}

btnSignup.disabled = true;

// 실시간 입력 검증
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
    } else if (!isValidPassword(pw)) {
        showError(pwHelper, "비밀번호는 8~20자, 대/소문자·숫자·특수문자를 모두 포함해야 합니다.");
    } else {
        clearError(pwHelper);
    }

    // 비밀번호 바뀌면 확인창도 다시 체크
    const pw2 = pwConfirmInput.value.trim();
    if (pw2 && pw !== pw2) {
        showError(pwConfirmHelper, "비밀번호가 일치하지 않습니다.");
    } else {
        clearError(pwConfirmHelper);
    }

    updateBtn();
});

pwConfirmInput.addEventListener("input", () => {
    const pw = pwInput.value.trim();
    const pw2 = pwConfirmInput.value.trim();

    if (!pw2) {
        clearError(pwConfirmHelper);
    } else if (pw !== pw2) {
        showError(pwConfirmHelper, "비밀번호가 일치하지 않습니다.");
    } else {
        clearError(pwConfirmHelper);
    }

    updateBtn();
});

nickInput.addEventListener("input", () => {
    const nick = nickInput.value.trim();

    if (!nick) {
        clearError(nickHelper);
    } else if (!isValidNickname(nick)) {
        showError(nickHelper, "닉네임은 1~10자 사이로 입력해주세요.");
    } else {
        clearError(nickHelper);
    }

    updateBtn();
});

// 회원가입 버튼 클릭
btnSignup.addEventListener("click", async (e) => {
    e.preventDefault();

    const email = emailInput.value.trim();
    const pw = pwInput.value.trim();
    const pw2 = pwConfirmInput.value.trim();
    const nick = nickInput.value.trim();

    // 마지막 방어
    if (!isValidEmail(email) || !isValidPassword(pw) || pw !== pw2 || !isValidNickname(nick)) {
        alert("입력값을 다시 확인해주세요.");
        return;
    }

    // 지금은 백엔드 회원가입 로직 없으므로 성공 처리만
    // TODO: 나중에 POST /api/v1/users 로 교체
    alert("회원가입이 완료되었습니다!");
    window.location.href = "/login";
});
