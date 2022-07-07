package com.joodang.story.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/story")
public class StoryController {
    @GetMapping(value = "/history")
    public String history() {
        return "story/history";
    }
    @GetMapping(value = "/eat")
    public String eat(){
        return "story/eat";
    }
    @GetMapping(value = "/compare")
    public String compare(){
        return "story/compare";
    }
}