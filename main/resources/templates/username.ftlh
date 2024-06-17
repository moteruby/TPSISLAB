<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Пользователь</title>
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
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            text-align: center;
        }
        .user-image {
            margin-bottom: 20px;
        }
        .user-image img {
            width: 200px;
            height: auto;
            border-radius: 50%;
        }
        .user-name {
            font-size: 24px;
            margin-bottom: 20px;
        }
        .user-input {
            margin-bottom: 20px;
        }
        .user-input input {
            width: 300px;
            padding: 10px;
            border-radius: 5px;
            border: none;
            background-color: rgba(255, 255, 255, 0.1);
            color: #fff;
            margin-bottom: 10px;
        }
        .user-input input::placeholder {
            color: rgba(255, 255, 255, 0.5);
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
    <#if user.isAdmin()>
        <a href="/admin">
            Управлять пользователями
        </a>
    </#if>
</div>
<div class="main-content">
    <div class="navbar">
        ${user.name}
        <a href="/contact-info">Контактная информация</a>
    </div>
    <div class="content">
        <div class="user-image">
            <#if user.icon?? && user.icon?has_content>
                <img alt="${user.name}" src="data:image/jpeg;base64,${user.icon}" class="img-fluid">
            </#if>
        </div>
        <div class="user-name">
            ${user.name}
        </div>
        <form action="/acc/update" method="post" enctype="multipart/form-data">
            <div class="user-input">
                <input type="text" placeholder="Имя пользователя" name="name" id="name">
                <br>
                <input type="file" class="file-input" accept=".png,.jpeg,.jpg" name="icon" id="icon">
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <button type="submit" class="save-button">Сохранить</button>
        </form>
    </div>
</div>
</body>
</html>
