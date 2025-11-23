// src/main/resources/static/js/post-detail.js

const BASE_API_URL = '/api/v1/posts';

// DOM 요소 캐싱
const detailContainer = document.getElementById('post-detail-container');
const postDeleteModal = document.getElementById('postDeleteModal');
const btnPostDeleteCancel = document.getElementById('btnPostDeleteCancel');
const btnPostDeleteConfirm = document.getElementById('btnPostDeleteConfirm');

// 댓글 관련 요소
const commentForm = document.getElementById('commentForm');
const commentContentInput = document.getElementById('commentContent');
const commentListContainer = document.getElementById('commentList');

// 댓글 삭제 모달 관련
const commentDeleteModal = document.getElementById('commentDeleteModal');
const btnCommentDeleteCancel = document.getElementById('btnCommentDeleteCancel');
const btnCommentDeleteConfirm = document.getElementById('btnCommentDeleteConfirm');

let pendingDeleteCommentId = null;

// 현재 게시글 ID
let currentPostId = null;

// 페이지 로드 시
document.addEventListener('DOMContentLoaded', () => {
    const postId = getPostIdFromUrl();

    if (!postId || isNaN(postId)) {
        detailContainer.innerHTML = '<p>유효하지 않은 게시글 ID입니다.</p>';
        return;
    }

    currentPostId = postId;

    // 게시글 상세
    fetchPostDetail(postId);
    setupPostDeleteModal();

    // 댓글
    setupCommentForm();
    fetchComments(postId);

    // 댓글 수정/삭제 이벤트 위임
    setupCommentListEvents();

    // 댓글 삭제 모달
    setupCommentDeleteModal();
});

// URL에서 {postId} 추출
function getPostIdFromUrl() {
    const pathSegments = window.location.pathname.split('/').filter(Boolean);
    return pathSegments[pathSegments.length - 1];
}

// 게시글 상세 가져오기
async function fetchPostDetail(postId) {
    try {
        const response = await fetch(`${BASE_API_URL}/${postId}`);

        if (response.status === 404) {
            detailContainer.innerHTML = `<p>게시글 ID: ${postId}번을 찾을 수 없습니다.</p>`;
            return;
        }

        if (!response.ok) {
            throw new Error(`HTTP 오류! 상태: ${response.status}`);
        }

        const post = await response.json();
        renderPostDetail(post);
    } catch (error) {
        console.error("게시글 상세 정보를 불러오는 데 실패:", error);
        detailContainer.innerHTML = '<p>데이터를 불러오는 중 오류가 발생했습니다.</p>';
    }
}

// 상세 렌더링
function renderPostDetail(post) {
    const detailHtml = `
        <header class="post-detail-header">
            <div class="post-detail-title-row">
                <h1 class="post-detail-title">${post.title || '제목 없음'}</h1>
                <div class="post-detail-actions">
                    <button class="btn btn--secondary" id="btnPostEdit">수정</button>
                    <button class="btn btn--secondary" id="btnPostDelete">삭제</button>
                </div>
            </div>

            <div class="post-detail-meta">
                <span>작성자: ${post.writerNickname || '익명'}</span>
                <span>${post.createdAt || '날짜 정보 없음'}</span>
            </div>
        </header>

        <div class="post-detail-content">
            ${(post.content || '내용이 없습니다.').replace(/\n/g, '<br>')}
        </div>

        <div class="post-detail-stats">
            <div class="post-detail-stat">
                <div>${post.likeCount || 0}</div>
                <div>좋아요수</div>
            </div>
            <div class="post-detail-stat">
                <div>${post.viewCount || 0}</div>
                <div>조회수</div>
            </div>
            <div class="post-detail-stat">
                <div>${post.commentCount || 0}</div>
                <div>댓글</div>
            </div>
        </div>
    `;

    detailContainer.innerHTML = detailHtml;

    const btnPostDelete = document.getElementById('btnPostDelete');
    const btnPostEdit = document.getElementById('btnPostEdit');

    if (btnPostDelete) {
        btnPostDelete.addEventListener('click', () => {
            openPostDeleteModal();
        });
    }

    if (btnPostEdit) {
        btnPostEdit.addEventListener('click', () => {
            if (!currentPostId) return;

            const ok = window.confirm('게시글을 수정하시겠습니까?');
            if (!ok) return;

            window.location.href = `/posts/${currentPostId}/edit`;
        });
    }
}

