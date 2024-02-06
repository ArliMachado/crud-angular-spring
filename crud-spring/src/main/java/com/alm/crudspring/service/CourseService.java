package com.alm.crudspring.service;

import com.alm.crudspring.dto.CourseDTO;
import com.alm.crudspring.dto.mapper.CourseMapper;
import com.alm.crudspring.exception.RecordNotFoundException;
import com.alm.crudspring.model.Course;
import com.alm.crudspring.repository.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Service
public class CourseService {

  private final CourseRepository repository;
  private final CourseMapper courseMapper;

  public CourseService(CourseRepository repository, CourseMapper courseMapper) {
    this.repository = repository;
    this.courseMapper = courseMapper;
  }

  public List<CourseDTO> list() {
    return repository.findAll()
        .stream()
        .map(courseMapper::toDTO)
        .toList();

  }

  public CourseDTO findById(@NotNull @Positive Long id) {
    return repository.findById(id).map(courseMapper::toDTO)
        .orElseThrow(() -> new RecordNotFoundException(id));
  }

  public CourseDTO create(@Valid CourseDTO course) {
    return courseMapper.toDTO(repository.save(courseMapper.toEntity(course)));
  }

  public CourseDTO update(@NotNull @Positive Long id, CourseDTO course) {

    return repository.findById(id)
        .map(recordFound -> {
          recordFound.setName(course.name());
          recordFound.setCategory(courseMapper.convertCategoryValue(course.category()));
          return courseMapper.toDTO(repository.save(recordFound));
        })
        .orElseThrow(() -> new RecordNotFoundException(id));
  }

  public void delete(@NotNull @Positive Long id) {
    repository.delete(repository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
  }

}
