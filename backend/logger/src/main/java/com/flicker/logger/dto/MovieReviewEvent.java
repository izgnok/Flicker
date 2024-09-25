package com.flicker.logger.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MovieReviewEvent {

    private Long userSeq;
    private Long movieSeq;
    private Long reviewSeq;
    private String type; // "review" or "movie"
    private String action; // "create" or "delete"
    private Double rating;
    private LocalDateTime timestamp;
}
