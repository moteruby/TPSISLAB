<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Регистрация</title>
    <style>

        body {
            display: flex;
            margin: 0;
            font-family: 'Roboto', sans-serif;
            height: 100vh;
            overflow: hidden;
            background-image: url('data:image/jpeg;base64,${bg123x}');
            background-size: cover;
            background-position: center;
            color: #fff;
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
        .form-title {
            font-size: 24px;
            margin-bottom: 20px;
        }
        .form-input {
            margin-bottom: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .form-input input {
            width: 300px;
            padding: 10px;
            border-radius: 5px;
            border: none;
            background-color: rgba(255, 255, 255, 0.1);
            color: #fff;
            margin-bottom: 10px;
        }
        .form-input input::placeholder {
            color: rgba(255, 255, 255, 0.5);
        }
        .form-row {
            display: flex;
            justify-content: space-between;
            width: 100%;
            max-width: 620px;
            gap: 20px; /* Добавлено расстояние между полями */
        }
        .form-row input {
            width: 48%;
        }
        .file-input {
            width: 48%;
            padding: 10px;
            border-radius: 5px;
            border: none;
            background-color: rgba(255, 255, 255, 0.1);
            color: #fff;
        }
        .register-button {
            background-color: #49c5b6;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            color: #fff;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s;
            margin-bottom: 10px;
        }
        .register-button:hover {
            background-color: #0288d1;
            transform: scale(1.05);
        }
        .login-button {
            background-color: transparent;
            padding: 10px 20px;
            border: 2px solid #49c5b6;
            border-radius: 5px;
            color: #49c5b6;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s, color 0.3s;
            text-decoration: none;
        }
        .login-button:hover {
            background-color: #49c5b6;
            color: #fff;
        }
        .error-container {
            background-color: #646464;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.1);
            display: inline-block;
        }
        .error-container h1, p {
            color: #000000;
        }
    </style>
</head>
<body>
<div class="main-content">
    <div class="content">
        <div class="form-title">
            Регистрация
        </div>
        <form action="/registration" method="post" enctype="multipart/form-data">
            <div class="form-input">
                <div class="form-row">
                    <input type="email" placeholder="Email" class="form-control" name="email" id="email" required>
                    <input type="password" placeholder="Пароль" class="form-control" name="password" id="password" required>
                </div>
                <div class="form-row">
                    <input type="text" placeholder="Имя" name="name" id="name" required>
                    <input type="file" class="file-input" name="images" id="images">
                </div>
                <input type="tel" placeholder="Номер телефона" class="form-control" name="phoneNumber" id="phoneNumber" pattern="\+375-\d{2}-\d{3}-\d{4}" title="Пожалуйста введите номер телефона по примеру: +375-__-___-____" required>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <button class="register-button" type="submit" class="btn btn-primary">Зарегистрироваться</button>
        </form>
        <a href="/login" class="login-button">Вход</a>
    </div>
    <#if errorMessage??>
        <div class="error-container">
            <h1>Ошибка</h1>
            <p>${errorMessage}</p>
        </div>
    </#if>
</div>
</body>
</html>
