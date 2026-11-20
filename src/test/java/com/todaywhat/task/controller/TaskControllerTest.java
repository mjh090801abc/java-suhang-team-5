package com.todaywhat.task.controller;

import com.todaywhat.task.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    // 테스트 시작 전 기존 데이터 삭제
    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
    }

    // 할 일 등록 테스트
    @Test
    void createTaskTest() throws Exception {
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "title": "수행평가 준비",
                        "deadline": "2026-11-20",
                        "importance": 5
                    }
                    """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("수행평가 준비"))
                .andExpect(jsonPath("$.deadline").value("2026-11-20"))
                .andExpect(jsonPath("$.importance").value(5));
    }

    // 할 일 조회 테스트
    @Test
    void getTaskTest() throws Exception {
        Long id = createTask();

        mockMvc.perform(get("/tasks/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("테스트 할 일"));
    }

    // 할 일 수정 테스트
    @Test
    void updateTaskTest() throws Exception {
        Long id = createTask();

        mockMvc.perform(put("/tasks/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "title": "수정된 할 일",
                        "deadline": "2026-11-25",
                        "importance": 5
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("수정된 할 일"))
                .andExpect(jsonPath("$.deadline").value("2026-11-25"))
                .andExpect(jsonPath("$.importance").value(5));
    }

    // 할 일 삭제 테스트
    @Test
    void deleteTaskTest() throws Exception {
        Long id = createTask();

        mockMvc.perform(delete("/tasks/" + id))
                .andExpect(status().isNoContent());

        // 삭제된 할 일 조회 시 404 확인
        mockMvc.perform(get("/tasks/" + id))
                .andExpect(status().isNotFound());
    }

    // 존재하지 않는 할 일 조회 테스트
    @Test
    void getTaskNotFoundTest() throws Exception {
        mockMvc.perform(get("/tasks/999999"))
                .andExpect(status().isNotFound());
    }

    // 잘못된 제목으로 등록하는 테스트
    @Test
    void createTaskFailTest() throws Exception {
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "title": "",
                        "deadline": "2026-11-20",
                        "importance": 3
                    }
                    """))
                .andExpect(status().isBadRequest());
    }

    // 우선순위 계산 테스트
    @Test
    void priorityTest() throws Exception {
        createTask();

        mockMvc.perform(get("/api/priority"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("테스트 할 일"))
                .andExpect(jsonPath("$[0].score").isNumber());
    }

    // 우선순위 추천 테스트
    @Test
    void recommendTest() throws Exception {
        createTask();

        mockMvc.perform(get("/api/priority/recommend"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("테스트 할 일"));
    }

    // 완료 상태 변경 및 추천 제외 테스트
    @Test
    void updateStatusTest() throws Exception {
        Long id = createTask();

        mockMvc.perform(patch("/tasks/" + id + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "completed": true
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(true));

        mockMvc.perform(get("/api/priority/recommend"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // 통계 및 분석 테스트
    @Test
    void statisticsTest() throws Exception {
        Long id = createTask();

        mockMvc.perform(get("/api/statistics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(1))
                .andExpect(jsonPath("$.completed").value(0))
                .andExpect(jsonPath("$.remaining").value(1))
                .andExpect(jsonPath("$.completionRate").value(0.0));

        mockMvc.perform(patch("/tasks/" + id + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"completed\":true}"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/statistics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(1))
                .andExpect(jsonPath("$.completed").value(1))
                .andExpect(jsonPath("$.remaining").value(0))
                .andExpect(jsonPath("$.completionRate").value(100.0));
    }

    // 테스트용 할 일 등록 후 생성된 ID 반환
    private Long createTask() throws Exception {
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "title": "테스트 할 일",
                        "deadline": "2026-11-20",
                        "importance": 3
                    }
                    """))
                .andExpect(status().isCreated());

        return taskRepository.findAll().get(0).getId();
    }
}