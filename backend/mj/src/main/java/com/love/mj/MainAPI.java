package com.love.mj;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainAPI {

    @Autowired
    DemoRepo demoRepo;

    @PostMapping("/x")
    public String add(@RequestBody Demo demo){
        demoRepo.save(demo);
        return "ADD";
    }
}
