
package com.todaywhat.task.repository;

import com.todaywhat.task.domain.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TaskRepositoryTest {

    // TaskRepository 객체 자동 주입
    @Autowired
    private TaskRepository taskRepository;

    // 테스트 메서드 실행되기 전에 실행
    @BeforeEach
    // 모든 할 일 삭제
    // 테스트마다 기존 데이터가 영향을 주지 않도록 초기화
    void setUp() {
        taskRepository.deleteAll();
    }

    // 제목으로 할 일 조회 성공
    @Test
    void findByTitleSuccessTest() {
        // 테스트에 사용할 할 일 데이터 생성
        Task task = new Task(
                "자바 공부하기",
                LocalDate.of(2026, 11, 25),
                3
        );
        //저장
        taskRepository.save(task);
        // 제목이 "자바 공부하기"인 할 일 조회
        var result = taskRepository.findByTitle("자바 공부하기");
        // 조회 결과가 존재하는지
        assertThat(result).isPresent();
        // 결과에서 제목 가져고 예상 제목과 같은지 확인
        assertThat(result.get().getTitle())
                .isEqualTo("자바 공부하기");
    }

    // 존재하지 않는 제목 조회
    @Test
    void findByTitleFailTest() {
        // 존재하지 않는 제목으로 할 일 조회
        var result = taskRepository.findByTitle("없는 할 일");
        // 비어 있는지 확인
        assertThat(result).isEmpty();
    }
}
