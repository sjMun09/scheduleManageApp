package com.example.scheduledemo.service;

import com.example.scheduledemo.entity.Schedule;
import com.example.scheduledemo.entity.User;
import com.example.scheduledemo.repository.ScheduleRepository;
import com.example.scheduledemo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public Schedule createSchedule(Long userId, Schedule schedule) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        schedule.setUser(user);
        return scheduleRepository.save(schedule);
    }

    public Schedule getSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(() -> new RuntimeException("Schedule not found"));
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    @Transactional
    public Schedule updateSchedule(Long scheduleId, Schedule updatedSchedule) {
        Schedule schedule = getSchedule(scheduleId);
        schedule.setTitle(updatedSchedule.getTitle());
        schedule.setContents(updatedSchedule.getContents());
        return scheduleRepository.save(schedule);
    }

    @Transactional
    public void deleteSchedule(Long scheduleId) {
        Schedule schedule = getSchedule(scheduleId);
        scheduleRepository.delete(schedule);
    }
}