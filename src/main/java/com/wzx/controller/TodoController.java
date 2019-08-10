package com.wzx.controller;

import com.wzx.domain.Todo;
import com.wzx.repository.TodoRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by arthurwang on 16/12/30.
 */
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final Logger logger = LoggerFactory.getLogger(TodoController.class);

    @Resource
    private TodoRepository todoRepository;

    @ApiOperation(value = "获取TodoList")
    @RequestMapping(method = RequestMethod.GET)
    public List<Todo> list() {
        return this.todoRepository.findAll();
    }

    @ApiOperation(value = "获取Todo详细信息", notes = "根据url的id来获取Todo详细信息")
    @ApiImplicitParam(name = "id", value = "TodoID", required = true, paramType = "path", dataType = "Long")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Todo get(@PathVariable Long id) {
        Todo todo = this.todoRepository.findById(id).get();
        return todo;
    }

    @ApiOperation(value = "创建Todo", notes = "根据Todo对象创建")
    @ApiImplicitParam(name = "content", value = "Todo内容字符", required = true, dataType = "String")
    @RequestMapping(method = RequestMethod.POST)
    public Todo create(@RequestBody String content) {
        return this.todoRepository.save(new Todo(content));
    }

    @ApiOperation(value = "更新Todo详细信息", notes = "根据url的id来指定更新对象，并根据传过来的todo信息来更新详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "TodoID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "entity", value = "Todo详细实体entity", required = true, dataType = "Todo")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Todo update(@PathVariable Long id, @RequestBody Todo entity) {
        logger.debug("update() of id#{} with body {}", id, entity);
        return this.todoRepository.save(entity);
    }

    @ApiOperation(value = "删除Todo", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "TodoID", required = true, dataType = "Long")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        this.todoRepository.deleteById(id);
    }
}
