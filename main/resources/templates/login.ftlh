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
        .login-button {
            background-color: #49c5b6;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            color: #fff;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .login-button:hover {
            background-color: #0288d1;
            transform: scale(1.05);
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
            text-decoration: none;
        }
        .register-button:hover {
            background-color: #0288d1;
            transform: scale(1.05);
        }
        .error-container {
            background-color: #ffffff;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.1);
            display: inline-block;
        }
    </style>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="main-content">
    <div class="content">
        <div class="form-title">
            Вход
        </div>
        <form action="/login" method="post">
            <div class="form-input">
                <input type="email" placeholder="Email" class="form-control" id="username" name="username" required>
                <input type="password" placeholder="Пароль" class="form-control" id="password" name="password" required>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <button type="submit" class="login-button">Войти</button>
        </form>
        <br>
        <a href="/registration" class="register-button">Зарегистрироваться</a>
    </div>
    <#if errorMessage??>
        <div class="error-container">
            <h1>Error</h1>
            <p>${errorMessage}</p>
        </div>
    </#if>
</div>
</body>
</html>
