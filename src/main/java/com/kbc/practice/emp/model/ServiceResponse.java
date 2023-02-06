package com.kbc.practice.emp.model;

import lombok.Builder;
import lombok.Data;


public record ServiceResponse(boolean isSuceess, String message) {
}
