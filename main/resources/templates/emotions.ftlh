<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Просмотр результатов</title>
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
            z-index: 10;
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
        .image-container {
            margin: 20px auto 0;
            overflow: hidden;
            position: relative;
            padding-bottom: 50px;
        }
        .image-container img {
            width: 100%;
            height: 100%;
            display: block;
            margin: 0 auto;
        }
        .user-image img {
            width: 200px;
            height: auto;
            border-radius: 50%;
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
        .newbutton {
            background-color: #49c5b6;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            color: #fff;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s;
            margin-bottom: 10px;
            text-decoration: none;
        }
        .newbutton:hover {
            background-color: #0288d1;
            transform: scale(1.05);
        }
        .tables-container {
            display: flex;
            justify-content: space-around;
            width: 100%;
            max-width: 1200px;
        }
        .info-block {
            width: 100%;
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
        Результат
        <a href="/contact-info">Контактная информация</a>
    </div>
    <div class="content">
        <#if result?has_content>
            <#if result.title?has_content && result.image?has_content>
                <div class="image-container">
                    <img alt="${result.title}" src="data:image/jpeg;base64,${result.image}" class="img-fluid">
                </div>
            </#if>
            <#if result.emotionsDf?has_content && result.valuesDf?has_content && result.emotionsMl?has_content && result.valuesMl?has_content>
                <div class="info-block">
                    <h2>Распознанные эмоции</h2>
                    <div class="tables-container">
                        <div class="table-wrapper">
                            <h1>EmotionDf</h1>
                            <table class="info-table">
                                <thead>
                                <tr>
                                    <th>ЭмоцииDf</th>
                                    <th>ЗначенияDf</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list result.emotionsDf as emotion>
                                    <tr>
                                        <td>${emotion}</td>
                                        <td>${result.valuesDf[emotion?index] + "%"}</td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>
                        <div class="table-wrapper">
                            <h1>EmotionsMl</h1>
                            <table class="info-table">
                                <thead>
                                <tr>
                                    <th>ЭмоцииML</th>
                                    <th>ЗначенияML</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list result.emotionsMl as emotion>
                                    <tr>
                                        <td>${emotion}</td>
                                        <td>${result.valuesMl[emotion?index] * 100 + "%"}</td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <br>
            <#else>
                <#if result.title == "Нельзя распознать эмоции на изображении">
                    ${result.description}
                </#if>
                <br>
                <br>
                <br>
            </#if>
        <#else>
            Нет результата
        </#if>
        <div class="buttons">
            <a href="/result/remove/${result.id}" class="newbutton">Удалить</a>
            <a href="/result/download/${result.id}" class="newbutton">Загрузить</a>
        </div><br>
        <button onclick="window.history.back()" class="save-button">Назад</button>
    </div>
</div>
</body>
</html>
