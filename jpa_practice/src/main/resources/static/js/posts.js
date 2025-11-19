document.addEventListener('DOMContentLoaded', () => {
    fetchPostList();
});

const POST_API_URL = '/api/v1/posts'; // 백엔드 API URL
const postListContainer = document.querySelector('.posts-list'); // HTML의 게시글 목록 컨테이너

//백엔드에서 게시글 목록 가져오기
async function fetchPostList() {
    try {
        const response = await fetch(POST_API_URL);
        if (!response.ok) {
            throw new Error(`HTTP 오류! 상태: ${response.status}`);
        }
        const posts = await response.json(); //JSON 객체로 변환
        renderPosts(posts);
    } catch (error) {
        console.error("게시글 목록을 불러오는 데 실패했습니다:", error);
        postListContainer.innerHTML = '<p>데이터를 불러오는 중 오류가 발생했습니다.</p>';
    }

    function renderPosts(posts) { //JSON 객체 -> 렌더링 반환
        if (posts.length === 0) {
            postListContainer.innerHTML = '<p>게시글이 없습니다. 첫 글을 작성해 보세요!</p>';
            return;
        }

        // 목록 내용을 비우고, 새 데이터 채우기 - 예시 데이터를 제거
        postListContainer.innerHTML = '';

  posts.forEach(post => {
          const postItem = `
              <article class="post-item" data-id="${post.id}">
                  <a href="/posts/${post.id}" class="post-item__link"></a>

                  <div class="post-item__title">${post.title}</div>

                  <div class="post-item__meta">
                      <span>${post.createdAt || '날짜 정보 없음'}</span>
                      </div>
                  <div class="post-item__writer-row">
                      <span>작성자: ${post.writer || '익명'}</span>
                  </div>
              </article>
          `;

          postListContainer.innerHTML += postItem;
      });

    }
}