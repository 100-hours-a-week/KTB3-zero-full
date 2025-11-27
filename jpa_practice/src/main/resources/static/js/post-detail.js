const POST_API_URL = '/api/v1/posts';

document.addEventListener('DOMContentLoaded', () => {
    const postId = extractPostId();
    if (!postId) {
        console.error("postId를 URL에서 찾지 못했습니다.");
        return;
    }

    fetchPostDetail(postId);
});

function extractPostId() {
    const parts = window.location.pathname.split('/');
    return parts[parts.length - 1];
}

async function fetchPostDetail(postId) {
    try {
        const response = await fetch(`${POST_API_URL}/${postId}`);
        if (!response.ok) throw new Error("게시글을 가져오지 못했습니다");

        const post = await response.json();
        renderPostDetail(post);

    } catch (err) {
        console.error(err);
        document.querySelector('#post-detail-container').innerHTML =
            `<p>게시글을 불러오는 중 오류가 발생했습니다.</p>`;
    }
}


// Label 매핑
const MOOD_LABELS = {
    QUIET: 'QUIET',
    BUZZY: 'BUZZY',
    LOCAL: 'LOCAL',
    TOURISTY: 'TOURISTY',
};

const THEME_LABELS = {
    FOOD: 'FOOD',
    ACCOMMODATION: 'ACCOMMODATION',
    TRANSPORT: 'TRANSPORT',
    PHOTO: 'PHOTO',
    ACTIVITY: 'ACTIVITY',
};


// 상세 렌더링
function renderPostDetail(post) {
    const container = document.getElementById('post-detail-container');

    // 이미지
    const imageSection = post.imageUrl
        ? `
            <div class="post-detail__image">
                <img src="${post.imageUrl}" alt="post image">
            </div>
        `
        : '';

    // mood 태그
    const moodTag = post.mood
        ? `<span class="tag tag--mood">${MOOD_LABELS[post.mood]}</span>`
        : '';

    // themes 태그 여러 개
    const themeTags = (post.themes || [])
        .map(t => `<span class="tag tag--theme">${THEME_LABELS[t]}</span>`)
        .join('');

    // 본문 줄바꿈 처리
    const contentHtml = (post.content || '').replace(/\n/g, "<br>");

    // 최종 렌더링
    container.innerHTML = `
        <article class="post-detail">
            ${imageSection}

            <header class="post-detail__header">
                <h1 class="post-detail__title">${post.title}</h1>

                <div class="post-detail__tags">
                    ${moodTag}
                    ${themeTags}
                </div>

                <div class="post-detail__meta">
                    <span>${post.createdAt || '날짜 정보 없음'}</span>
                </div>

                <div class="post-detail__author">
                    By ${post.writerNickname}
                </div>
            </header>

            <section class="post-detail__content">
                ${contentHtml}
            </section>
        </article>
    `;
}
