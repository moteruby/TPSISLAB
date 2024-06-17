<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<input type="file" id="imageUpload" accept="image/*" />
<button id="recognizeButton">Recognize Emotions</button>
<div id="result"></div>

<!-- Firebase App (the core Firebase SDK) is always required and must be listed first -->
<script src="https://www.gstatic.com/firebasejs/8.10.0/firebase-app.js"></script>
<script src="https://www.gstatic.com/firebasejs/8.10.0/firebase-storage.js"></script>
<script src="https://www.gstatic.com/firebasejs/8.10.0/firebase-ml.js"></script>

<script>
    // Firebase configuration
    const firebaseConfig = {
        apiKey: "AIzaSyCFM4MZEGd-NbMqVZ7-jOb7uD_QD_2XU_o",
        authDomain: "emorecognition-6add4.firebaseapp.com",
        projectId: "emorecognition-6add4",
        storageBucket: "emorecognition-6add4.appspot.com",
        messagingSenderId: "1073296253413",
        appId: "1:1073296253413:web:4d5f34ff7e009e87fa4f0c",
        measurementId: "G-E16Z8TEX21"
    };

    // Initialize Firebase
    firebase.initializeApp(firebaseConfig);
    const storage = firebase.storage();
    const ml = firebase.ml();

    let selectedFile = null;

    document.getElementById('imageUpload').addEventListener('change', function(event) {
        selectedFile = event.target.files[0];
        console.log('File selected:', selectedFile);
    });

    document.getElementById('recognizeButton').addEventListener('click', function() {
        if (!selectedFile) {
            alert('Please select an image file first.');
            return;
        }

        const storageRef = storage.ref('images/' + selectedFile.name);
        const uploadTask = storageRef.put(selectedFile);

        uploadTask.on('state_changed', function(snapshot) {
            // Observe state change events such as progress, pause, and resume
            console.log('Upload progress:', (snapshot.bytesTransferred / snapshot.totalBytes) * 100);
        }, function(error) {
            // Handle unsuccessful uploads
            console.error('Upload failed:', error);
        }, function() {
            // Handle successful uploads
            uploadTask.snapshot.ref.getDownloadURL().then(function(downloadURL) {
                console.log('File available at', downloadURL);

                // Prepare the ML Kit Image object
                const image = ml.vision.imageFromUri(downloadURL);

                // Use Face Detector for emotion recognition
                const faceDetectorOptions = {
                    modeType: ml.vision.FaceDetectorOptions.FAST,
                    landmarkType: ml.vision.FaceDetectorOptions.ALL_LANDMARKS,
                    classificationType: ml.vision.FaceDetectorOptions.ALL_CLASSIFICATIONS,
                    minFaceSize: 0.1,
                    isTrackingEnabled: true
                };

                const faceDetector = ml.vision.faceDetector(faceDetectorOptions);

                faceDetector.process(image)
                    .then(faces => {
                        const emotions = faces.map(face => ({
                            smilingProbability: face.smilingProbability,
                            leftEyeOpenProbability: face.leftEyeOpenProbability,
                            rightEyeOpenProbability: face.rightEyeOpenProbability
                        }));

                        console.log('Detected emotions:', emotions);

                        // Display the result in the HTML
                        document.getElementById('result').innerText = JSON.stringify(emotions, null, 2);

                        // Send the result to Spring controller
                        fetch('/emotion-recognition', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify({ emotions })
                        })
                            .then(response => response.json())
                            .then(data => {
                                console.log('Success:', data);
                            })
                            .catch((error) => {
                                console.error('Error:', error);
                            });
                    })
                    .catch(error => {
                        console.error('Error processing image:', error);
                    });
            });
        });
    });
</script>
</body>
</html>