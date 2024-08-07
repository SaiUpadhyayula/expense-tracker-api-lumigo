package com.programming.techie.expensetracker.service;

import com.programming.techie.expensetracker.dto.ExpenseDto;
import com.programming.techie.expensetracker.exception.ExpenseNotFoundException;
import com.programming.techie.expensetracker.model.Expense;
import com.programming.techie.expensetracker.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public Long addExpense(ExpenseDto expenseDto) {
        Expense expense = mapFromDto(expenseDto);
        return expenseRepository.save(expense).getId();
    }

    public void updateExpense(ExpenseDto expenseDto) {
        Expense expense = mapFromDto(expenseDto);
        if(expenseDto.getId() == null) {
            throw new IllegalArgumentException("Expense ID is required");
        }
        Expense savedExpense = expenseRepository.findById(expenseDto.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                String.format("Cannot Find Expense by ID %s", expense.getId())));
        savedExpense.setExpenseName(expense.getExpenseName());
        savedExpense.setExpenseCategory(expense.getExpenseCategory());
        savedExpense.setExpenseAmount(expense.getExpenseAmount());

        expenseRepository.save(savedExpense);
    }

    public ExpenseDto getExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException(String.format("Cannot Find Expense by ID - %s", id)));
        return mapToDto(expense);
    }

    public List<ExpenseDto> getAllExpenses() {
        return expenseRepository.findAll()
                .stream()
                .map(this::mapToDto).toList();
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    private ExpenseDto mapToDto(Expense expense) {
        return ExpenseDto.builder()
                .id(expense.getId())
                .expenseName(expense.getExpenseName())
                .expenseCategory(expense.getExpenseCategory())
                .expenseAmount(expense.getExpenseAmount())
                .build();
    }

    private Expense mapFromDto(ExpenseDto expense) {
        return Expense.builder()
                .expenseName(expense.getExpenseName())
                .expenseCategory(expense.getExpenseCategory())
                .expenseAmount(expense.getExpenseAmount())
                .build();
    }
}
