package com.todaywhat.task.dto.request;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate; //날짜를 표현하는 LocalDate 클래스

@NoArgsConstructor
@Setter //변수에 값을 설정함
@Getter //변수 값을 가지고 옴
public class TaskRequest {
    //private : 외부에서 직접 접근 제한
    private String title;
    private LocalDate deadline;
    private Integer importance;

}