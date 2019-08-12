package com.wzx.service;

import com.wzx.domain.Todo;

import java.util.List;

/**
 * @author arthurwang
 * @date 2019-08-11
 */
public interface TodoService {
    List<Todo> getAllTodo();

    Todo findById(Long id);

    Todo save(Todo todo);

    void deleteById(Long id);
}
