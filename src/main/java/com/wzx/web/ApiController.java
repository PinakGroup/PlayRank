package com.wzx.web;

import com.wzx.domain.Maze;
import com.wzx.service.JsonParser;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by arthurwang on 16/12/28.
 */
@RestController
@RequestMapping(value = "/api")
public class ApiController {
    private JsonParser jsonParser;

    @Inject
    public ApiController(JsonParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    @RequestMapping(value = "/maze", method = RequestMethod.GET)
    List<List<Integer>> maze(@RequestParam(required = false, defaultValue = "15") int rows,
                       @RequestParam (required = false, defaultValue = "15") int cols) {
        Maze maze = new Maze();
        return maze.getMaze(rows, cols);
    }

    @RequestMapping(value = "/json", method = RequestMethod.POST)
    String json(@RequestBody String str) {
        jsonParser.parse(str);
        return jsonParser.getJ().getObject();
    }
}
