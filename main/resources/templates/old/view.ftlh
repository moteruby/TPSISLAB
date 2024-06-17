<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Распознавание Эмоций с Firebase</title>
</head>
<body>
<h1>EmotionsDf</h1>
<table>
    <thead>
    <tr>
        <th>EmotionDf</th>
        <th>ValueDf</th>
    </tr>
    </thead>
    <tbody>
    <#list emotionsDf as emotion>
        <tr>
            <td>${emotion}</td>
            <td>${valuesDf[emotion?index]}</td>
        </tr>
    </#list>
    </tbody>
</table>
<h1>EmotionsMl</h1>
<table>
    <thead>
    <tr>
        <th>EmotionML</th>
        <th>ValueML</th>
    </tr>
    </thead>
    <tbody>
    <#list emotionsMl as emotion>
        <tr>
            <td>${emotion}</td>
            <td>${valuesMl[emotion?index]}</td>
        </tr>
    </#list>
    </tbody>
</table>
</body>
</html>