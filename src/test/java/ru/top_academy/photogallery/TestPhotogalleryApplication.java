package ru.top_academy.photogallery;

import org.springframework.boot.SpringApplication;

public class TestPhotogalleryApplication {

	public static void main(String[] args) {
		SpringApplication.from(PhotogalleryApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
