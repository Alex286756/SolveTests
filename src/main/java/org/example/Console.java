package org.example;

import java.util.Scanner;

public class Console {
    public Console() {
        this.student = null;
    }

    private Student<Integer> student;

    public void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        int input = 0;

        while (input != 5) {
            System.out.println("Выберите действие:");
            System.out.println("   1 - Создать студента по имени");
            System.out.println("   2 - Добавить оценку");
            System.out.println("   3 - Удалить последнюю оценку");
            System.out.println("   4 - Вывести данные о студенте");
            System.out.println("   5 - Выход из программы");

            input = scanner.nextInt();
            switch (input) {
                case 1 -> newStudent();
                case 2 -> newGrade();
                case 3 -> delGrade();
                case 4 -> printStudent();
            }
        }
    }

    private void newStudent() {
        System.out.println("Введите имя студента:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        student = new Student<>(name);
    }

    private void newGrade() {
        if (student != null) {
            System.out.println("Введите новую оценку:");
            Scanner scanner = new Scanner(System.in);
            int grade = scanner.nextInt();
            student.addGrade(grade);
        }
    }

    private void delGrade() {
        if (student != null) {
            System.out.println("Введите оценку, которую нужно удалить:");
            Scanner scanner = new Scanner(System.in);
            int grade = scanner.nextInt();
            student.delGrade(grade);
        }
    }

    private void printStudent() {
        if (student != null) {
            System.out.println(student);
        }
    }

}
