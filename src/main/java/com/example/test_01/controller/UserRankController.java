package com.example.test_01.controller;

import com.example.test_01.service.UserRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/user_rank")
public class UserRankController {
    @Autowired
    private UserRankService userRankService;

    // xếp hạng người chơi theo số điểm
    @GetMapping("/rankings")
    public ResponseEntity<List<Object[]>> getPlayerRankings() {
        List<Object[]> rankings = userRankService.getPlayerRankings();
        return ResponseEntity.ok(rankings);
    }

}
