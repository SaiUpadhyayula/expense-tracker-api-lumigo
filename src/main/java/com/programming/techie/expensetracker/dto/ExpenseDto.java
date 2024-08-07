package com.programming.techie.expensetracker.dto;

import com.programming.techie.expensetracker.model.ExpenseCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseDto {
    private Long id;
    @NotBlank(message = "Expense Name is required")
    private String expenseName;
    private ExpenseCategory expenseCategory;
    @Min(value = 0, message = "Expense Amount must be greater than 0")
    private BigDecimal expenseAmount;
}
