package org.example.shopvue.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OtherController {
    @GetMapping(value = "/bannerimage")
    public List<String> bannerImage() {
        List<String> list = new ArrayList<>();
        list.add("images/banner1.jpg");
        list.add("images/banner2.png");
        list.add("images/banner3.jpg");
        list.add("images/banner4.jpg");
        list.add("images/banner5.jpg");
        return list;
    }

}
