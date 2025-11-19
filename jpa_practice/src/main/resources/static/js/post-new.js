document.addEventListener('DOMContentLoaded', () => {
    const postForm = document.getElementById('postForm');

    postForm.addEventListener('submit', handlePostSubmit);
});

const POST_API_URL = '/api/v1/posts';

async function handlePostSubmit(event) {
    event.preventDefault(); // 기본 폼 제출 동작 (페이지 새로고침) 방지

    // 1. 폼 데이터 추출
    const title = document.getElementById('title').value;
    const content = document.getElementById('content').value;

    // TODO: 현재 로그인한 사용자 ID를 얻는 로직이 없으므로 임의의 ID 사용
    const writerId = 1; // 임시 작성자 ID

    const postData = {
        writerId: writerId,
        title: title,
        content: content
    };

    try {
        // 2. Fetch API로 POST 요청 전송
        const response = await fetch(POST_API_URL, {
            method: 'POST', // HTTP POST 메서드
            headers: {
                'Content-Type': 'application/json' // 전송하는 데이터 형식 지정
            },
            body: JSON.stringify(postData) // JavaScript 객체를 JSON 문자열로 변환하여 전송
        });

        // 3. 응답 처리
        if (response.ok) {
            // 성공적으로 생성된 경우 (201 Created 포함)
            const result = await response.json();
            alert('게시글이 성공적으로 작성되었습니다.');

            // 4. 게시글 목록 페이지 또는 상세 페이지로 이동
            window.location.href = `/posts/${result.id}`; // 생성된 게시글 상세 페이지로 이동 (PostResponse에 id가 있어야 함)

        } else if (response.status === 400) {
            // 400 Bad Request 등 유효성 검사 실패
            const error = await response.json();
            alert(`작성 실패: ${error.message || '입력값을 확인해 주세요.'}`);
        }
        else {
            throw new Error(`게시글 작성 실패: ${response.statusText}`);
        }

    } catch (error) {
        console.error("게시글 작성 중 오류 발생:", error);
        alert('서버와의 통신 중 오류가 발생했습니다.');
    }
}