// src/main/resources/static/js/post-new.js

const POST_API_URL = '/api/v1/posts';

// 현재 모드 플래그 & ID
let isEditMode = false;
let currentPostId = null;

document.addEventListener('DOMContentLoaded', () => {
    const postForm = document.getElementById('postForm');

    detectModeFromUrl();      // 새 글 / 수정 모드 판단

    if (isEditMode) {
        loadPostForEdit();    // 수정 모드면 기존 글 내용 불러오기
    }

    if (postForm) {
        postForm.addEventListener('submit', handlePostSubmit);
    }
});

/**
 * URL을 보고 지금이 새 글(/posts/new)인지, 수정(/posts/{id}/edit)인지 판단
 */
function detectModeFromUrl() {
    const path = window.location.pathname;                // /posts/new 또는 /posts/3/edit
    const segments = path.split('/').filter(Boolean);     // ["posts","new"] 또는 ["posts","3","edit"]

    const headerTitle = document.querySelector('.app-header__title');
    const pageTitle = document.querySelector('.page-title');
    const submitButton = document.querySelector('#postForm button[type="submit"]');

    if (segments.length === 3 && segments[0] === 'posts' && segments[2] === 'edit') {
        // /posts/{id}/edit  → 수정 모드
        isEditMode = true;
        currentPostId = segments[1];

        if (headerTitle) headerTitle.textContent = '게시글 수정';
        if (pageTitle) pageTitle.textContent = '게시글 수정';
        if (submitButton) submitButton.textContent = '수정 완료';
    } else {
        // 그 외 → 새 글 모드
        isEditMode = false;
        currentPostId = null;

        if (headerTitle) headerTitle.textContent = '새 게시글 작성';
        if (pageTitle) pageTitle.textContent = '게시글 작성';
        if (submitButton) submitButton.textContent = '작성 완료';
    }
}

/**
 * 수정 모드일 때 기존 게시글 데이터를 불러와서 폼에 채워 넣기
 */
async function loadPostForEdit() {
    if (!currentPostId) return;

    try {
        const response = await fetch(`${POST_API_URL}/${currentPostId}`);

        if (!response.ok) {
            throw new Error(`게시글 불러오기 실패: ${response.status}`);
        }

        const post = await response.json();

        const titleInput = document.getElementById('title');
        const contentInput = document.getElementById('content');

        if (titleInput) titleInput.value = post.title || '';
        if (contentInput) contentInput.value = post.content || '';
    } catch (error) {
        console.error('게시글 데이터를 불러오는 중 오류 발생:', error);
        alert('게시글 정보를 불러오는 데 실패했습니다.');
    }
}

/**
 * 새 글 작성 + 수정 공용 submit 핸들러
 */
async function handlePostSubmit(event) {
    event.preventDefault(); // 기본 폼 제출(새로고침) 방지

    const titleInput = document.getElementById('title');
    const contentInput = document.getElementById('content');

    const title = titleInput ? titleInput.value.trim() : '';
    const content = contentInput ? contentInput.value.trim() : '';

    if (!title) {
        alert('제목을 입력해 주세요.');
        return;
    }
    if (!content) {
        alert('내용을 입력해 주세요.');
        return;
    }

    try {
        if (isEditMode && currentPostId) {
            // ✅ 수정 모드 → PATCH /api/v1/posts/{id}
            await updatePost(currentPostId, { title, content });
            alert('게시글이 수정되었습니다.');
            window.location.href = `/posts/${currentPostId}`;
        } else {
            // ✅ 새 글 작성 모드 → POST /api/v1/posts
            const writerId = 1; // TODO: 나중에 로그인 붙이면 실제 사용자 ID로 교체
            const newPostId = await createPost({ writerId, title, content });
            alert('게시글이 성공적으로 작성되었습니다.');
            window.location.href = `/posts/${newPostId}`;
        }
    } catch (error) {
        console.error('게시글 저장 중 오류 발생:', error);
        alert('서버와의 통신 중 오류가 발생했습니다.');
    }
}

/**
 * 새 게시글 생성 (POST /api/v1/posts)
 * body: { writerId, title, content }
 * 반환: 생성된 게시글 id
 */
async function createPost(postData) {
    const response = await fetch(POST_API_URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(postData)
    });

    if (!response.ok) {
        if (response.status === 400) {
            const error = await response.json();
            alert(`작성 실패: ${error.message || '입력값을 확인해 주세요.'}`);
        }
        throw new Error(`게시글 작성 실패: ${response.status} ${response.statusText}`);
    }

    const result = await response.json();  // PostResponse
    return result.id;                      // id 있어야 함
}

/**
 * 기존 게시글 수정 (PATCH /api/v1/posts/{id})
 * body: { title, content }
 */
async function updatePost(postId, postData) {
    const response = await fetch(`${POST_API_URL}/${postId}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(postData)
    });

    if (!response.ok) {
        if (response.status === 400) {
            const error = await response.json();
            alert(`수정 실패: ${error.message || '입력값을 확인해 주세요.'}`);
        }
        throw new Error(`게시글 수정 실패: ${response.status} ${response.statusText}`);
    }
}
