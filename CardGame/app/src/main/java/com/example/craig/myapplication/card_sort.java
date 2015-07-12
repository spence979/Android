package com.example.craig.myapplication;

/**
 * Created by Craig on 25/02/2015.
 */
public class card_sort {
    private String convert;
    public String SortByNumber(String Card){
        String CurrentCardData[] = (Card).split("_");
        switch (CurrentCardData[0]) {
            case "ace":
                return "1_" + CurrentCardData[1] + "_" + CurrentCardData[2];
            case "two":
                return "2_" + CurrentCardData[1] + "_" + CurrentCardData[2];
            case "three":
                return "3_" + CurrentCardData[1] + "_" + CurrentCardData[2];
            case "four":
                return "4_" + CurrentCardData[1] + "_" + CurrentCardData[2];
            case "five":
                return "5_" + CurrentCardData[1] + "_" + CurrentCardData[2];
            case "six":
                return "6_" + CurrentCardData[1] + "_" + CurrentCardData[2];
            case "seven":
                return "7_" + CurrentCardData[1] + "_" + CurrentCardData[2];
            case "eight":
                return "8_" + CurrentCardData[1] + "_" + CurrentCardData[2];
            case "nine":
                return "9_" + CurrentCardData[1] + "_" + CurrentCardData[2];
            case "ten":
                return "10_" + CurrentCardData[1] + "_" + CurrentCardData[2];
            case "jack":
                return "11_" + CurrentCardData[1] + "_" + CurrentCardData[2];
            case "queen":
                return "12_" + CurrentCardData[1] + "_" + CurrentCardData[2];
            case "king":
                return "13_" + CurrentCardData[1] + "_" + CurrentCardData[2];
        }
        return "";
    }

    public String SortBackByNumber(String Card){
        String CurrentCardData[] = (Card).split("_");
        switch (CurrentCardData[0]) {
            case "1":
                return "ace_" + CurrentCardData[1] + "_" + CurrentCardData[2];
            case "2":
                return "two_" + CurrentCardData[1] + "_" + CurrentCardData[2];
            case "3":
                return "three_" + CurrentCardData[1] + "_" + CurrentCardData[2];
            case "4":
                return "four_" + CurrentCardData[1] + "_" + CurrentCardData[2];
            case "5":
                return "five_" + CurrentCardData[1] + "_" + CurrentCardData[2];
            case "6":
                return "six_" + CurrentCardData[1] + "_" + CurrentCardData[2];
            case "7":
                return "seven_" + CurrentCardData[1] + "_" + CurrentCardData[2];
            case "8":
                return "eight_" + CurrentCardData[1] + "_" + CurrentCardData[2];
            case "9":
                return "nine_" + CurrentCardData[1] + "_" + CurrentCardData[2];
            case "10":
                return "ten_" + CurrentCardData[1] + "_" + CurrentCardData[2];
            case "11":
                return "jack_" + CurrentCardData[1] + "_" + CurrentCardData[2];
            case "12":
                return "queen_" + CurrentCardData[1] + "_" + CurrentCardData[2];
            case "13":
                return "king_" + CurrentCardData[1] + "_" + CurrentCardData[2];
        }
        return "";
    }
}
