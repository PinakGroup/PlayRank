package com.wzx;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by arthurwang on 16/12/28.
 */
@RestController
public class MazeController {
    @RequestMapping(value = "/maze", method = RequestMethod.GET)
    public List<List<Integer>> maze(@RequestParam(required = false, defaultValue = "15") int rows,
                       @RequestParam (required = false, defaultValue = "15") int cols) {
        Maze maze = new Maze();
        return maze.getMaze(rows, cols);
    }
}
