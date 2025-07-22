package org.example;

import java.util.ArrayList;
import java.util.Objects;

public class Student<T> {

    private String name;
    private final ArrayList<T> grades;
    private final Criteria<T> criteria;
    private final ArrayList<T> oldGrades;
    private final ArrayList<Integer> indexOfGrades;
    private final ArrayList<String> oldNames;
    private final ArrayList<ChangeType> changesLog;

    public Student(String name) {
        this(name, new ArrayList<>(), (n) -> true);
    }

    public Student(String name, ArrayList<T> grades) {
        this(name, grades, (n) -> true);
    }

    public Student(String name, Criteria<T> criteria) {
        this(name, new ArrayList<>(), criteria);
    }

    public Student(String name, ArrayList<T> grades, Criteria<T> criteria) {
        this.name = name;
        this.criteria = criteria;
        this.grades = new ArrayList<>();
        grades.forEach(el -> {
            if (criteria.isMatch(el)) {
                this.grades.add(el);
            } else {
                PrintMessage(el.toString());
            }
        });
        oldGrades = new ArrayList<>();
        indexOfGrades = new ArrayList<>();
        oldNames = new ArrayList<>();
        changesLog = new ArrayList<>();
    }

    private void PrintMessage(String message) {
        System.err.println("Оценка " + message + " не соответствует заданным условиям!");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        oldNames.add(this.name);
        changesLog.add(ChangeType.CHANGE_NAME);
        this.name = name;
    }

    public void addGrade(T newGrade) {
        if (criteria.isMatch(newGrade)) {
            grades.add(newGrade);
            changesLog.add(ChangeType.ADD_GRADE);
        } else {
            PrintMessage(newGrade.toString());
        }
    }

    public void delGrade(T oldGrade) {
        int index = grades.lastIndexOf(oldGrade);
        if (index != -1) {
            indexOfGrades.add(index);
            oldGrades.add(oldGrade);
            changesLog.add(ChangeType.DELETE_GRADE);
            grades.remove(index);
        }
    }

    public void delLastGrade() {
        int index = grades.size() - 1;
        if (index != -1) {
            T lastGrade = grades.get(index);
            oldGrades.add(lastGrade);
            changesLog.add(ChangeType.DELETE_LAST_GRADE);
            grades.remove(index);
        }
    }

    public ArrayList<T> getGrades() {
        return new ArrayList<>(grades);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Student<?> student = (Student<?>) o;
        return Objects.equals(getName(), student.getName()) &&
               Objects.equals(getGrades(), student.getGrades());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, grades);
    }

    @Override
    public String toString() {
        return name + ": " + grades;
    }

    public void undo(){
        if (!changesLog.isEmpty()) {
            switch (changesLog.get(changesLog.size() - 1)) {
                case CHANGE_NAME -> undoChangeName();
                case ADD_GRADE -> undoAddGrade();
                case DELETE_GRADE -> undoDeleteGrade();
                case DELETE_LAST_GRADE -> undoDeleteLastGrade();
            }
            changesLog.remove(changesLog.size() - 1);
        }
    }

    private void undoChangeName() {
        this.name = oldNames.remove(oldNames.size() - 1);
    }

    private void undoAddGrade() {
        grades.remove(grades.size() - 1);
    }

    private void undoDeleteGrade() {
        int index = indexOfGrades.remove(indexOfGrades.size() - 1);
        grades.add(index, oldGrades.remove(oldGrades.size() - 1));
    }

    private void undoDeleteLastGrade() {
        grades.add(oldGrades.remove(oldGrades.size() - 1));
    }

}