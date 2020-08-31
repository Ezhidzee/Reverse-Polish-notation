package com.company;

import java.util.Stack;

public class Main {
    //Метод проверки приоритета символа
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
            //Если очередной символ входной строки - число, то кладем его в стек, предварительно конвертируя его.
            if (!opzArray[i].equals("+") && !opzArray[i].equals("-") && !opzArray[i].equals("/") && !opzArray[i].equals("*")) {
                stackNumbers.push(Double.parseDouble(opzArray[i]));
            } /*Если очередной символ - знак операции, то извлекаем из стека два верхних числа,
             используем их в качестве операндов для этой операции, затем кладем результат обратно в стек.
             Когда вся входная строка будет разобрана в стеке должно остаться одно число,
             которое и будет результатом данного выражения.*/
            else {
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
                        System.out.println("EROR");
                }
                stackNumbers.push(res);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        String answ = "36852*5-3+111"; //переменная для ввода примера
        answ += " "; //костыль
        char[] problem = answ.toCharArray();
        //создаём два стека(согласно алгоритму)
        Stack<Character> stackSIMB = new Stack<>();
        Stack<String> stackOPZ = new Stack<>(); //OPZ - обратная польская запись
        for (int i = 0; i < problem.length - 1; i++) {
            //если символ является числом
            if (problem[i] != '+' && problem[i] != '-' && problem[i] != '/' && problem[i] != '*') {
                String str = String.valueOf(problem[i]);
                //если число одинарное то просто добваить его в стек
                if (problem[i + 1] == '+' || problem[i + 1] == '-' || problem[i + 1] == '/' || problem[i + 1] == '*') {
                    stackOPZ.push(str);
                } //иначе добавляем следующий символ к переменной, проверив что это число,
                // до тех пор пока следующий символ не будет арифметическим символом
                else if (problem[i + 1] != '+' && problem[i + 1] != '-' && problem[i + 1] != '/' && problem[i + 1] != '*') {
                    while (i < problem.length - 1 && problem[i + 1] != '+' && problem[i + 1] != '-' && problem[i + 1] != '/' && problem[i + 1] != '*' && problem[i + 1] != ' ') {
                        str += String.valueOf(problem[i + 1]);
                        i++;
                    }
                    stackOPZ.push(str);
                }
            }
            if (problem[i] == '+' || problem[i] == '-' || problem[i] == '/' || problem[i] == '*') {
                /*Если стек все еще пуст, или находящиеся в нем символы имеют меньший приоритет,
                 чем приоритет текущего символа, то помещаем текущий символ в стек.*/
                if (stackSIMB.empty() || priority(stackSIMB.peek()) < priority(problem[i])) {
                    stackSIMB.push(problem[i]);
                }/*Если символ, находящийся на вершине стека имеет приоритет,
                 больший или равный приоритету текущего символа,
                 то извлекаем символы из стека в выходную строку до тех пор, пока выполняется это условие.*/
                else if (priority(stackSIMB.peek()) >= priority(problem[i])) {
                    while (!stackSIMB.empty()) {
                        stackOPZ.push(String.valueOf(stackSIMB.pop()));
                    }
                    stackSIMB.push(problem[i]);
                }
            }
        }
        //Оставшиеся символы просто добавляем(согласно алгоритму)
        while (!stackSIMB.empty()) {
            stackOPZ.push(String.valueOf(stackSIMB.pop()));
        }
        System.out.println(Calc(stackOPZ));
    }
}
/*
...........／＞　 フ.....................
　　　　　| 　O　 O|
　 　　　／`ミ _x 彡
　　 　 /　　　 　 |
　　　 /　 ヽ　　 ﾉ
　／￣|　　 |　|　|
　| (￣ヽ＿_ヽ_)_)
　＼二つ
*/