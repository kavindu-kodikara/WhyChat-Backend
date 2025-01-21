/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.why.config;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ilma_imthiyaz
 */
public class Validat {
    
  public static boolean isEmailValid(String email) {
        return email.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    }

    public static boolean isPasseordValid(String password) {
        return password.matches("^(?=.[a-z])(?=.[A-Z])(?=.\\d)(?=.[@#$%^&+=]).{8,}$");
    } 
    public static boolean isMobileValid(String mobile){
    
        return mobile.matches("^07[01245678]{1}[0-9]{7}$");
    
    }
    
    public static boolean isPasswordValid(String password){
    
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$");
    
    }

    public static Map<String, String> parseQueryParams(URI uri) {
        Map<String, String> params = new HashMap<>();
        String query = uri.getQuery();  // e.g., "type=chat&mobileNumber=1234567"
        if (query != null) {
            for (String param : query.split("&")) {
                String[] pair = param.split("=");
                if (pair.length == 2) {
                    params.put(pair[0], pair[1]);
                }
            }
        }
        return params;
    }
}
