package com.novatechzone.web.domain.solution;

import com.novatechzone.web.dto.ApplicationResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/solution")
public class SolutionResource {
    private final SolutionService solutionService;

    @PostMapping("/create")
    public ResponseEntity<ApplicationResponseDTO> createSolution(@Valid @RequestBody SolutionDTO solutionDTO) {
        return ResponseEntity.ok(solutionService.createSolution(solutionDTO));
    }

    @GetMapping("/get")
    public ResponseEntity<List<Solution>> getAllSolutions() {
        return ResponseEntity.ok(solutionService.getAllSolutions());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Solution> getSolutionById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(solutionService.getSolutionById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApplicationResponseDTO> updateSolution(@PathVariable("id") Long id, @Valid @RequestBody SolutionDTO solutionDTO) {
        return ResponseEntity.ok(solutionService.updateSolution(id, solutionDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApplicationResponseDTO> deleteSolution(@PathVariable("id") Long id) {
        return ResponseEntity.ok(solutionService.deleteSolution(id));
    }
}