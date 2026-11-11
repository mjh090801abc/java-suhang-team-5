package com.todaywhat.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Table(name = "tasks") //테이블 이름 지정
public class Task {

    @Id //기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 자동 생성
    private Long id; //할 일 번호

    @Column(nullable = false) //null 값 허용X
    private String title; // 할 일 제목

    @Column(nullable = false)
    private LocalDate deadline; //마감일, 날짜 자료형

    @Column(nullable = false)
    private Integer importance; //중요도, 정수 저장

    //같은 패키지 및 상속받은 클래스에서 접근 가능
    protected Task() {} //Task 클래스의 기본 생성자

    //객체 생성
    public Task(String title, LocalDate deadline, Integer importance) {
        this.title = title;
        this.deadline = deadline;
        this.importance = importance;
    }

    //기존에 등록된 할 일 정보 수정
    public void update(String title, LocalDate deadline, Integer importance) {
        this.title = title;
        this.deadline = deadline;
        this.importance = importance;
    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public Integer getImportance() {
        return importance;
    }
}
