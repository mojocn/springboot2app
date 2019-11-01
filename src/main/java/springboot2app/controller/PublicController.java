package springboot2app.controller;

import springboot2app.common.util.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(path = "/api/public")
public class PublicController {

    @GetMapping("meta")
    public ResponseEntity<Result> allMeta() {
        Map<Integer, String> jobs = new HashMap<>();
        jobs.put(0, "未知职位");
        jobs.put(1, "省厅领导");
        jobs.put(2, "干警");
        Result res = Result.of("jobTitle", jobs);
        return ResponseEntity.ok(res);
    }

}