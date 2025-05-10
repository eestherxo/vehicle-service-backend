package com.esther.VehicleService.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendServiceCompletionEmail(String to,
                                           String username,
                                           String vehicleMake,
                                           String vehicleModel){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your Vehicle is ready for pickup!");
        message.setText("Hi " + username + ",\n\n" +
                "Your " + vehicleMake + ", " + vehicleModel + " is ready for pickup.\n" +
                "Thank you for choosing our service!\n\n" +
                "Best regards,\n" +
                "The Vehicle Service Team");
        mailSender.send(message);
    }
}
