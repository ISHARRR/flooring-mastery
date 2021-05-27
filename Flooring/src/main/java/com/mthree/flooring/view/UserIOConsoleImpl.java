/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooring.view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 *
 * @author ishar
 */
@Component
public class UserIOConsoleImpl implements UserIO {
    
    

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        Scanner sc = new Scanner(System.in);
        String message = null;
        boolean flag = true;

        while (flag) {
            message = sc.nextLine().stripLeading().stripTrailing();
            if ("".equals(message)) {
                System.out.println("Invalid Entry");
            } else {
                flag = false;
            }
        }

        char[] charArray = message.toCharArray();
        boolean foundSpace = true;

        for (int i = 0; i < charArray.length; i++) {
            if (Character.isLetter(charArray[i])) {
                if (foundSpace) {
                    charArray[i] = Character.toUpperCase(charArray[i]);
                    foundSpace = false;
                }
            } else {
                foundSpace = true;
            }
        }
        message = String.valueOf(charArray);
        String stripTrailingSpace = message.strip();
        return stripTrailingSpace;
    }

    @Override
    public int readInt(String prompt) {
        System.out.println(prompt);
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        System.out.println(prompt);
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        int input = 0;
        while (flag) {
            try {
                input = sc.nextInt();
            } catch (java.util.InputMismatchException e) {
                sc.nextLine();
            }
            
            if (input >= min && input <= max) {
                flag = false;
            } else {
                System.out.println("Error out of range or Invalid: " + prompt);
                input = sc.nextInt();
            }

        }

        return input;
    }

    @Override
    public double readDouble(String prompt) {
        System.out.println(prompt);
        Scanner sc = new Scanner(System.in);
        return sc.nextDouble();
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        System.out.println(prompt);
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        double input = 0;
        while (flag) {
            input = sc.nextDouble();
            if (input >= min && input <= max) {
                flag = false;
            } else {
                System.out.println(prompt);
            }

        }

        return input;
    }

    @Override
    public float readFloat(String prompt) {
        System.out.println(prompt);
        Scanner sc = new Scanner(System.in);
        return sc.nextFloat();
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        System.out.println(prompt);
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        float input = sc.nextFloat();
        while (flag) {
            if (input >= min && input <= max) {
                flag = false;
            }
            System.out.println(prompt);
            input = sc.nextFloat();
        }

        return input;
    }

    @Override
    public long readLong(String prompt) {
        System.out.println(prompt);
        Scanner sc = new Scanner(System.in);
        return sc.nextLong();
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        System.out.println(prompt);
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        long input = sc.nextLong();
        while (flag) {
            if (input >= min && input <= max) {
                flag = false;
            }
            System.out.println(prompt);
            input = sc.nextLong();
        }

        return input;
    }

    @Override
    public BigDecimal readBDecimal(String prompt) {
        System.out.println(prompt);
        Scanner sc = new Scanner(System.in);
        BigDecimal bd = null;
        boolean flag = true;
        double doubleToBD;
        
        while(flag){
            try {
                doubleToBD = sc.nextDouble();
                bd = new BigDecimal(doubleToBD);
                bd = bd.setScale(2, RoundingMode.HALF_EVEN);
                flag = false;
            } catch (InputMismatchException e) {
                System.out.println("Invaild entery: try again");
                sc.nextLine();
            }
        }

        return bd;
    }

    @Override
    public LocalDate readDate(String prompt) {
        System.out.println("YYYY-MM-DD");
        Scanner scanner = new Scanner(System.in);
        LocalDate today = LocalDate.now();
        boolean flag = true;
        String date = null;
        
        LocalDate date1 = null;
        while(flag){
            try {
                date = scanner.nextLine().stripLeading().stripTrailing();
                date1 = LocalDate.parse(date);
                
                if(date1.isAfter(today)){
                    flag = false;
                } else {
                    System.out.println("Invalid Date, try again");
                }
                
            } catch (DateTimeParseException e){
                System.out.println("Invalid Date, try again");
            }
        }
        return date1;
    }


}
