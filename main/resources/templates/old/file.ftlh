<!DOCTYPE html>
<html lang="en">
<head>
    <<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Распознавание Эмоций с Firebase</title>
</head>
<body>
<form method="POST" enctype="multipart/form-data" action="/detect-emotion"><!-- /send-image-->
    <input type="file" name="file" accept="image/*" required />
    <button type="submit">Detect Emotion</button>
</form>
</body>
</html>