package com.apaz.studentenrollments.services;

import org.springframework.stereotype.Service;

@Service
public class EmailSender {

    public void send(String recipientEmail, String subject, String body) {

        System.out.printf("""
                        --- Simulating sending email to [%s] ---
                        
                        """, recipientEmail
        );

        System.out.printf("""
                --- Subject ---
                %s
                --- Body ---
                %s
                
                """, subject, body
        );
    }
}

