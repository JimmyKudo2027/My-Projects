package com.example.jimmykudo.ReportCardClass;

public class ReportCardClass {
    private int id;
    private String name;
    private int age;
    private int grade;
    private String semester;
    private String course;

    public ReportCardClass(int id, String name, int age, int grade, String semester, String course) {
       this.id = id;
       this.name = name;
       this.age = age;
       this.grade = grade;
       this.semester = semester;
       this.course = course;
   }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getGradeLetter(int grade){

        String letterGrade;

        if(grade >= 85 && grade <= 100){
            letterGrade = "A";
        } else if (grade >= 75 && grade < 85){
            letterGrade = "B";
        } else if (grade >= 65 && grade < 75){
            letterGrade = "C";
        }  else if (grade >= 50 && grade < 65){
            letterGrade = "D";
        } else {
            letterGrade = "F";
        }

        return letterGrade;
    }

    @Override
    public String toString() {
        return "ReportCard{" +
                "studentID=" + getId() +
                ", studentName='" + getName() + '\'' +
                ", age='" + getAge() + '\'' +
                ", course='" + getCourse() + '\'' +
                ", semester='" + getSemester() + '\'' +
                ", grade='" + getGrade() + '\'' +
                ", gradeLetter=" + getGradeLetter(getGrade()) +
                '}';
    }
}
