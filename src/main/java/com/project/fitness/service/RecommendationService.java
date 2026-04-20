package com.project.fitness.service;

import com.project.fitness.dto.AiResponseDetails;
import com.project.fitness.model.Activity;
import com.project.fitness.model.Recommendation;
import com.project.fitness.repository.ActivityRepository;
import com.project.fitness.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final ActivityRepository activityRepository;
    private final RecommendationRepository recommendationRepository;
    private final AiRecommendationService aiRecommendationService;

    @Transactional
    public Recommendation generateAiRecommendation(String activityId) {
        // 1. Fetch the activity (Assuming you already have this logic)
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found"));

        // 2. Build a clear, structured prompt
        String prompt = String.format(
                "You are an elite AI fitness coach. Your client just completed a %s workout lasting %d minutes, burning %d calories. " +
                        "Provide a response filling out these 4 specific areas: " +
                        "1. recommendation: Acknowledgment of their specific effort and a recovery tip. " +
                        "2. improvements: One technical tip to improve their form or efficiency next time. " +
                        "3. suggestions: A specific workout to do tomorrow. " +
                        "4. safety: One safety or injury-prevention tip related to %s.",
                activity.getType(), activity.getDuration(), activity.getCaloriesBurned(), activity.getType()
        );

        // 3. Call the AI Service (It now returns the structured Record)
        AiResponseDetails aiDetails = aiRecommendationService.getAiAdvice(prompt);

        // 4. Map the Record to your Database Entity
        Recommendation recommendation = new Recommendation();
        recommendation.setActivity(activity);
        recommendation.setUser(activity.getUser());
        recommendation.setType("AI_GENERATED");

        // Populate all the fields!
        recommendation.setRecommendation(aiDetails.recommendation());
        recommendation.setImprovements(aiDetails.improvements());
        recommendation.setSuggestions(aiDetails.suggestions());
        recommendation.setSafety(aiDetails.safety());

        // 5. Save and return
        return recommendationRepository.save(recommendation);
    }

    public List<Recommendation> getUserRecommendation(String userId) {
        return recommendationRepository.findByUserId(userId);
    }

    public List<Recommendation> getActivityRecommendation(String activityId) {
        return recommendationRepository.findByActivityId(activityId);
    }
}