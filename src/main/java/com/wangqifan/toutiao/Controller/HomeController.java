package com.wangqifan.toutiao.Controller;

import com.wangqifan.toutiao.Models.EntityType;
import com.wangqifan.toutiao.Models.HostHolder;
import com.wangqifan.toutiao.Models.ViewObject;
import com.wangqifan.toutiao.Service.LikeService;
import com.wangqifan.toutiao.Service.NewsService;
import com.wangqifan.toutiao.Models.News;
import com.wangqifan.toutiao.Service.UserService;


import com.wangqifan.toutiao.async.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private NewsService newservice;

    @Autowired
    private UserService userservice;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    List<ViewObject> getNews(int userId, int offset, int limit) {
        List<News> newsList = newservice.getLatestNews(userId, offset, limit);
        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
        List<ViewObject> vos = new ArrayList<>();
        for (News news : newsList) {
            ViewObject vo = new ViewObject();
            vo.set("news", news);
            vo.set("user", userservice.getUser(news.getUserId()));
            if (localUserId != 0) {
                vo.set("like", likeService.getLikeStatus(localUserId, EntityType.ENTITY_NEWS, news.getId()));
            } else {
                vo.set("like", 0);
            }
            vos.add(vo);
        }
        return vos;
    }

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(@RequestParam(value = "pop", defaultValue = "0") int pop, Model model) {
        model.addAttribute("vos",getNews(0,0,10));
        if (hostHolder.getUser() != null) {
            pop = 0;
        }
        model.addAttribute("pop", pop);
        return "home";
    }

    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId) {
        model.addAttribute("vos", getNews(userId, 0, 10));
        return "home";
    }
}
