package com.cafe.server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cafe.server.entities.ChangePassword;
import com.cafe.server.entities.JwtResponse;
import com.cafe.server.entities.Login;
import com.cafe.server.entities.User;
import com.cafe.server.entities.UserDao;
import com.cafe.server.jwt.JwtAuthenticationFilter;
import com.cafe.server.jwt.JwtHelper;
import com.cafe.server.repositories.UserRepository;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtAuthenticationFilter filter;

    @Autowired
    private JavaMailSender mailSender;

    public User signUp(UserDao userDao) {
        User existingUser = userRepository.findByEmail(userDao.getEmail());
        if (existingUser != null) {
            return null;
        }
        User user = new User();
        user.setName(userDao.getName());
        user.setEmail(userDao.getEmail());
        user.setPassword(passwordEncoder.encode(userDao.getPassword()));
        user.setContactNumber(userDao.getContactNumber());
        user.setRole("user");
        user.setStatus("false");
        return userRepository.save(user);
    }

    public JwtResponse userLogin(Login login) {
        JwtResponse response = authenticateDetail(login);
        if (response == null) {
            return null;
        }
        return response;
    }

    public List<User> getAllUser() {
        return (List<User>) userRepository.findAll();
    }

    public void updateStatus(int id) {
        User getUser = userRepository.findById(id);
        String email = getEmailFromToken();
        User admin = userRepository.findByEmail(email);
        if (admin.getRole().equals("admin")) {
            getUser.setStatus("true");
            sendMailToAllAdmin(getUser.getStatus(), getUser.getEmail(), userRepository.findEmailsByRole("admin"));
        }
        userRepository.save(getUser);
    }

    public User changePassword(ChangePassword changePassword) {

        String email = getEmailFromToken();
        User user = userRepository.findByEmail(email);

        if (passwordEncoder.matches(changePassword.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
            return userRepository.save(user);
        }
        return null;
    }

    public Login forgotPassword(String email) {
        try {
            User user = userRepository.findByEmail(email);
            if (user.getStatus().equals("true")) {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper help = new MimeMessageHelper(message, true);

                // set name of email
                help.setFrom("cp864323@gmail.com", " Cafe Management:- ForgotPassword");
                help.setTo(email);
                help.setSubject("Password Changed");
                help.setText("Your email:- " + email + "\nYour Password:-123456");
                user.setPassword(passwordEncoder.encode("123456"));
                mailSender.send(message);
                userRepository.save(user);

                Login userData = new Login();
                userData.setEmail(email);
                userData.setPassword(user.getPassword());
                return userData;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {
        allAdmin.remove(filter.getCurrentUser());
        if (status != null && status.equalsIgnoreCase("true")) {
            emailService.sendSimpleMessage(filter.getCurrentUser(), "Account Approved",
                    "USER:- " + user + " \n is approved by \n ADMIN:-" + filter.getCurrentUser(), allAdmin);
        } else {
            emailService.sendSimpleMessage(filter.getCurrentUser(), "Account Disabled",
                    "USER:- " + user + " \n is disabled by \n ADMIN:-" + filter.getCurrentUser(), allAdmin);
        }
    }

    private String getEmailFromToken() {
        String requestHeader = request.getHeader("Authorization");
        String token = null;
        token = requestHeader.substring(7);
        String email = helper.getUsernameFromToken(token);
        return email;
    }

    private JwtResponse authenticateDetail(Login login) {
        this.doAuthenticate(login.getEmail(), login.getPassword());
        User userData = userRepository.findByEmail(login.getEmail());
        if (userData.getStatus().equals("true")) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(login.getEmail());

            String token = this.helper.generateToken(userDetails);

            JwtResponse response = JwtResponse.builder()
                    .token(token)
                    .username(userDetails.getUsername()).build();
            return response;
        } else {
            return null;
        }
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }

}
