package com.alm.crudspring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.alm.crudspring.dto.CourseDTO;
import com.alm.crudspring.dto.CoursePageDTO;
import com.alm.crudspring.dto.mapper.CourseMapper;
import com.alm.crudspring.exception.RecordNotFoundException;
import com.alm.crudspring.model.Course;
import com.alm.crudspring.repository.CourseRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class CourseService {

  private final CourseRepository repository;
  private final CourseMapper courseMapper;

  public CourseService(CourseRepository repository, CourseMapper courseMapper) {
    this.repository = repository;
    this.courseMapper = courseMapper;
  }

  public CoursePageDTO list(int page, int size) {
    Page<Course> pageCourse = repository.findAll(PageRequest.of(page, size));
    List<CourseDTO> courses = pageCourse.get().map(courseMapper::toDTO).collect(Collectors.toList());

    return new CoursePageDTO(courses, pageCourse.getTotalElements(), pageCourse.getTotalPages());
    // return repository.findAll()
    // .stream()
    // .map(courseMapper::toDTO)
    // .toList();

  }

  public CourseDTO findById(@NotNull @Positive Long id) {
    return repository.findById(id).map(courseMapper::toDTO)
        .orElseThrow(() -> new RecordNotFoundException(id));
  }

  public CourseDTO create(@Valid CourseDTO course) {
    return courseMapper.toDTO(repository.save(courseMapper.toEntity(course)));
  }

  public CourseDTO update(@NotNull @Positive Long id, CourseDTO courseDTO) {

    return repository.findById(id)
        .map(recordFound -> {
          Course course = courseMapper.toEntity(courseDTO);
          recordFound.setName(courseDTO.name());
          recordFound.setCategory(courseMapper.convertCategoryValue(courseDTO.category()));
          recordFound.getLessons().clear();

          course.getLessons().forEach(recordFound.getLessons()::add);
          return courseMapper.toDTO(repository.save(recordFound));
        })
        .orElseThrow(() -> new RecordNotFoundException(id));
  }

  public void delete(@NotNull @Positive Long id) {
    repository.delete(repository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
  }

}
