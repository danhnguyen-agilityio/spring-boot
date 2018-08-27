package com.agility.security.controller;

import com.agility.security.models.Course;
import com.agility.security.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students/{studentId}/courses")
    public List<Course> retrieveCoursesForStudent(@PathVariable String studentId) {
        return studentService.retrieveCourses(studentId);
    }

    @GetMapping("/manager/{studentId}/courses")
    public List<Course> retrieveCoursesForStudentByManager(@PathVariable String studentId) {
        return studentService.retrieveCourses(studentId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/{studentId}/courses")
    public List<Course> retrieveCoursesForStudentByAdmin(@PathVariable String studentId) {
        return studentService.retrieveCourses(studentId);
    }

    @GetMapping("/anonymous/{studentId}/courses")
    public List<Course> retrieveCoursesForStudentByAnonymous(@PathVariable String studentId) {
        return studentService.retrieveCourses(studentId);
    }

    @GetMapping("/multi/{studentId}/courses")
    public List<Course> retriveCoursesByRoleManagerAndAdmin(@PathVariable String studentId) {
        return studentService.retrieveCoursesByRoleManagerAndAdmin(studentId);
    }

}
