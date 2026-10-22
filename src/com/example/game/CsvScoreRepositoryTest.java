package com.example.game;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;

public class CsvScoreRepositoryTest {
    static int pass = 0, fail = 0;

    static void eq(String what, Object expected, Object actual) {
        boolean ok = java.util.Objects.equals(expected, actual);
        if (ok) pass++; else fail++;
        System.out.printf("%s %-36s 期望=%-16s 实际=%s%n",
                ok ? "✅" : "❌", what, expected, actual);
    }

    public static void main(String[] args) throws IOException {
        Path tmp = Files.createTempFile("scores-test-", ".csv");
        Files.deleteIfExists(tmp);                       // 从"文件不存在"开始

        CsvScoreRepository repo = new CsvScoreRepository(tmp);

        // 1. 文件不存在 → 空 List，不崩
        eq("文件不存在返回空", 0, repo.loadAll().size());

        // 2. save 3 条
        repo.save(new Score("张三", 21, "normal", LocalDateTime.of(2026, 10, 22, 21, 40)));
        repo.save(new Score("李四", 18, "normal", LocalDateTime.of(2026, 10, 22, 21, 41)));
        repo.save(new Score("王五", 15, "normal", LocalDateTime.of(2026, 10, 22, 21, 42)));

        // 3. 读回 3 条（不是 2 条 —— 抓"第一次只写表头"的 bug）
        List<Score> all = repo.loadAll();
        eq("读回 3 条", 3, all.size());

        // 4. round-trip 内容对
        eq("第1条玩家", "张三", all.get(0).player());
        eq("第1条分数", 21, all.get(0).score());
        eq("第1条模式", "normal", all.get(0).mode());

        // 5. 表头正确（第一行是列名，不是数据）
        eq("表头正确", "player_name,score,mode,played_at",
                Files.readAllLines(tmp).get(0));

        // 6. 追加生效：再存 1 条 → 读回 4 条
        repo.save(new Score("赵六", 12, "normal", LocalDateTime.of(2026, 10, 22, 21, 43)));
        eq("追加后 4 条", 4, repo.loadAll().size());

        Files.deleteIfExists(tmp);                       // 清理临时文件

        System.out.printf("%n结果：通过 %d / 失败 %d%n", pass, fail);
    }
}