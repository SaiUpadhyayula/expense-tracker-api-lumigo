package com.programming.techie.expensetracker.repository;

import com.programming.techie.expensetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
