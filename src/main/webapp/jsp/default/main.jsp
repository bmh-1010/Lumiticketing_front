<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>LUMITICKETING</title>

  <!-- Swiper CSS CDN -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css" />

  <style>
    .swiper {
      width: 309px;
      height: 490px;
      margin: auto;
    }

    .swiper-slide img {
      width: 309px;
      height: 490px;
      object-fit: cover;
    }

    .swiper-button-prev,
    .swiper-button-next {
      color: white;
    }

    .reserve-button {
      display: inline-block;
      margin-top: 20px;
      background-color: #ff4500;
      color: white;
      font-weight: bold;
      padding: 10px 20px;
      border-radius: 10px;
      text-decoration: none;
    }
  </style>
</head>
<body>

<div align="center">

  <!-- Swiper -->
  <div class="swiper">
    <div class="swiper-wrapper">
      <div class="swiper-slide">
        <img src="https://dccdqji0iinuw.cloudfront.net/banner1.png" alt="배너1" loading="eager">
      </div>
      <div class="swiper-slide">
        <img src="https://dccdqji0iinuw.cloudfront.net/banner2.png" alt="배너2" loading="eager">
      </div>
      <div class="swiper-slide">
        <img src="https://dccdqji0iinuw.cloudfront.net/banner3.png" alt="배너3" loading="eager">
      </div>
         <div class="swiper-slide">
        <img src="https://dccdqji0iinuw.cloudfront.net/banner4.png" alt="배너4" loading="eager">
      </div>
         <div class="swiper-slide">
        <img src="https://dccdqji0iinuw.cloudfront.net/banner5.png" alt="배너5" loading="eager">
      </div>
    </div>

    <!-- Navigation buttons -->
    <div class="swiper-button-prev"></div>
    <div class="swiper-button-next"></div>
  </div>

  <!-- 예매 버튼 -->
  <a href="${pageContext.request.contextPath}/ticketing" class="reserve-button">
    지금 바로 예매하러가기
  </a>
</div>

<!-- Swiper JS CDN -->
<script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>

<script>
  const swiper = new Swiper('.swiper', {
    loop: true,
    autoplay: {
      delay: 3000,
      disableOnInteraction: false,
    },
    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
    },
  });
</script>

</body>
</html>
