package com.agility.security.controller;

import com.agility.security.models.Course;
import com.agility.security.services.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class StudentController {

    Logger logger = LoggerFactory.getLogger(StudentController.class);

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

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

    /**
     * Clear basic auth cache in the browser after logout
     */
    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Logout -------------");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "Logout successfully";
    }

}
