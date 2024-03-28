package com.example.test_01.service;

import com.example.test_01.model.UserMail;
import com.example.test_01.repository.UserMailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class UserMailServiceImpl implements UserMailService{
    @Autowired
    private UserMailRepository userMailRepository;
    @Override
    public void sendRankingBonusMail(Integer userId, String topName, String bonusRepresentation) {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // Create user mail object
        UserMail userMail = new UserMail();
        userMail.setUser(userId); // Set user ID
        userMail.setSender_id(0); // Assuming senderId 0 represents system-generated mails
        userMail.setTitle("Phần thưởng xếp hạng người chơi");
        userMail.setMessage("Chúc mừng bạn đã đạt thứ hạng : " + topName);
        userMail.setBonus(bonusRepresentation);
        userMail.setReceive(0); // Assuming the mail has not been received yet
        userMail.setRemove(0); // Assuming the mail has not been removed yet
        userMail.setMail_idx(Integer.parseInt(currentDate)); // Assuming mail index is formatted as yyyyMMdd
        userMail.setDate_created(new Timestamp(System.currentTimeMillis()));

        // Save user mail to repository
        userMailRepository.save(userMail);
    }
}
