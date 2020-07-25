package com.mashup.poten.domain;

import com.mashup.poten.common.state.State;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * habit Domain 객체
 * 과제에 대한 비즈니스 로직을 담당
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Habit implements Comparable<Habit> {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer habitSeq;

    private String title;

    private int duration;

    private String doDay;

    private int totalCount;

    private int doneCount;

    @Enumerated(EnumType.STRING)
    private State state;

    private int alarmTime;

    private LocalDateTime createDate;

    @JoinColumn(name = "userSeq")
    @ManyToOne
    private User user;

    @Builder
    public Habit(Integer habitSeq, String title, int duration, String doDay, int totalCount, int doneCount, State state, int alarmTime, LocalDateTime createDate) {
        this.habitSeq = habitSeq;
        this.title = title;
        this.duration = duration;
        this.doDay = doDay;
        this.totalCount = totalCount;
        this.doneCount = doneCount;
        this.state = state;
        this.alarmTime = alarmTime;
        this.createDate =createDate;
    }


    @Override
    public int compareTo(Habit habit) {
        int remainDayCount = this.totalCount - this.doneCount;
        int nexthabitRemainDayCount = habit.getTotalCount() - habit.getDoneCount();
        return (remainDayCount - nexthabitRemainDayCount);
    }

    @PrePersist
    public void setCreateDate() {
        this.createDate = LocalDateTime.now();
    }

    public void setOwner(User user) {
        this.user = user;
    }

    public void setTotalCount() {
        int cnt = 0;
        String[] dows = this.doDay.split(";");
        LocalDate currDate = LocalDate.now();
        String day = currDate.getDayOfWeek().toString();

        for(int i = 0; i < this.duration; i++) {
            switch(day) {
                case "MONDAY":
                    if(Arrays.stream(dows).anyMatch(day::equals)) cnt++;
                    break;
                case "TUESDAY":
                    if(Arrays.stream(dows).anyMatch(day::equals)) cnt++;
                    break;
                case "WEDNESDAY":
                    if(Arrays.stream(dows).anyMatch(day::equals)) cnt++;
                    break;
                case "THURSDAY":
                    if(Arrays.stream(dows).anyMatch(day::equals)) cnt++;
                    break;
                case "FRIDAY":
                    if(Arrays.stream(dows).anyMatch(day::equals)) cnt++;
                    break;
                case "SATURDAY":
                    if(Arrays.stream(dows).anyMatch(day::equals)) cnt++;
                    break;
                case "SUNDAY":
                    if(Arrays.stream(dows).anyMatch(day::equals)) cnt++;
                    break;
            }
            currDate = currDate.plusDays(1);
            day = currDate.getDayOfWeek().toString();
        }
        this.totalCount = cnt;
    }

    public void updateStatus() {

    }

}
