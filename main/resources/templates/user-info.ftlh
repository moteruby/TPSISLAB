<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Просмотр пользователя</title>
  <style>
    body {
      display: flex;
      margin: 0;
      font-family: 'Roboto', sans-serif;
      height: 100vh;
      overflow: hidden;
      background-image: url('data:image/jpeg;base64,${k09n}');
      background-size: cover;
      background-position: center;
      color: #fff;
    }
    .sidebar {
      width: 250px;
      background-color: #000;
      padding: 15px;
      box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
      display: flex;
      flex-direction: column;
      height: 100vh;
    }
    .sidebar img.logo {
      display: block;
      margin: 0 auto 10px;
      width: 100px;
      height: auto;
    }
    .sidebar h1 {
      text-align: center;
      font-size: 18px;
      margin-bottom: 20px;
    }
    .sidebar a.logo-link {
      text-align: center;
      margin-bottom: 20px;
      text-decoration: none;
    }
    .sidebar a {
      display: flex;
      align-items: center;
      padding: 10px;
      color: #fff;
      text-decoration: none;
      margin-bottom: 10px;
      transition: background-color 0.3s, transform 0.3s;
      border-radius: 8px;
    }
    .sidebar a:hover {
      background-color: #69d9ca;
      transform: scale(1.05);
    }
    .sidebar a img {
      margin-right: 10px;
      width: 20px;
      height: 20px;
    }
    .main-content {
      flex-grow: 1;
      display: flex;
      flex-direction: column;
      height: 100vh;
      background-color: rgba(0, 0, 0, 0.8);
      color: #fff;
    }
    .navbar {
      width: 100%;
      padding: 10px 15px;
      background-color: #49c5b6;
      box-shadow: 0 2px 5px rgba(0, 0, 0, 0.5);
      position: relative;
      display: flex;
      flex-direction: row;
      justify-content: space-between;
    }
    .navbar a {
      text-decoration: none;
      color: #fff;
      transition: color 0.3s;
    }
    .navbar a:hover {
      color: #0288d1;
    }
    .content {
      flex-grow: 1;
      padding: 15px;
      overflow-y: auto;
    }
    .user-info {
      margin-bottom: 20px;
    }
    .user-info h2 {
      margin-bottom: 10px;
    }
    .user-info p {
      margin: 5px 0;
    }
    .user-results {
      margin-top: 20px;
    }
    .user-results h3 {
      margin-bottom: 10px;
    }
    .content {
      flex-grow: 1;
      padding: 15px;
      overflow-y: auto;
    }
    .info-block {
      background-color: rgba(0, 0, 0, 0.75);
      padding: 20px;
      margin-bottom: 15px;
      box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
      border-left: 6px solid #0288d1;
      transition: transform 0.3s, box-shadow 0.3s;
      display: flex;
      align-items: center;
      justify-content: space-between;
    }
    .info-block img {
      width: 100px;
      height: auto;
      margin-right: 20px;
    }
    .info-block h2 {
      font-weight: bold;
    }
    .info-block p {
      font-size: 14px;
      margin-bottom: 10px;
    }
    .info-block .date {
      margin-top: auto;
      margin-right: 20px;
    }
    .info-block .button {
      background-color: #49c5b6;
      color: #fff;
      padding: 5px 10px;
      border-radius: 5px;
      cursor: pointer;
      text-decoration: none;
    }
    .button:hover{
      background-color: #69d9ca;
      transform: scale(1.05);
    }
    .user-image {
      margin-bottom: 20px;
    }
    .user-image img {
      width: 200px;
      height: auto;
      border-radius: 50%;
    }
  </style>
</head>
<body>
<div class="sidebar">
  <a href="/firstpage" class="logo-link">
    <img src="data:image/jpeg;base64,${nnep1}" alt="Logo" class="logo">
  </a>
  <h1>EmotionRecognition</h1>
  <a href="/firstpage">
    Главная
  </a>
  <a href="/acc">
    Имя
  </a>
  <a href="/new_images">
    Распознать эмоции
  </a>
  <a href="/view_results">
    Просмотр результатов
  </a>
  <#if admin.isAdmin()>
    <a href="/admin">
      Управлять пользователями
    </a>
  </#if>
</div>
<div class="main-content">
  <div class="navbar">
    Информация о пользователе
    <a href="/contact-info">Контактная информация</a>
  </div>
  <div class="content">
    <div class="user-info">
      <h2>Подробная информация о пользователе ${user.name}</h2>
      <#if user.icon?? && user.icon?has_content>
        <div class="user-image">
          <img alt="${user.name}" src="data:image/jpeg;base64,${user.icon}" class="img-fluid">
        </div>
      </#if>
      <p>Email: ${user.email}</p>
      <p>Номер телефона: ${user.phoneNumber}</p>
    </div>
    <div class="user-results">
      <h3>Результаты распознавания пользователя ${user.name}:</h3>
      <#if results?size gt 0>
        <#list results as result>
          <div class="content">
            <div class="info-block">
              <img alt="${result.title}" src="data:image/jpeg;base64,${result.image}" class="img-fluid">
              <div>
                <h2>${result.title}</h2>
                <p>${result.description}</p>
              </div>
              <div class="date">Дата: ${result.dateOfCreated}</div>
              <a href="/result/remove/${result.id}" class="button">Удалить</a>
            </div>
          </div>
        </#list>
      <#else>
        <div class="content">
          <div class="info-block">
            <h3>Нету результатов распознавания</h3>
          </div>
        </div>
      </#if>
    </div>
  </div>
</div>
</body>
</html>
