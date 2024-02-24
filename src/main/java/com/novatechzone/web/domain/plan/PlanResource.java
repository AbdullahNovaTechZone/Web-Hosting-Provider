package com.novatechzone.web.domain.plan;

import com.novatechzone.web.dto.ApplicationResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/plan")
public class PlanResource {
    private final PlanService planService;

    @PostMapping("/create")
    public ResponseEntity<ApplicationResponseDTO> createPlan(@Valid @RequestBody PlanDTO planDTO) {
        return ResponseEntity.ok(planService.createPlan(planDTO));
    }

    @GetMapping("/get")
    public ResponseEntity<List<Plan>> getAllPlans() {
        return ResponseEntity.ok(planService.getAllPlans());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Plan> getPlanById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(planService.getPlanById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApplicationResponseDTO> updatePlan(@PathVariable("id") Long id, @Valid @RequestBody PlanDTO planDTO) {
        return ResponseEntity.ok(planService.updatePlan(id, planDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApplicationResponseDTO> deletePlan(@PathVariable("id") Long id) {
        return ResponseEntity.ok(planService.deletePlan(id));
    }
}
