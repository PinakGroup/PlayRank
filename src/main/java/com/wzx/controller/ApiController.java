package com.wzx.controller;

import com.wzx.domain.Maze;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by arthurwang on 16/12/28.
 */
@RestController
@RequestMapping(value = "/api")
public class ApiController {
    @RequestMapping(value = "/maze", method = RequestMethod.GET)
    List<List<Integer>> maze(@RequestParam(required = false, defaultValue = "15") int rows,
                             @RequestParam(required = false, defaultValue = "15") int cols) {
        Maze maze = new Maze();
        return maze.getMaze(rows, cols);
    }
}
