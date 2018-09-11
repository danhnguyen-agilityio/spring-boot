package com.agility.springJWT.controllers;

import com.agility.springJWT.Utils.FakeUtils;
import com.agility.springJWT.models.News;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/news")
public class NewsController {
    @RequestMapping(method = RequestMethod.GET)
    public List<News> news() {
        return FakeUtils.getAllNews();
    }
}