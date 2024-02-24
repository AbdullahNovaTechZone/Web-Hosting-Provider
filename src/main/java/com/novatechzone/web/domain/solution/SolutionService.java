package com.novatechzone.web.domain.solution;

import com.novatechzone.web.dto.ApplicationResponseDTO;
import com.novatechzone.web.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SolutionService {
    private final SolutionRepository solutionRepository;
    private static final String INVALID_SOLUTION_ID = "INVALID_SOLUTION_ID";
    private static final String INVALID_SOLUTION_ID_MESSAGE = "Invalid Solution Id";

    public ApplicationResponseDTO createSolution(SolutionDTO solutionDTO) {
        if (solutionRepository.findByName(solutionDTO.getName()).isPresent()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "SOLUTION_ALREADY_EXIST", "Solution Already Exist");
        } else {
            solutionRepository.save(new Solution.SolutionBuilder().name(solutionDTO.getName()).build());
            return new ApplicationResponseDTO(HttpStatus.CREATED, "SOLUTION_CREATED", "Solution Created Successfully");
        }
    }

    public List<Solution> getAllSolutions() {
        List<Solution> solutions = solutionRepository.findAll();
        if (solutions.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.NOT_FOUND, "NO_SOLUTIONS_FOUND", "No Solutions Found");
        }
        return solutions;
    }

    public Solution getSolutionById(Long id) {
        Optional<Solution> optionalSolution = solutionRepository.findById(id);
        if (optionalSolution.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.NOT_FOUND, INVALID_SOLUTION_ID, INVALID_SOLUTION_ID_MESSAGE);
        }
        return optionalSolution.get();
    }

    public ApplicationResponseDTO updateSolution(Long id, SolutionDTO solutionDTO) {
        Optional<Solution> optionalSolution = solutionRepository.findById(id);
        if (optionalSolution.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.NOT_FOUND, INVALID_SOLUTION_ID, INVALID_SOLUTION_ID_MESSAGE);
        } else {
            Solution solution = optionalSolution.get();
            solutionRepository.findByName(solutionDTO.getName()).ifPresent(existingSolution -> {
                if (!solution.getName().equals(solutionDTO.getName())) {
                    throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "NAME_ALREADY_EXISTS", "Name Already Exists");
                }
            });
            solution.setName(solutionDTO.getName());
            solutionRepository.save(solution);
            return new ApplicationResponseDTO(HttpStatus.OK, "SOLUTION_UPDATED", "Solution Updated Successfully");
        }
    }

    public ApplicationResponseDTO deleteSolution(Long id) {
        Optional<Solution> optionalSolution = solutionRepository.findById(id);
        if (optionalSolution.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.NOT_FOUND, INVALID_SOLUTION_ID, INVALID_SOLUTION_ID_MESSAGE);
        }
        solutionRepository.deleteById(id);
        return new ApplicationResponseDTO(HttpStatus.OK, "SOLUTION_DELETED", "Solution Deleted Successfully");
    }
}