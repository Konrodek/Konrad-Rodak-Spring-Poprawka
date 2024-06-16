package com.capgemini.wsb.fitnesstracker.report;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class TrainingReportService {

    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;
    private final JavaMailSender mailSender;

    @Scheduled(cron = "0 0 0 */14 * ?") // co 2 tygodnie
    public void generateAndSendReports() {
        LocalDate now = LocalDate.now();
        LocalDate twoWeeksAgo = now.minusWeeks(2);

        List<Training> trainingsLastTwoWeeks = trainingRepository.findByStartTimeBetween(twoWeeksAgo, now);

        Map<User, List<Training>> trainingsByUser = trainingsLastTwoWeeks.stream()
                .collect(Collectors.groupingBy(Training::getUser));

        for (Map.Entry<User, List<Training>> entry : trainingsByUser.entrySet()) {
            User user = entry.getKey();
            List<Training> userTrainings = entry.getValue();
            sendEmailReport(user, userTrainings);
        }
    }

    private void sendEmailReport(User user, List<Training> trainings) {
        int totalTrainings = trainings.size();
        String message = String.format("Hello %s,\n\nYou have completed %d training sessions in the last two weeks.",
                user.getFirstName(), totalTrainings);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Your Training Report");
        email.setText(message);

        mailSender.send(email);
    }
}
