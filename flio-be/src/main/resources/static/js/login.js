// login.js

// 실제 로그인 API 주소에 맞게 수정해서 사용해줘!
const LOGIN_API_URL = '/api/v1/auth/login';

document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('loginForm');
    if (!form) return;

    form.addEventListener('submit', onLoginSubmit);
});

async function onLoginSubmit(e) {
    e.preventDefault();

    const emailEl = document.getElementById('loginEmail');
    const passwordEl = document.getElementById('loginPassword');

    const email = emailEl.value.trim();
    const password = passwordEl.value.trim();

    if (!email || !password) {
        alert('이메일과 비밀번호를 모두 입력해 주세요.');
        return;
    }

    const body = { email, password };

    try {
        const res = await fetch(LOGIN_API_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body),
        });

        if (!res.ok) {
            alert(`로그인 실패 (status: ${res.status})`);
            return;
        }

        const user = await res.json();
        console.log('로그인 성공:', user);

        // 임시: 로그인 유저 ID를 localStorage에 저장 (나중에 JWT/세션으로 교체)
        if (user && user.id) {
            localStorage.setItem('currentUserId', String(user.id));
            localStorage.setItem('currentUserNickname', user.nickname || '');
        }

        // 로그인 성공 후 목록 페이지로 이동
        window.location.href = '/posts';
    } catch (err) {
        console.error('로그인 에러:', err);
        alert('로그인 중 오류가 발생했습니다.');
    }
}
