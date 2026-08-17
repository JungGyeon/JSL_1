/* =========================================================
   공용 더미 데이터 (Kaggle anime-offline-database.jsonl 축약)
   실제 구현 시 Oracle ANIME / TAG / ANIME_TAG 테이블 조회로 대체
   ========================================================= */
const ANIME = [
  {id:1, title:"月影少女ユメ", type:"TV", episodes:24, status:"放送終了", season:"春", year:2021, score:8.7, duration:"24分", tags:["ファンタジー","魔法少女","冒険"]},
  {id:2, title:"鋼鉄狼騎士団", type:"TV", episodes:12, status:"放送終了", season:"秋", year:2022, score:8.1, duration:"23分", tags:["ロボット","戦争","アクション"]},
  {id:3, title:"サイバーガーデン", type:"TV", episodes:13, status:"放送中", season:"夏", year:2024, score:7.9, duration:"24分", tags:["SF","ロボット","ドラマ"]},
  {id:4, title:"放課後魔法部", type:"TV", episodes:12, status:"放送終了", season:"春", year:2020, score:8.4, duration:"23分", tags:["学園","魔法少女","コメディ"]},
  {id:5, title:"異世界パン屋アルケー", type:"TV", episodes:12, status:"放送終了", season:"冬", year:2023, score:8.0, duration:"22分", tags:["異世界","ファンタジー","日常"]},
  {id:6, title:"ブルーコート・ヴァルキリー", type:"劇場版", episodes:1, status:"放送終了", season:"夏", year:2019, score:8.9, duration:"105分", tags:["アクション","戦争","ドラマ"]},
  {id:7, title:"ゼロイニング", type:"TV", episodes:26, status:"放送終了", season:"春", year:2018, score:8.5, duration:"24分", tags:["スポーツ","学園","青春"]},
  {id:8, title:"黒井戸の錬金術師", type:"TV", episodes:24, status:"放送終了", season:"秋", year:2017, score:8.8, duration:"24分", tags:["ファンタジー","冒険","ダークファンタジー"]},
  {id:9, title:"ネオン・ライブラリー", type:"TV", episodes:12, status:"放送中", season:"冬", year:2025, score:7.6, duration:"23分", tags:["SF","ミステリー","日常"]},
  {id:10, title:"ラストオーダー:紅の軌道", type:"TV", episodes:24, status:"放送終了", season:"夏", year:2016, score:8.6, duration:"24分", tags:["ロボット","SF","戦争"]},
  {id:11, title:"モモの庭", type:"TV", episodes:12, status:"放送終了", season:"春", year:2022, score:8.2, duration:"23分", tags:["日常","癒し","ドラマ"]},
  {id:12, title:"体育館の異世界人", type:"TV", episodes:12, status:"放送中", season:"秋", year:2024, score:7.8, duration:"23分", tags:["異世界","スポーツ","コメディ"]},
];
const ALL_TAGS = [...new Set(ANIME.flatMap(a=>a.tags))].sort();

const GRADIENTS = [
  "linear-gradient(150deg,#3a2a6a,#151024)","linear-gradient(150deg,#1f6f66,#131a24)",
  "linear-gradient(150deg,#7a2d4a,#1a1224)","linear-gradient(150deg,#2d5a7a,#101828)",
  "linear-gradient(150deg,#6a4a1f,#1a140f)","linear-gradient(150deg,#4a2d7a,#12101f)",
  "linear-gradient(150deg,#1f4a5a,#0f1a20)","linear-gradient(150deg,#7a3d2d,#1f120e)",
];
function gradFor(id){ return GRADIENTS[id % GRADIENTS.length]; }

/**
 * 포스터 카드 HTML (그라디언트 목업 이미지)
 * @param {*} a 애니메이션 객체
 * @param {*} opts { hideFav: boolean, isFav: boolean }
 */
function posterHTML(a, opts={}){
  const isFav = !!opts.isFav;
  return `
    <div class="poster" style="background:${gradFor(a.id)}">
      <span class="glyph">${a.title.slice(0,1)}</span>
      <span class="score-badge">★ ${a.score.toFixed(1)}</span>
      ${opts.hideFav?'':`<button class="fav-btn ${isFav?'active':''}" data-fav-id="${a.id}" onclick="event.preventDefault(); event.stopPropagation(); if(typeof onFavClick==='function') onFavClick(${a.id});">${isFav?'♥':'♡'}</button>`}
      <div class="poster-title">${a.title}</div>
    </div>`;
}

/**
 * 목록/그리드용 카드. detailBasePath는 detail.html까지의 상대경로.
 * 예) 루트(index.html)에서 호출 시: "detail/detail.html"
 *     하위 폴더(list/, mypage/ 등)에서 호출 시: "../detail/detail.html"
 */
function cardHTML(a, detailBasePath, isFav){
  return `
    <div class="col-6 col-md-4 col-lg-3">
      <a class="anime-card" href="${detailBasePath}?id=${a.id}">
        ${posterHTML(a, {isFav: isFav})}
        <div class="meta">${a.type} · ${a.year}年 · 全${a.episodes}話</div>
      </a>
    </div>`;
}

/* 명세서 오버레이 토글 (요구사항 ID 배지 표시) */
function toggleSpec(){
  document.body.classList.toggle("spec-mode");
  const btn = document.getElementById("specToggleBtn");
  if(btn) btn.classList.toggle("on");
}
