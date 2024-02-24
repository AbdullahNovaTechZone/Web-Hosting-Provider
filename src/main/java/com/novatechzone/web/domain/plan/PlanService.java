package com.novatechzone.web.domain.plan;

import com.novatechzone.web.domain.category.CategoryRepository;
import com.novatechzone.web.dto.ApplicationResponseDTO;
import com.novatechzone.web.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlanService {
    private final PlanRepository planRepository;
    private final CategoryRepository categoryRepository;
    private static final String INVALID_PLAN_ID = "INVALID_PLAN_ID";
    private static final String INVALID_PLAN_ID_MESSAGE = "Invalid Plan Id";

    public ApplicationResponseDTO createPlan(PlanDTO planDTO) {
        if (categoryRepository.findById(planDTO.getCategoryId()).isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_CATEGORY_ID", "Invalid Category Id");
        } else if (planRepository.findByCategoryIdAndPriceAndDuration(planDTO.getCategoryId(), planDTO.getPrice(), planDTO.getDuration()).isPresent()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "PLAN_ALREADY_EXIST", "Plan Already Exist");
        } else {
            planRepository.save(new Plan.PlanBuilder().categoryId(planDTO.getCategoryId()).price(planDTO.getPrice()).duration(planDTO.getDuration()).build());
            return new ApplicationResponseDTO(HttpStatus.CREATED, "PLAN_CREATED", "Plan Created Successfully");
        }
    }

    public List<Plan> getAllPlans() {
        List<Plan> plans = planRepository.findAll();
        if (plans.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.NOT_FOUND, "NO_PLANS_FOUND", "No Plans Found");
        }
        return plans;
    }

    public Plan getPlanById(Long id) {
        Optional<Plan> optionalPlan = planRepository.findById(id);
        if (optionalPlan.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.NOT_FOUND, INVALID_PLAN_ID, INVALID_PLAN_ID_MESSAGE);
        }
        return optionalPlan.get();
    }

    public ApplicationResponseDTO updatePlan(Long id, PlanDTO planDTO) {
        Optional<Plan> optionalPlan = planRepository.findById(id);
        if (optionalPlan.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.NOT_FOUND, INVALID_PLAN_ID, INVALID_PLAN_ID_MESSAGE);
        } else {
            Plan plan = optionalPlan.get();
            planRepository.findByCategoryIdAndPriceAndDuration(planDTO.getCategoryId(), planDTO.getPrice(), planDTO.getDuration()).ifPresent(existingPlan -> {
                if (!(plan.getCategoryId().equals(planDTO.getCategoryId()) && plan.getPrice().equals(planDTO.getPrice()) && plan.getDuration().equals(planDTO.getDuration()))) {
                    throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "PLAN_ALREADY_EXISTS", "Plan Already Exists");
                }
            });
            plan.setCategoryId(planDTO.getCategoryId());
            plan.setPrice(planDTO.getPrice());
            plan.setDuration(planDTO.getDuration());
            planRepository.save(plan);
            return new ApplicationResponseDTO(HttpStatus.OK, "PLAN_UPDATED", "Plan Updated Successfully");
        }
    }

    public ApplicationResponseDTO deletePlan(Long id) {
        Optional<Plan> optionalPlan = planRepository.findById(id);
        if (optionalPlan.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.NOT_FOUND, INVALID_PLAN_ID, INVALID_PLAN_ID_MESSAGE);
        }
        planRepository.deleteById(id);
        return new ApplicationResponseDTO(HttpStatus.OK, "PLAN_DELETED", "Plan Deleted Successfully");
    }
}