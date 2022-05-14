package com.example.smarthiring.service;

import com.example.smarthiring.dto.CapabilityLevelDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


public class RecommendService {

    private static RecommendService instance;

    private RecommendService() {}

    public static RecommendService getInstance() {
        if (instance == null) {
            instance = new RecommendService();
        }

        return instance;
    }

    public long getMatchingScore(List<CapabilityLevelDto> userSkills, List<CapabilityLevelDto> personaSkills, Map<Integer, Double> personaPercent) {
        Map<Integer, Integer> mapPersonaLevel = personaSkills.stream().collect(Collectors.toMap(CapabilityLevelDto::getCapabilityId, CapabilityLevelDto::getLevel));
        double result = 0;
        for (CapabilityLevelDto userSkill : userSkills) {
            if (personaPercent.get(userSkill.getCapabilityId()) != null) {
                if (userSkill.getLevel() >= mapPersonaLevel.get(userSkill.getCapabilityId())) {
                    result = result + personaPercent.get(userSkill.getCapabilityId());
                } else {
                    result = result + (double)userSkill.getLevel()/(double)mapPersonaLevel.get(userSkill.getCapabilityId()) * personaPercent.get(userSkill.getCapabilityId());
                }
            }
        }

        return Math.round(result);
    }

    public Map<Integer, Double> getPercentPersonaSkills(List<CapabilityLevelDto> personaSkills) {
        Map<Integer, Map<Integer, Double>> pairwiseComparison = new HashMap<>();
        Map<Integer, Double> totalPairwise = new HashMap<>();

        personaSkills.forEach(personalSkill -> {
            pairwiseComparison.put(personalSkill.getCapabilityId(), getPairwiseComparison(personalSkill.getImportance(), personaSkills, totalPairwise));
        });

        return calculatePercentSkill(pairwiseComparison, totalPairwise);
    }

    private Map<Integer, Double> getPairwiseComparison(Integer importance, List<CapabilityLevelDto> personalSkills, Map<Integer, Double> totalPairwise) {
        Map<Integer, Double> result = new HashMap<>();

        personalSkills.forEach(personalSkill -> {
            result.put(personalSkill.getCapabilityId(), (double)importance/(double)personalSkill.getImportance());
            if (totalPairwise.get(personalSkill.getCapabilityId()) == null) {
                totalPairwise.put(personalSkill.getCapabilityId(), (double)importance/(double)personalSkill.getImportance());
            } else {
                totalPairwise.put(personalSkill.getCapabilityId(), totalPairwise.get(personalSkill.getCapabilityId()) + (double)importance/(double)personalSkill.getImportance());
            }
        });

        return result;
    }

    private Map<Integer, Double> calculatePercentSkill(Map<Integer, Map<Integer, Double>> pairwise, Map<Integer, Double> totalPairwise) {
        Map<Integer, Double> result = new HashMap<>();

        // count percent of skill for a persona
        pairwise.forEach((id, map) -> {
            AtomicInteger index = new AtomicInteger(0);
            AtomicReference<Double> sum = new AtomicReference<>(0.0);

            map.forEach((k, v) -> {
                sum.set(sum.get() + v/totalPairwise.get(k));
                index.getAndIncrement();
            });

            result.put(id, (double)Math.round(sum.get()/(double)index.get() * 10000)/(double)100 );
        });

        return result;
    }
}
