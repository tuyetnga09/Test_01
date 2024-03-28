package com.example.test_01.controller;

import com.example.test_01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/updatePlayerRankings")
    public String updatePlayerRankings() {
        userService.updatePlayerRankings();
        return "Dữ liệu xếp hạng người chơi đã được cập nhật thành công.";
    }

}
