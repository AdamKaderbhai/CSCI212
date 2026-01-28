package Homework1;

import java.util.Scanner;

public class H1 {

    public static void printPins(int x, int y) {
        if (x <= 0) return;
        printSpaces(y - x);
        printPinsOnRow(x);
        System.out.println();
        printPins(x - 1, y);
    }


    private static void printSpaces(int n) {
        if (n <= 0) return;
        System.out.print(" ");
        printSpaces(n - 1);
    }

    private static void printPinsOnRow(int n) {
        if (n <= 0) return;
        System.out.print("O");
        if (n > 1) System.out.print(" ");
        printPinsOnRow(n - 1);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of rows: ");
        int n = scanner.nextInt();
        printPins(n,n);
        scanner.close();
    }
}