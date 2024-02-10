package com.alm.crudspring;

import com.alm.crudspring.enums.Category;
import com.alm.crudspring.model.Lesson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.alm.crudspring.model.Course;
import com.alm.crudspring.repository.CourseRepository;

@SpringBootApplication
public class CrudSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudSpringApplication.class, args);
	}

	@Bean
	@Profile("dev")
	CommandLineRunner initDatabase(CourseRepository courseRepository) {
		return args -> {
			courseRepository.deleteAll();

			for (int i = 0; i < 30; i++) {

				Course c = new Course();

				c.setName("Angular com Spring " + i);
				c.setCategory(Category.FRONT_END);

				Lesson lesson = new Lesson();
				lesson.setName("Introdução");
				lesson.setYoutubeUrl("wfsdfsdf");
				lesson.setCourse(c);
				c.getLessons().add(lesson);

				Lesson lesson2 = new Lesson();
				lesson2.setName("Angular ");
				lesson2.setYoutubeUrl("wdddasd");
				lesson2.setCourse(c);
				c.getLessons().add(lesson2);

				courseRepository.save(c);
			}
		};

	}

}
