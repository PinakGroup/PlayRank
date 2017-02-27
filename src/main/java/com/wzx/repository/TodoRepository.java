package com.wzx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.wzx.domain.Todo;

/**
 * Created by arthurwang on 16/12/30.
 */
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

}