package com.example.game;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CsvScoreRepository implements ScoreRepository {
    // scores.csv (格式为UTF-8）：
    // player_name,score,mode,played_at (表头,跳过本行)
    // 张三,21,normal,2026-10-22T21:40:00 (示例数据)
    //
    // save()：若文件不存在,先写一行表头，再追加数据
    // loadAll()：文件不存在返回空 List；跳过表头；split(",") 解析；LocalDateTime.parse()
    private final Path file;

    public CsvScoreRepository(Path file) {
        this.file = file;
    }

    public CsvScoreRepository() {
        this(Path.of("data", "scores.csv"));
    }

    @Override
    public void save(Score score) throws IOException {
        Path file = this.file;
        Files.createDirectories(file.getParent());

        boolean needHeader = !Files.exists(file) || Files.size(file) == 0;

        try (Writer writer = new FileWriter(file.toString(), StandardCharsets.UTF_8, true)) {
            if (needHeader) {
                writer.write("player_name,score,mode,played_at\n");
            }
            writer.write(score.player() + "," + score.score() + ","
                           + score.mode() + "," + score.playedAt() + "\n");
        }
    }

    @Override
    public List<Score> loadAll() throws IOException {
        if (!Files.exists(this.file)) {
            return new ArrayList<>();
        }
        Path gamedata = this.file;
        List<Score> list = new ArrayList<>();
        List<String> lines = Files.readAllLines(gamedata, StandardCharsets.UTF_8);
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            if(line.isBlank()){
                continue;
            }
            String[] parts = line.split(",");
            String player = parts[0];
            int score = Integer.parseInt(parts[1]);
            String mode = parts[2];
            LocalDateTime playedAt = LocalDateTime.parse(parts[3]);
            list.add(new Score(player, score, mode, playedAt));
        }
        return list;
    }
}