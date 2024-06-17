<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Распознавание эмоций</title>
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
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            position: relative;
            display: flex;
            justify-content: space-between;
            align-items: center;
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
        .upload-block {
            background-color: rgba(0, 0, 0, 0.7);
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        .upload-block p {
            text-align: center;
            margin-bottom: 10px;
        }
        .upload-block button {
            display: block;
            margin: 0 auto;
            background-color: #49c5b6;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 20px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .upload-block button:hover {
            background-color: #69d9ca;
            transform: scale(1.05);
        }
        .file-list {
            list-style-type: none;
            padding: 0;
        }
        .file-item {
            display: flex;
            align-items: center;
            justify-content: space-between;
            border-bottom: 1px solid rgba(255, 255, 255, 0.3);
            padding: 10px 0;
        }
        .file-item:last-child {
            border-bottom: none;
        }
        .file-info {
            flex-grow: 1;
            margin-right: 10px;
        }
        .file-info span {
            font-size: 14px;
        }
        .file-item button, a {
            background-color: transparent;
            color: #fff;
            border: none;
            cursor: pointer;
            transition: color 0.3s;
        }
        .file-item button:hover {
            color: #ff4081;
        }
        .popup {
            display: none;
            position: fixed;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
            padding: 20px;
            background-color: white;
            border: 1px solid #ccc;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            z-index: 1000;
        }
        .popup-overlay {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.5);
            z-index: 999;
        }
        .custom-file-upload {
            display: inline-block;
            padding: 10px 20px;
            cursor: pointer;
            background-color: #49c5b6;
            color: #fff;
            border: none;
            border-radius: 20px;
            transition: background-color 0.3s;
        }

        .custom-file-upload:hover {
            background-color: #69d9ca;
        }
        .loading-message {
             display: none;
             position: fixed;
             top: 50%;
             left: 50%;
             transform: translate(-50%, -50%);
             padding: 20px;
             background-color: rgba(0, 0, 0, 0.8);
             color: #fff;
             border-radius: 8px;
             z-index: 1001;
             text-align: center;
         }
    </style>
    <script>
        function showLoadingMessage() {
            document.getElementById('loading-message').style.display = 'block';
        }
    </script>
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
        Распознать эмоции
        <a href="/contact-info">Контактная информация</a>
    </div>
    <div class="content">
        <!-- Блок для загрузки файла -->
        <div class="upload-block">
            <form method="POST" enctype="multipart/form-data" action="/detect-emotion" onsubmit="showLoadingMessage()">
                <b><p>Загрузка файла</p></b>
                <p>Загрузите файлы сюда</p>
                <p>Поддерживаемые форматы: <b>PNG, JPG</b></p>
                <input type="file" name="file" accept=".png,.jpeg,.jpg" required />
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <button type="submit">Распознать эмоции</button>
            </form>
        </div>
        <!-- Список загруженных файлов -->
        <h2>Загруженные файлы:</h2>
        <ul class="file-list">
            <#if results?has_content>
                <#list results as result>
                    <div class="file-item">
                        <div class="file-info">
                            <strong>${result.title}</strong>
                            <span>Дата загрузки: ${result.dateOfCreated}</span>
                        </div>
                    </div>
                </#list>
            <#else>
                <br>
                <div class="file-item">
                    <div class="file-info">
                        <h3>Нет загруженных файлов</h3>
                    </div>
                </div>
            </#if>
        </ul>
    </div>
    <div id="loading-message" class="loading-message">
        Пожалуйста, подождите, происходит распознавание эмоций...
    </div>
</div>
</body>
</html>
