package com.quizz.roomservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChartStatistic {
    private int currentRank;
    private Collection<UserRankStatistic> userRankStatistics;
}
