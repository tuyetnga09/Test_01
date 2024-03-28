package com.example.test_01.service.impl;

import com.example.test_01.repository.UserRankRepository;
import com.example.test_01.repository.UserRepository;
import com.example.test_01.service.UserMailService;
import com.example.test_01.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRankRepository userRankRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMailService userMailService;
    private final String[] bonusRepresentations = {
            "[2,100,1,1000]",   // Top 1-10
            "[2,80,1,900]",    // Top 11-20
            "[2,50]",           // Top 21-100
            "[1,500]"           // Top 101 - …
    };
    @Override
    public void updatePlayerRankings() {
        List<Object[]> playerRankings = userRankRepository.getPlayerRankings();
        for (Object[] ranking : playerRankings) {
            Integer userId = (Integer) ranking[0];
            String topName = (String) ranking[1];
            updateGoldAndGems(userId, topName);
            sendRankingBonusMail(userId, topName);
        }
    }

    @Override
    public void updateGoldAndGems(Integer userId, String topName) {
        int topIndex;
        switch (topName) {
            case "Top 1-10":
                topIndex = 0;
                break;
            case "Top 11-20":
                topIndex = 1;
                break;
            case "Top 31-100":
                topIndex = 2;
                break;
            default:
                topIndex = 3;
                break;
        }
        if (topIndex <= 3) {
            try {
                String[] bonusRepresentations = new String[0];
                if (topIndex == 0) { // Xử lý cho top 1-10
                    bonusRepresentations = new String[]{
                            "[2,100,1,1000]",   // Quà thứ nhất
                            "[2,80,1,900]",    // Quà thứ hai
                            "[2,50]",          // Quà thứ ba
                            "[2,100,1,1000]"   // Quà thứ tư
                    };
                } else {
                    bonusRepresentations = new String[]{this.bonusRepresentations[topIndex]};
                }

                long gold = 0;
                long gem = 0;
                for (String bonusRepresentation : bonusRepresentations) {
                    bonusRepresentation = bonusRepresentation.substring(1, bonusRepresentation.length() - 1);
                    String[] bonuses = bonusRepresentation.split(",");

                    for (int j = 0; j < bonuses.length; j += 2) {
                        String typeStr = bonuses[j].trim();
                        String valueStr = bonuses[j + 1].trim();

                        if (typeStr.matches("\\d+") && valueStr.matches("\\d+")) {
                            int type = Integer.parseInt(typeStr);
                            int value = Integer.parseInt(valueStr);

                            if (type == 1) {
                                gold += value;
                            } else if (type == 2) {
                                gem += value;
                            }
                        } else {
                            System.out.println("Invalid bonus representation: " + bonusRepresentation);
                        }
                    }
                }
                userRepository.updateGoldAndGems(userId, gold, gem);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendRankingBonusMail(Integer userId, String topName) {
        int topIndex;
        switch (topName) {
            case "Top 1-10":
                topIndex = 0;
                break;
            case "Top 11-20":
                topIndex = 1;
                break;
            case "Top 31-100":
                topIndex = 2;
                break;
            default:
                topIndex = 3;
                break;
        }

        if (topIndex == 0) { // Top 1-10 -  Nhận được 4 quà -  xử lý gửi nhiều mail.
            String[] bonusRepresentations = {
                    "[2,100,1,1000]",   // Quà thứ nhất
                    "[2,80,1,900]",    // Quà thứ hai
                    "[2,50]",          // Quà thứ ba
                    "[2,100,1,1000]"   // Quà thứ tư
            };

            for (String bonus : bonusRepresentations) {
                userMailService.sendRankingBonusMail(userId, topName, bonus);
            }
        } else { // Các top khác
            String bonusRepresentation = bonusRepresentations[topIndex];
            userMailService.sendRankingBonusMail(userId, topName, bonusRepresentation);
        }
    }



}
