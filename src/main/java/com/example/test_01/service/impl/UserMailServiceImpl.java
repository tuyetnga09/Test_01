package com.example.test_01.service.impl;

import com.example.test_01.model.User;
import com.example.test_01.model.UserMail;
import com.example.test_01.repository.UserMailRepository;
import com.example.test_01.repository.UserRepository;
import com.example.test_01.service.UserMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class UserMailServiceImpl implements UserMailService {
    @Autowired
    private UserMailRepository userMailRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public void sendRankingBonusMail(Integer userId, String topName, String bonusRepresentation) {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            UserMail userMail = new UserMail();
            userMail.setUser(user); // Set user
            userMail.setSender_id(0);
            userMail.setTitle("Phần thưởng xếp hạng người chơi");
            userMail.setMessage("Chúc mừng bạn đã đạt thứ hạng: " + topName);
            userMail.setBonus(bonusRepresentation);
            userMail.setReceive(0);
            userMail.setRemove(0);
            userMail.setMail_idx(Integer.parseInt(currentDate));
            userMail.setDate_created(new Timestamp(System.currentTimeMillis()));
            userMailRepository.save(userMail);
        } else {
            System.out.println("User with ID " + userId + " not found.");
        }
    }
}
