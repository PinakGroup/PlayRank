package com.wzx.service;

import com.wzx.domain.Todo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author arthurwang
 * @date 2019/11/29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TodoServiceTest {
    @Autowired
    private TodoService todoService;

    @Test
    public void getAllTodo() {
        List<Todo> todos = this.todoService.getAllTodo();
        Assert.assertEquals("empty list", 0, todos.size());
    }

    @Test
    @Transactional
    public void save() {
        Todo todo = new Todo("test");
        this.todoService.save(todo);

        List<Todo> todos = this.todoService.getAllTodo();
        Assert.assertEquals("list contains one element", 1, todos.size());
    }
}