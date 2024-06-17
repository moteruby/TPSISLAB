package com.example.ERT.repositories;

import com.example.ERT.models.Result;
import com.example.ERT.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findResultsByUser(User user);

    @Query("SELECT COUNT(r) FROM Result r")
    long countResults();

    List<Result> findAllByUser(User user);

    Result findResultById(Long resultId);
}
