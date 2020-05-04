package com.wzx.service.impl;

import com.wzx.model.Todo;
import com.wzx.repository.TodoRepository;
import com.wzx.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author arthurwang
 * @date 2019-08-11
 */
@Service
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public List<Todo> getAllTodo() {
        return todoRepository.findAll();
    }

    @Override
    @Cacheable(value = "todo", key = "#id")
    public Todo findById(Long id) {
        Optional<Todo> todo = todoRepository.findById(id);
        return todo.orElse(null);
    }

    @Override
    @CachePut(value = "todo", key = "#todo.id")
    public Todo save(Todo todo) {
        return this.todoRepository.save(todo);
    }

    @Override
    @CacheEvict(value = "todo", key = "#id")
    public void deleteById(Long id) {
        this.todoRepository.deleteById(id);
    }
}
