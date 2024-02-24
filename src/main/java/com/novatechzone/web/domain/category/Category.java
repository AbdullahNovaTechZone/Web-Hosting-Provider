package com.novatechzone.web.domain.category;

import com.novatechzone.web.domain.solution.Solution;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "solution_id", nullable = false)
    private Long solutionId;
    @ManyToOne
    @JoinColumn(name = "solution_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Solution solution;
}