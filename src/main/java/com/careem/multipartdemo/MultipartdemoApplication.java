package com.careem.multipartdemo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@RestController
public class MultipartdemoApplication {

	@PostMapping(value = "/upload", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TestRequest> upload(@RequestBody() TestRequest request) {
		return ResponseEntity.ok(request);
	}

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<List<FileDebug>> upload(@RequestPart("request") TestRequest request,
			@RequestPart(value = "attach") List<MultipartFile> files) {
		List<FileDebug> list = Optional.ofNullable(files)
				.orElse(Collections.emptyList())
				.stream()
				.map(f -> {
					try {
						return new FileDebug(f.getOriginalFilename(), f.getContentType(), new String(f.getBytes(),
								StandardCharsets.UTF_8));
					} catch (IOException e) {
						throw new RuntimeException("failed to read file ");
					}
				})
				.collect(Collectors.toList());

		return ResponseEntity.ok(list);
	}

	public static void main(String[] args) {
		SpringApplication.run(MultipartdemoApplication.class, args);
	}
}

class FileDebug {
	private String name;
	private String contentType;
	private String content;

	public FileDebug(String name, String contentType, String content) {
		this.name = name;
		this.contentType = contentType;
		this.content = content;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

class TestRequest {
	private String a;
	private String b;

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}
}