// 게시글 삭제 모달
function setupPostDeleteModal() {
    if (btnPostDeleteCancel) {
        btnPostDeleteCancel.addEventListener('click', closePostDeleteModal);
    }

    if (btnPostDeleteConfirm) {
        btnPostDeleteConfirm.addEventListener('click', async () => {
            await deleteCurrentPost();
        });
    }

    postDeleteModal.addEventListener('click', (event) => {
        if (event.target === postDeleteModal) closePostDeleteModal();
    });
}

function openPostDeleteModal() {
    postDeleteModal.classList.add('is-open');
}

function closePostDeleteModal() {
    postDeleteModal.classList.remove('is-open');
}

async function deleteCurrentPost() {
    try {
        const response = await fetch(`${BASE_API_URL}/${currentPostId}`, {
            method: 'DELETE'
        });

        if (!response.ok) {
            throw new Error(`삭제 실패: ${response.status}`);
        }

        closePostDeleteModal();
        window.location.href = '/posts';
    } catch (error) {
        console.error('게시글 삭제 오류:', error);
        alert('게시글 삭제 중 오류가 발생했습니다.');
    }
}

/* ================================
   댓글 작성 / 조회 / 수정 / 삭제
================================ */

// ✅ 댓글 수 즉시 반영용 함수
function updateCommentCount(count) {
    // stats 순서: 좋아요, 조회수, 댓글
    const statEls = document.querySelectorAll(".post-detail-stat div:first-child");
    if (!statEls || statEls.length < 3) return;

    statEls[2].textContent = count;
}

// 댓글 폼 제출
function setupCommentForm() {
    if (!commentForm) return;

    commentForm.addEventListener('submit', async (event) => {
        event.preventDefault();

        const content = commentContentInput.value.trim();
        if (!content) {
            alert('댓글을 입력해 주세요.');
            return;
        }

        const payload = {
            writerId: 1, // TODO: 로그인 붙이면 실제 userId로 교체
            postId: Number(currentPostId),
            content: content
        };

        try {
            const response = await fetch(`/api/v1/posts/${currentPostId}/comments`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });

            if (!response.ok) throw new Error(`댓글 등록 실패`);

            commentContentInput.value = '';
            await fetchComments(currentPostId);
        } catch (error) {
            console.error(error);
            alert('댓글 등록 중 오류 발생');
        }
    });
}

// 댓글 목록 불러오기
async function fetchComments(postId) {
    if (!commentListContainer) return;

    try {
        const response = await fetch(`/api/v1/posts/${postId}/comments`);
        if (!response.ok) throw new Error('댓글 불러오기 실패');

        const comments = await response.json();
        renderComments(comments);

        // ✅ 댓글 수 즉시 반영
        updateCommentCount(comments.length);

    } catch (e) {
        commentListContainer.innerHTML = '<p>댓글 불러오기 오류</p>';
        updateCommentCount(0);
    }
}

// 댓글 렌더링
function renderComments(comments) {
    if (!comments || comments.length === 0) {
        commentListContainer.innerHTML = '<p>등록된 댓글이 없습니다.</p>';
        return;
    }

    commentListContainer.innerHTML = '';

    comments.forEach(comment => {
        const safeContent = escapeHtml(comment.content || '').replace(/\n/g, '<br>');

        commentListContainer.innerHTML += `
            <article class="comment-item" data-comment-id="${comment.id}">
                <div class="comment-item__header">
                    <span class="comment-item__nickname">${comment.writerNickname || '익명'}</span>
                    <span class="comment-item__date">${comment.createdAt || ''}</span>
                </div>

                <div class="comment-item__content">${safeContent}</div>

                <div class="comment-item__actions">
                    <button class="btn btn--secondary btn-comment-edit">수정</button>
                    <button class="btn btn--danger btn-comment-delete">삭제</button>
                </div>
            </article>
        `;
    });
}

// 댓글 수정/삭제 이벤트 위임
function setupCommentListEvents() {
    if (!commentListContainer) return;

    commentListContainer.addEventListener('click', (event) => {
        const commentEl = event.target.closest('.comment-item');
        if (!commentEl) return;

        const commentId = commentEl.dataset.commentId;
        if (!commentId) return;

        if (event.target.classList.contains('btn-comment-edit')) {
            enterCommentEditMode(commentEl);
        }

        if (event.target.classList.contains('btn-comment-delete')) {
            openCommentDeleteModal(commentId);
        }
    });
}

