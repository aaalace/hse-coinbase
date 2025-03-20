package com.aaalace.hsecoinbase.util;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
public class SessionLogger {

    private UUID sessionId;

    public void log() {
        log.info("Текущая сессия: {}", sessionId);
    }
}
