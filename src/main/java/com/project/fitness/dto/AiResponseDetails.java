package com.project.fitness.dto;

import java.util.List;

public record AiResponseDetails(
        String recommendation,
        List<String> improvements,
        List<String> suggestions,
        List<String> safety        // <-- CHANGED THIS TO LIST
) {}