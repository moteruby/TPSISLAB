package com.example.ERT;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ErtApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErtApplication.class, args);
	}
	/*@Bean
	public FirebaseApp initializeFirebase() throws IOException {
		InputStream serviceAccount = new ClassPathResource("emorecognition-6add4-firebase-adminsdk-56tnm-b022b41f28.json").getInputStream();
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.build();
		return FirebaseApp.initializeApp(options);
	}*/
}
