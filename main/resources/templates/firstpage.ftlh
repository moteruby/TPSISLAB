<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Веб-страница с боковым меню</title>
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
            margin-right: auto;
            padding-left: 15px;
            margin-left: 1000px;
        }
        .navbar a:hover {
            color: #0288d1;
        }
        .content {
            flex-grow: 1;
            padding: 15px;
            overflow-y: auto;
        }
        .info-block {
            background-color: rgba(0, 0, 0, 0.7);
            padding: 20px;
            margin-bottom: 15px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            border-left: 6px solid #0288d1;
            transition: transform 0.3s, box-shadow 0.3s;
        }
        .info-block h2 {
            color: #ffffff;
        }
        .info-block p {
            color: #49c5b6;
        }
        .info-block:nth-child(2n) {
            border-left-color: #ff4081;
        }
        .info-block:nth-child(3n) {
            border-left-color: #8bc34a;
        }
        .info-block:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15);
        }
        .save-button {
            background-color: #49c5b6;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            color: #fff;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .save-button:hover {
            background-color: #0288d1;
        }

        @media only screen and (max-width: 600px) {
            .navbar a {
                margin-left: 10px;
            }
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
    <#if usert.isAdmin()>
        <a href="/admin">
            Управлять пользователями
        </a>
    </#if>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" class="save-button" value="Выйти">
    </form>
</div>
<div class="main-content">
    <div class="navbar">
        Главная<a href="/contact-info">Контактная информация</a>
    </div>
    <div class="content">
        <div class="info-block">
            <h2>Пользователи</h2>
            <p>Сколько пользователей в веб-приложении: ${user}</p>
        </div>
        <div class="info-block">
            <h2>Эмоции</h2>
            <p>Сколько эмоций распознано: ${result}</p>
        </div>
        <div class="info-block">
            <h2>Распознавание</h2>
            <p>Чтобы начать распознавание, нажми "Распознать эмоции"</p>
        </div>
    </div>
</div>
</body>
</html>

