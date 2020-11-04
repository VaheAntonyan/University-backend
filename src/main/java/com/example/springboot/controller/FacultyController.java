package com.example.springboot.controller;

import com.example.springboot.exception.ResourceNotFoundException;
import com.example.springboot.model.Faculty;
import com.example.springboot.repository.FacultyRepository;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/v1/")
public class FacultyController {
    @Autowired
    private FacultyRepository facultyRepository;

    @GetMapping("/faculties")
    public List<Faculty> getAllFaculties() {
        return this.facultyRepository.findAll();
    }

    @PostMapping("/faculties")
    public Faculty createFaculty(@Valid @RequestBody Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @PutMapping("/faculties/{id}")
    public ResponseEntity<Faculty> updateStudent(@PathVariable Long id, @RequestBody Faculty requestedFaculty) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not exist with id :" + id));

        faculty.setName(requestedFaculty.getName());

        Faculty updatedFaculty = facultyRepository.save(faculty);
        return ResponseEntity.ok(updatedFaculty);
    }

    @DeleteMapping("/faculties/{id}")
    public ResponseEntity<Boolean> deleteFaculty(@PathVariable Long id) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not exist with id :" + id));

        facultyRepository.delete(faculty);
        return ResponseEntity.ok(true);
    }
}
