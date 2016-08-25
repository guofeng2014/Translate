package com.translate;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 作者: guofeng
 * 日期: 16/8/25.
 */
public class ThreadPools {

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void executor(@NotNull Runnable runnable) {
        executor.execute(runnable);
    }
}
