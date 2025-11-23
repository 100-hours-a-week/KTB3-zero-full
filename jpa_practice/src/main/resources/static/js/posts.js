// src/main/resources/static/js/posts.js

const POST_API_URL = '/api/v1/posts';

// 페이지 로드 완료 시점에 실행
document.addEventListener('DOMContentLoaded', () => {
    const postListContainer = document.querySelector('.posts-list');

    if (!postListContainer) {
        console.error("'.posts-list' 요소를 찾을 수 없습니다. posts.html에 div.posts-list가 있는지 확인하세요.");
        return;
    }

    fetchPostList(postListContainer);
});

// 백엔드에서 게시글 목록 가져오기
async function fetchPostList(postListContainer) {
    try {
        const response = await fetch(POST_API_URL);

        if (!response.ok) {
            throw new Error(`HTTP 오류! 상태: ${response.status}`);
        }

        const posts = await response.json(); // JSON 배열
        renderPosts(posts, postListContainer);
    } catch (error) {
        console.error("게시글 목록을 불러오는 데 실패했습니다:", error);
        if (postListContainer) {
            postListContainer.innerHTML = '<p>데이터를 불러오는 중 오류가 발생했습니다.</p>';
        }
    }
}

// JSON 배열 → HTML 렌더링
function renderPosts(posts, postListContainer) {
    if (!postListContainer) {
        console.warn("renderPosts: postListContainer가 없습니다.");
        return;
    }

    if (!posts || posts.length === 0) {
        postListContainer.innerHTML = '<p>게시글이 없습니다. 첫 글을 작성해 보세요!</p>';
        return;
    }

    // 목록 내용을 비우고, 새 데이터 채우기
    postListContainer.innerHTML = '';

    // PostResponse: { id, title, content, writerId, writerNickname, ... }
    posts.forEach(post => {
        const postItemHtml = `
            <article class="post-item" data-id="${post.id}">
                <a href="/posts/${post.id}" class="post-item__link"></a>

                <div class="post-item__title">${post.title || '(제목 없음)'}</div>

                <div class="post-item__meta">
                    <span>${post.createdAt || '날짜 정보 없음'}</span>
                </div>

                <div class="post-item__writer-row">
                    <span>작성자: ${post.writerNickname || '익명'}</span>
                </div>
            </article>
        `;

        postListContainer.innerHTML += postItemHtml;
    });
}

