import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NgnA {

    public static class Pair {
        private char sign;
        private double value;

        public char getSign() {
            return sign;
        }

        public double getValue() {
            return value;
        }

        public Pair(char sign, double value) {
            this.sign = sign;
            this.value = value;
        }
    }

    private static char[] specialChars = new char[]{'+', '-', '/', '*', '.'};
    private static String emblemat = "--- | ";

    public static void main(String[] args) {
        System.out.println(emblemat+"Welcome.");
        System.out.println(emblemat+"Program dedicated to NGN IT Solutions GmbH.");
        System.out.println(emblemat+"Made by Michal Kurzyk.");
        System.out.println(emblemat+"2021");
        System.out.println(emblemat+"");
        System.out.println(emblemat+"Decimal separator is used as '.' sign or ',' ");
        System.out.println(emblemat+"You can add, subtract, multiply and divide");
        System.out.println(emblemat+"Brackets are not allowd");
        System.out.println(emblemat);

        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println(emblemat+"[Type 'e' to exit]");
            System.out.println(emblemat+"Write the operation to do:");
            System.out.print(emblemat);
            String operationStr = scan.nextLine();

            if (operationStr.equals("e")) {
                System.out.println(emblemat+"Michal Kurzyk.");
                System.out.println(emblemat+"2021");
                System.out.println(emblemat+"Closing program. See you.");
                System.exit(0);
            }
            operationStr = prepareOperationString(operationStr);
            if (checkCorrectnessOfLine(operationStr) == false) {
                System.out.println(emblemat+"Incorrect input");
                continue;
            }

            List<Pair> pairList = organizeOperationString(operationStr);
//            operationSeries(pairList, '^');
            operationSeries(pairList, '/');
            operationSeries(pairList, '*');
            operationSeries(pairList, '-');
            operationSeries(pairList, '+');

            char lastSign = pairList.get(0).getSign();

            if (pairList.size() > 0)
                System.out.println(emblemat+"The result is: ");
            System.out.print(emblemat);
            if (lastSign == '-')
                System.out.print(pairList.get(0).sign);

            System.out.println(pairList.get(0).getValue());
            System.out.println(emblemat);
        }
    }

    private static String prepareOperationString(String operationStr) {
        operationStr = operationStr.replaceAll(",", ".");
        operationStr = operationStr.replaceAll("\\s", "");

        if (operationStr.length() > 0) {
            char firstChar = operationStr.charAt(0);
            if (isNumeric(Character.toString(firstChar))) {
                operationStr = '+' + operationStr;
            }
        }
        return operationStr;
    }

    private static List<Pair> organizeOperationString(String operationStr) {
        List<Pair> pairList = new ArrayList<>();
        pairList.add(new Pair('+',0));
        while (operationStr.length() > 0) {
            char sign = operationStr.charAt(0);
            operationStr = operationStr.substring(1);
            String strValue = "";
            char c;
            do {
                c = operationStr.charAt(0);
                strValue = strValue + c;
                operationStr = operationStr.substring(1);
                if (operationStr.length() == 0)
                    break;
                c = operationStr.charAt(0);
            } while (c >= 48 && c <= 57 || c == '.');
            double doubleValue = Double.parseDouble(strValue);
            pairList.add(new Pair(sign, doubleValue));
        }
        return pairList;
    }

    private static void operationSeries(List<Pair> pairList, char typeOfOperation) {
        for (int i = 0; i < pairList.size(); i++) {
            Pair p = pairList.get(i);
            char sign = p.sign;
            if (sign != typeOfOperation)
                continue;
            if (i > 0) {
                Pair pLast = pairList.get(i - 1);
                switch (sign) {
                    case '^':
                        pLast.value = Math.pow(pLast.value, p.value);
                        break;
                    case '*':
                        pLast.value = pLast.value * p.value;
                        break;
                    case '/':
                        pLast.value = pLast.value / p.value;
                        break;
                    case '+':
                        pLast.value = pLast.value + p.value;
                        break;
                    case '-':
                        pLast.value = pLast.value - p.value;
                        break;
                }
                pairList.remove(p);
                i--;
            }
        }
    }

    private static boolean checkCorrectnessOfLine(String str) {
        if (str.length() > 0) {
            char c = str.charAt(0);
            if (!isNumeric(Character.toString(c)) &&
                    c != '.' &&
                    c != '+' &&
                    c != '-')
                return false;
        } else return false;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!(c >= 48 && c <= 57)) { // if is not a digit
                boolean isSpecial = false;
                for (char sc : specialChars) {
                    if (c == sc) {
                        if (str.length() <= i + 1)
                            return false;
                        else if (!isNumeric(Character.toString(str.charAt(i + 1))))
                            return false;
                        isSpecial = true;
                        break;
                    }
                }
                if (isSpecial == false)
                    return false;
            }
        }
        return true;
    }

    public static boolean isNumeric(String str) {
        if (str == null)
            return false;
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private static String removeUnsuspectedChars(String str) {
        str = str.replaceAll(",", ".");
        str = str.replaceAll("\\s", "");
        StringBuilder stringBuilder = new StringBuilder(str);
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!(c >= 48 && c <= 57)) {
                //power sign
//                if (c != '+' && c != '-' && c != '*' && c != '/' && c != '^')
                if (c != '+' && c != '-' && c != '*' && c != '/' && c!='.') {
                    stringBuilder.setCharAt(i, '_');
                    System.out.println("Warning: encountered unsuspected character");
                }
            }
        }
        str = stringBuilder.toString().replaceAll("_", "");

        return str;
    }

    private static String getOperationStringRebuilt(List<Pair> pairList) {
        String operation = "";
        for (Pair p : pairList) {
            operation = operation + p.getSign() + " " + p.getValue() + " ";
        }
        return operation;
    }

    public static void printPairs(List<Pair> pairList) {
        for (Pair p : pairList) {
            System.out.print(p.getSign());
            System.out.println(p.getValue());
        }
    }
}
