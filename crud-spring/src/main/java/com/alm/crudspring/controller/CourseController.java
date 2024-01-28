package com.alm.crudspring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alm.crudspring.model.Course;
import com.alm.crudspring.repository.CourseRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
public class CourseController {

  @Autowired
  private final CourseRepository courseRepository;

  @GetMapping
  public List<Course> list() {
    return courseRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Course> findById(@PathVariable Long id) {
    return courseRepository.findById(id)
        .map(recordFound -> ResponseEntity.ok().body(recordFound))
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public Course create(@RequestBody Course course) {
    return courseRepository.save(course);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Course> update(@PathVariable Long id, @RequestBody Course course) {

    return courseRepository.findById(id)
        .map(recordFound -> {
          recordFound.setName(course.getName());
          recordFound.setCategory(course.getCategory());
          Course updated = courseRepository.save(recordFound);
          return ResponseEntity.ok().body(updated);
        })
        .orElse(ResponseEntity.notFound().build());
  }

}
