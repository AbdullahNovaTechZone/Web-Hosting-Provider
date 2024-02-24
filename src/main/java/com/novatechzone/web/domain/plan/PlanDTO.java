package com.novatechzone.web.domain.plan;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlanDTO {
    @NotNull
    private Long categoryId;
    @NotNull
    private Double price;
    @NotNull
    private Double duration;
}
