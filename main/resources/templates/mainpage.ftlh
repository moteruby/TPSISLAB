<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>EmotionRecognition</title>
    <style>
        body, html {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
            font-family: Arial, sans-serif;
            background: url('data:image/jpeg;base64,${bg123x}') no-repeat center center fixed;
            background-size: cover;
            color: white;
        }

        .overlay {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.8);
        }

        .container {
            position: relative;
            text-align: center;
            z-index: 1;
        }

        .title {
            font-size: 4em;
            margin-bottom: 80px;
        }

        .buttons {
            margin-top: 20px;
        }

        .buttons .btn {
            background: none;
            border: 2px solid white;
            color: white;
            padding: 10px 20px;
            margin: 0 10px;
            cursor: pointer;
            font-size: 1em;
            transition: background 0.3s ease, color 0.3s ease;
            border-radius: 20px;
            text-decoration: none;
        }

        .buttons .btn:hover {
            background: lightblue;
            color: black;
        }
    </style>
</head>
<body>
<div class="overlay"></div>
<div class="container">
    <div class="title">EmotionRecognition</div>
    <div class="buttons">
        <a href="/login" class="btn">Войти</a>
        <a href="/registration" class="btn">Зарегистрироваться</a>
    </div>
</div>
</body>
</html>
