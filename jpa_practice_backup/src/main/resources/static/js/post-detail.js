document.addEventListener('DOMContentLoaded', () => {
    fetchPostDetail();
});

const detailContainer = document.getElementById('post-detail-container');
const BASE_API_URL = '/api/v1/posts';

//URL에서 {postId} 추출
function getPostIdFromUrl() {
    // window.location.pathname: 현재 URL의 경로 (예: /posts/123)
    const pathSegments = window.location.pathname.split('/');
    // 배열의 마지막 요소가 ID
    return pathSegments[pathSegments.length - 1];
}

//게시글 상세 정보를 가져와 렌더링
async function fetchPostDetail() {
    const postId = getPostIdFromUrl();

    if (!postId || isNaN(postId)) {
        detailContainer.innerHTML = '<p>유효하지 않은 게시글 ID입니다.</p>';
        return;
    }

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
        console.error("게시글 상세 정보를 불러오는 데 실패했습니다:", error);
        detailContainer.innerHTML = '<p>데이터를 불러오는 중 오류가 발생했습니다.</p>';
    }
}

//상세 데이터를 HTML 구조에 맞게 렌더링
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
                <span>작성자: ${post.writer || '익명'}</span>
                <span>${post.createdAt || '날짜 정보 없음'}</span>
            </div>
        </header>

        <div class="post-detail-image-placeholder"></div>

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

    // TODO: 댓글 목록을 별도로 fetch하여 commentList 컨테이너에 렌더링하는 함수를 여기서 호출하기
    // fetchComments(postId);
}

// ... (fetchPostDetail 함수 정의) ...

// document.addEventListener('DOMContentLoaded', () => { fetchPostDetail(); });