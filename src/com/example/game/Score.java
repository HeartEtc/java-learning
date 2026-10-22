package com.example.game;

import java.time.LocalDateTime;

public record Score(String player, int score, String mode, LocalDateTime playedAt){
}
