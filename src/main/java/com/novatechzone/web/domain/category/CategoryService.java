package com.novatechzone.web.domain.category;

import com.novatechzone.web.domain.solution.SolutionRepository;
import com.novatechzone.web.dto.ApplicationResponseDTO;
import com.novatechzone.web.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final SolutionRepository solutionRepository;
    private static final String INVALID_CATEGORY_ID = "INVALID_CATEGORY_ID";
    private static final String INVALID_CATEGORY_ID_MESSAGE = "Invalid Category Id";

    public ApplicationResponseDTO createCategory(CategoryDTO categoryDTO) {
        if (solutionRepository.findById(categoryDTO.getSolutionId()).isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_SOLUTION_ID", "Invalid Solution Id");
        } else if (categoryRepository.findByNameAndSolutionId(categoryDTO.getName(), categoryDTO.getSolutionId()).isPresent()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "CATEGORY_ALREADY_EXIST", "Category Already Exist");
        } else {
            categoryRepository.save(new Category.CategoryBuilder().name(categoryDTO.getName()).solutionId(categoryDTO.getSolutionId()).build());
            return new ApplicationResponseDTO(HttpStatus.CREATED, "CATEGORY_CREATED", "Category Created Successfully");
        }
    }

    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.NOT_FOUND, "NO_CATEGORIES_FOUND", "No Categories Found");
        }
        return categories;
    }

    public Category getCategoryById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.NOT_FOUND, INVALID_CATEGORY_ID, INVALID_CATEGORY_ID_MESSAGE);
        }
        return optionalCategory.get();
    }

    public ApplicationResponseDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.NOT_FOUND, INVALID_CATEGORY_ID, INVALID_CATEGORY_ID_MESSAGE);
        } else {
            Category category = optionalCategory.get();
            categoryRepository.findByNameAndSolutionId(categoryDTO.getName(), categoryDTO.getSolutionId()).ifPresent(existingCategory -> {
                if (!(category.getName().equals(categoryDTO.getName()) && category.getSolutionId().equals(categoryDTO.getSolutionId()))) {
                    throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "CATEGORY_ALREADY_EXISTS", "Category Already Exists");
                }
            });
            category.setName(categoryDTO.getName());
            category.setSolutionId(categoryDTO.getSolutionId());
            categoryRepository.save(category);
            return new ApplicationResponseDTO(HttpStatus.OK, "CATEGORY_UPDATED", "Category Updated Successfully");
        }
    }

    public ApplicationResponseDTO deleteCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.NOT_FOUND, INVALID_CATEGORY_ID, INVALID_CATEGORY_ID_MESSAGE);
        }
        categoryRepository.deleteById(id);
        return new ApplicationResponseDTO(HttpStatus.OK, "CATEGORY_DELETED", "Category Deleted Successfully");
    }
}
