package com.example.game;

import java.io.IOException;
import java.util.List;

public interface ScoreRepository {
    void save(Score score) throws IOException;
    List<Score> loadAll() throws IOException ;
}
