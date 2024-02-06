package com.alm.crudspring.dto.mapper;

import com.alm.crudspring.dto.CourseDTO;
import com.alm.crudspring.dto.LessonDTO;
import com.alm.crudspring.enums.Category;
import com.alm.crudspring.model.Course;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseMapper {

  public CourseDTO toDTO(Course course) {
    if (course == null) {
      return null;
    }

    List<LessonDTO> lessons = course.getLessons()
        .stream()
        .map(lesson -> new LessonDTO(lesson.getId(), lesson.getName(), lesson.getYoutubeUrl()))
        .toList();
    return new CourseDTO(course.getId(), course.getName(), course.getCategory().getValue(), lessons);
  }

  public Course toEntity(CourseDTO courseDTO) {
    if (courseDTO == null) {
      return null;
    }

    Course course = new Course();
    if (courseDTO.id() != null) {
      course.setId(courseDTO.id());
    }

    course.setName(courseDTO.name());
    course.setCategory(convertCategoryValue(courseDTO.category()));
    return course;
  }

  public Category convertCategoryValue(String value) {
    if (value == null) {
      return null;
    }
    return switch (value) {
      case "Front-end" -> Category.FRONT_END;
      case "Back-end" -> Category.BACK_END;
      default -> throw new IllegalArgumentException("Categoria inválida: " + value);
    };
  }
}
