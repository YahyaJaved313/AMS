package com.noobcoder.ams;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "admin123";
        String hashedPassword = encoder.encode(rawPassword);
        System.out.println("Hashed password for admin123: " + hashedPassword);

        // Compare with the stored hash from the database
        String storedHash = "YOUR_STORED_HASH_FROM_DATABASE"; // Replace with the actual hash from the users table
        boolean matches = encoder.matches(rawPassword, storedHash);
        System.out.println("Does admin123 match the stored hash? " + matches);
    }
}
