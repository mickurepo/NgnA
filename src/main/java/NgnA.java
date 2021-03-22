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

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("[Type 'e' to exit]");
            System.out.println("Write the operation to do:");
            String operationStr = scan.nextLine();
            if (operationStr.startsWith("e"))
                System.exit(0);
            operationStr = prepareOperationString(operationStr);
            List<Pair> pairList = organizeOperationString(operationStr);
//            operationSeries(pairList, '^');
            operationSeries(pairList, '/');
            operationSeries(pairList, '*');
            operationSeries(pairList, '-');
            operationSeries(pairList, '+');
            if (pairList.size() > 0)
                System.out.println("The result is:" + pairList.get(0).getSign() + pairList.get(0).getValue());

            System.out.println();
        }
    }

    private static String getOperationRebuilt(List<Pair> pairList){
        String operation = "";
        for (Pair p : pairList){
            operation = operation + p.getSign()+" "+p.getValue()+" ";
        }
        return operation;
    }

    private static String prepareOperationString(String operationStr) {
        operationStr = removeUnsuspectedChars(operationStr);

        if (operationStr.length() > 0) {
            char firstChar = operationStr.charAt(0);
            if (isNumeric(Character.toString(firstChar))) {
                operationStr = '+' + operationStr;
            }
        }
        return operationStr;
    }

    private static String removeUnsuspectedChars(String str) {
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

    private static List<Pair> organizeOperationString(String operationStr) {
        List<Pair> pairList = new ArrayList<>();
        while (operationStr.length() > 0) {
            char sign = operationStr.charAt(0);
            operationStr = operationStr.substring(1);
            while (!isNumeric(Character.toString(operationStr.charAt(0)))) {
                operationStr = operationStr.substring(1);
                System.out.println("Warning: used autocorrection.");
            }

            String strValue = "";
            char c;
            do {
                c = operationStr.charAt(0);
                strValue = strValue + c;
                operationStr = operationStr.substring(1);
                if (operationStr.length() == 0)
                    break;
                c = operationStr.charAt(0);
            } while (c >= 48 && c <= 57 || c=='.');
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

    private static void paint(List<Pair> pairList) {
        for (Pair p : pairList) {
            System.out.print(p.getSign());
            System.out.println(p.getValue());
        }
    }
}
