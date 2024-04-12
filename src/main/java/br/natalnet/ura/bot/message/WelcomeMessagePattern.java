package br.natalnet.ura.bot.message;

import lombok.Getter;

@Getter
public enum WelcomeMessagePattern {

    GRADUANDOS(":robot: - **Graduandos**", ":alarm_clock: Horário: **18:35 até às 20:30** \n:calendar: Início das aulas: **12/04/2024** \n:round_pushpin: Local: **Laboratório 1 ECT, 2 piso**"),
    PROFESSORES(":robot: - **Ensino Médio**", ":alarm_clock: Horário: **8:30 até às 11:30** \n:calendar: Início das aulas: **13/04/2024** \n:round_pushpin: Local: **Laboratório 1 ECT, 2 piso**"),
    ENSINO_MEDIO("", "");

    private final String title, subtitle;

    WelcomeMessagePattern(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }
}
