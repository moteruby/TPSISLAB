<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Управление пользователями</title>
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
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            width: auto;
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
        .info-table {
            width: 100%;
            border-collapse: collapse;
        }
        .info-table th,
        .info-table td {
            border: 1px solid #fff;
            padding: 8px;
            text-align: center;
        }
        .info-table th {
            background-color: #49c5b6;
            color: #fff;
        }
        .info-table td button {
            padding: 5px 10px;
            border: none;
            border-radius: 5px;
            background-color: #49c5b6;
            color: #fff;
            cursor: pointer;
        }
        .info-table td button:hover {
            background-color: #0288d1;
        }
        .button {
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
        table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        th, td {
            padding: 12px 15px;
            border: 1px solid #ddd;
            text-align: left;
        }
        th {
            background-color: #49c5b6;
            color: #fff;
            font-weight: bold;
        }
        tr:nth-child(even) {
            background-color: rgba(255, 255, 255, 0.1);
        }
        tr:hover {
            background-color: rgba(255, 255, 255, 0.2);
        }
        h1 {
            margin-top: 40px;
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
        Панель администратора
        <a href="/contact-info">Контактная информация</a>
    </div>
    <div class="content">
        <table class="info-table">
            <thead>
            <tr>
                <th>Email</th>
                <th>Номер телефона</th>
                <th>Активность</th>
                <th>Роли</th>
                <th>Заблокировать</th>
                <th>Изменение роли</th>
                <th>Удаление пользователя</th>
                <th>Подробная информация</th>
            </tr>
            </thead>
            <tbody>
            <#if users?has_content>
                <#list users as user>
                    <tr>
                        <td>${user.email}</td>
                        <td>${user.phoneNumber}</td>
                        <td><#if user.active>true<#else>false</#if></td>
                        <td><#list user.roles as role>${role} </#list></td>
                        <td>
                            <#if user.id != admin.id>
                            <form action="/admin/user/ban/${user.id}" method="post">
                                <input type="hidden" name="_csrf" value="${_csrf.token}">
                                <input type="submit" class="button" value="Заблокировать">
                            </form>
                            </#if>
                        </td>
                        <td>
                            <#if user.id != admin.id>
                                <a class="button" href="/admin/user/edit/${user.id}">Изменить роль</a>
                            </#if>
                        </td>
                        <td>
                            <#if user.id != admin.id>
                                <form action="/admin/user/delete/${user.id}" method="post">
                                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                                    <input class="button" type="submit" class="btn btn-danger" value="Удалить пользователя">
                                </form>
                            </#if>
                        </td>
                        <td>
                            <a href="/user/${user.id}" class="button">Подробная информация</a>
                        </td>
                    </tr>
                </#list>
            <#else>
                <tr>
                    <td colspan="8"><h3>Пользователей нет</h3></td>
                </tr>
            </#if>
            </tbody>
        </table>
        <a href="/registration" class="button">Создать пользователя</a>
    </div>
</div>
</body>
</html>
