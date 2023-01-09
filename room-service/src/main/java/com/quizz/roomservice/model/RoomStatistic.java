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

    @Getter(AccessLevel.NONE)
    private double accuracy;

    @Getter(AccessLevel.NONE)
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
        for (int i = 0; i < answerTimeList.size() && i < 5; i++) {
            AnswerTimeStatistic answerTimeStatistic = (AnswerTimeStatistic) answerTimeList.get(i);
            UserRankStatistic userRankStatistic = new UserRankStatistic();
            if (answerTimeStatistic.getUserInfo() != null) {
                BeanUtils.copyProperties(answerTimeStatistic.getUserInfo(), userRankStatistic);
            } else {
                userRankStatistic.setUsername(answerTimeStatistic.getNickName());
                userRankStatistic.setId(answerTimeStatistic.getId().toString());
            }
            userRankStatistic.setPoint(answerTimeStatistic.getPoint());
            userRankStatistic.setRank(i + 1);
            userRankStatisticList.add(userRankStatistic);
        }
        chartStatisticGetter.setUserRankStatistics(userRankStatisticList);
        return chartStatisticGetter;
    }

    public ChartStatistic getChartStatistic(String userId, Long questionId) {
        List<UserRankStatistic> userRankStatisticList = new ArrayList<>();
        ChartStatistic chartStatisticGetter = new ChartStatistic();
        List<AnswerTime> answerTimes = getAnswerTimes().stream().toList();
        List<AnswerTime> answerTimeList = new ArrayList<>();
        for (AnswerTime answerTime : answerTimes) {
            answerTimeList.add(answerTime);
        }
        answerTimeList.sort((o1, o2) -> {
            long value = o1.getPoint(questionId) - o2.getPoint(questionId);
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
        int userRank = -1;
        List<UserRankStatistic> userRankStatistics = chartStatisticGetter.getUserRankStatistics().stream().toList();
        if (userId != null) {
            for (int i = 0; i < userRankStatistics.size(); i++) {
                if (userId.equals(userRankStatistics.get(i).getId())) {
                    userRank = i + 1;
                    break;
                }
            }
        }
        ChartStatistic returnChartStatistic = new ChartStatistic();
        BeanUtils.copyProperties(chartStatisticGetter, returnChartStatistic);
        returnChartStatistic.setCurrentRank(userRank);
        return returnChartStatistic;
    }

    public int getNumberOfJoined() {
        if (numberOfJoined == 0) {
            return getAnswerTimes().size();
        }
        return numberOfJoined;
    }

    public double getAccuracy() {
        if (accuracy == 0) {
            if (getAnswerTimes().size() == 0) return 0;
            List<AnswerTime> answerTimes = getAnswerTimes().stream().toList();
            double sum = 0;
            for (AnswerTime answerTime : answerTimes) {
                sum += answerTime.getAccuracy();
            }
            return sum / getAnswerTimes().size();
        }
        return accuracy;
    }

    public void reduceQuestionAnswer() {

    }
}