// 수정모드 진입
function enterCommentEditMode(commentEl) {
    const contentDiv = commentEl.querySelector('.comment-item__content');
    const actionsDiv = commentEl.querySelector('.comment-item__actions');

    if (commentEl.dataset.editing === 'true') return;
    commentEl.dataset.editing = 'true';

    const originalText = contentDiv.innerText;
    commentEl.dataset.originalHtml = contentDiv.innerHTML;

    contentDiv.innerHTML = `
        <textarea class="textarea comment-edit-textarea">${escapeHtml(originalText)}</textarea>
    `;

    actionsDiv.innerHTML = `
        <button class="btn btn--secondary btn-comment-cancel">취소</button>
        <button class="btn btn--primary btn-comment-save">저장</button>
    `;

    const textarea = contentDiv.querySelector('.comment-edit-textarea');

    actionsDiv.querySelector('.btn-comment-cancel').addEventListener('click', () => {
        exitCommentEditMode(commentEl, true);
    });

    actionsDiv.querySelector('.btn-comment-save').addEventListener('click', async () => {
        const newContent = textarea.value.trim();
        if (!newContent) {
            alert('내용을 입력해 주세요.');
            return;
        }
        await saveCommentEdit(commentEl.dataset.commentId, newContent);
    });
}

// 수정모드 종료
function exitCommentEditMode(commentEl, restoreOriginal) {
    const contentDiv = commentEl.querySelector('.comment-item__content');
    const actionsDiv = commentEl.querySelector('.comment-item__actions');

    if (restoreOriginal) {
        contentDiv.innerHTML = commentEl.dataset.originalHtml;
    }

    actionsDiv.innerHTML = `
        <button class="btn btn--secondary btn-comment-edit">수정</button>
        <button class="btn btn--danger btn-comment-delete">삭제</button>
    `;

    commentEl.dataset.editing = 'false';
}

// PATCH 수정 요청 (✅ 원본 CommentController 경로에 맞춤)
async function saveCommentEdit(commentId, newContent) {
    try {
        const response = await fetch(
            `/api/v1/posts/${currentPostId}/comments/${commentId}`,
            {
                method: 'PATCH',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ content: newContent })
            }
        );

        if (!response.ok) throw new Error('댓글 수정 실패');

        alert('댓글이 수정되었습니다.');
        await fetchComments(currentPostId);
    } catch (error) {
        console.error(error);
        alert('댓글 수정 중 오류 발생');
    }
}

/* =======================
   댓글 삭제 모달 처리
======================= */

function setupCommentDeleteModal() {
    if (!commentDeleteModal) return;

    if (btnCommentDeleteCancel)
        btnCommentDeleteCancel.addEventListener('click', closeCommentDeleteModal);

    if (btnCommentDeleteConfirm)
        btnCommentDeleteConfirm.addEventListener('click', async () => {
            if (!pendingDeleteCommentId) return;
            await deleteComment(pendingDeleteCommentId);
        });

    commentDeleteModal.addEventListener('click', (e) => {
        if (e.target === commentDeleteModal) closeCommentDeleteModal();
    });
}

function openCommentDeleteModal(commentId) {
    pendingDeleteCommentId = commentId;
    commentDeleteModal.classList.add('is-open');
}

function closeCommentDeleteModal() {
    pendingDeleteCommentId = null;
    commentDeleteModal.classList.remove('is-open');
}

// 실제 삭제 호출 (✅ 원본 CommentController 경로에 맞춤)
async function deleteComment(commentId) {
    try {
        const response = await fetch(
            `/api/v1/posts/${currentPostId}/comments/${commentId}`,
            { method: 'DELETE' }
        );

        if (!response.ok) throw new Error('댓글 삭제 실패');

        closeCommentDeleteModal();
        alert('댓글이 삭제되었습니다.');
        await fetchComments(currentPostId);
    } catch (e) {
        console.error('댓글 삭제 중 오류:', e);
        alert('댓글 삭제 오류');
    }
}

/* ============================
   HTML escape
============================ */
function escapeHtml(str) {
    return (str || '').replace(/[&<>"']/g, (ch) => {
        switch (ch) {
            case '&': return '&amp;';
            case '<': return '&lt;';
            case '>': return '&gt;';
            case '"': return '&quot;';
            case "'": return '&#39;';
            default: return ch;
        }
    });
}
