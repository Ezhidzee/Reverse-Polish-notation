package com.example.unitonverter;

import java.util.Stack;

public class Calc {

    private static int priority(char Simbol) {
        switch (Simbol) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }

    private static double Calc(Stack<String> OPZ) {
        String[] opzArray;
        Stack<Double> stackNumbers = new Stack<>();
        double n1 = 0, n2 = 0, res = 0;
        opzArray = OPZ.toArray(new String[0]);
        for (int i = 0; i < opzArray.length; ++i) {
            
            if (!opzArray[i].equals("+") && !opzArray[i].equals("-") && !opzArray[i].equals("/") && !opzArray[i].equals("*")) {
                stackNumbers.push(Double.parseDouble(opzArray[i]));
            } else {
                n2 = stackNumbers.pop();
                n1 = stackNumbers.pop();
                switch (opzArray[i]) {
                    case "+":
                        res = n1 + n2;
                        break;
                    case "-":
                        res = n1 - n2;
                        break;
                    case "*":
                        res = n1 * n2;
                        break;
                    case "/":
                        res = n1 / n2;
                        break;
                    default:
                        return n1;
                }
                stackNumbers.push(res);
            }
        }
        return res;
    }

    public static double CalcIn(String answ){
        answ += " "; //костыль
        boolean firstTry = false;
        char[] problem = answ.toCharArray();
        Stack<Character> stackSIMB = new Stack<>();
        Stack<String> stackOPZ = new Stack<>();
        
        for (int i = 0; i < problem.length - 1; i++) {
            if(problem[0] == '-' && !firstTry) {
                i++;
                firstTry = true;
            }
            
            if (problem[i] != '+' && problem[i] != '-' && problem[i] != '/' && problem[i] != '*') {
                String str = "";
                if(i == 1 && firstTry){
                    str = "-";
                    str += String.valueOf(problem[i]);
                }else{
                    str = String.valueOf(problem[i]);
                }
                
                if (problem[i + 1] == '+' || problem[i + 1] == '-' || problem[i + 1] == '/' || problem[i + 1] == '*') {
                    stackOPZ.push(str);
                } else if (problem[i + 1] != '+' && problem[i + 1] != '-' && problem[i + 1] != '/' && problem[i + 1] != '*') {
                    while (i < problem.length - 1 && problem[i + 1] != '+' && problem[i + 1] != '-' && problem[i + 1] != '/' && problem[i + 1] != '*' && problem[i + 1] != ' ') {
                        str += String.valueOf(problem[i + 1]);
                        i++;
                    }
                    stackOPZ.push(str);
                }
            }
            if (problem[i] == '+' || problem[i] == '-' || problem[i] == '/' || problem[i] == '*') {
                
                if (stackSIMB.empty() || priority(stackSIMB.peek()) < priority(problem[i])) {
                    stackSIMB.push(problem[i]);
                } else if (priority(stackSIMB.peek()) >= priority(problem[i])) {
                    while (!stackSIMB.empty()) {
                        stackOPZ.push(String.valueOf(stackSIMB.pop()));
                    }
                    stackSIMB.push(problem[i]);
                }
            }
        }
        
        while (!stackSIMB.empty()) {
            stackOPZ.push(String.valueOf(stackSIMB.pop()));
        }
        
        return Calc(stackOPZ);
    }
}
