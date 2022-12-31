package com.quizz.roomservice.model;

import lombok.*;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomStatistic extends Room {
    private String lessonTitle;
    private String lessonDescription;
    private double accuracy;
    private int numberOfJoined;

    @Getter(AccessLevel.NONE)
    private ChartStatistic chartStatistic;

    public ChartStatistic getChartStatistic() {
        List<UserRankStatistic> userRankStatisticList = new ArrayList<>();
        ChartStatistic chartStatisticGetter = new ChartStatistic();
        List<AnswerTime> answerTimes = getAnswerTimes().stream().toList();
        List<AnswerTime> answerTimeList = new ArrayList<>();
        for (AnswerTime answerTime : answerTimes) {
            answerTimeList.add(answerTime);
        }
        answerTimeList.sort((o1, o2) -> {
            long value = o1.getPoint() - o2.getPoint();
            if (value > 0) return 1;
            if (value < 0) return -1;
            return 0;
        });
        for (int i = 0; i < answerTimeList.size(); i++) {
            AnswerTimeStatistic answerTimeStatistic = (AnswerTimeStatistic) answerTimeList.get(i);
            UserRankStatistic userRankStatistic = new UserRankStatistic();
            if (answerTimeStatistic.getUserInfo() != null) {
                BeanUtils.copyProperties(answerTimeStatistic.getUserInfo(), userRankStatistic);
            } else {
                userRankStatistic.setId(answerTimeStatistic.getUserId());
            }
            userRankStatistic.setRank(i + 1);
            userRankStatisticList.add(userRankStatistic);
        }
        chartStatisticGetter.setUserRankStatistics(userRankStatisticList);
        return chartStatisticGetter;
    }

    public ChartStatistic getChartStatistic(String userId) {
        ChartStatistic chartStatisticGetter = getChartStatistic();
        int userRank = 0;
        List<UserRankStatistic> userRankStatistics = chartStatisticGetter.getUserRankStatistics().stream().toList();
        for (int i = 0; i < userRankStatistics.size(); i++) {
            if (userRankStatistics.get(i).getId().equals(userId)) {
                userRank = i + 1;
                break;
            }
        }
        ChartStatistic returnChartStatistic = new ChartStatistic();
        BeanUtils.copyProperties(chartStatisticGetter, returnChartStatistic);
        returnChartStatistic.setCurrentRank(userRank);
        return returnChartStatistic;
    }
}
