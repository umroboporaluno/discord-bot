package br.natalnet.ura.bot.message;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum WelcomeMessagePattern {

    GRADUANDOS("\uD83E\uDD16 - Graduandos", "⏰ Horário: **18:35 até às 20:30** \n\uD83D\uDDD3\uFE0F Início das aulas: **12/04/2024** \n\uD83D\uDCCD Local: **Laboratório 1 ECT, 2 piso**"),
    PROFESSORES("\uD83E\uDD16 - Ensino Médio", "⏰ Horário: **8:30 até às 11:30** \n\uD83D\uDDD3\uFE0F Início das aulas: **13/04/2024** \n\uD83D\uDCCD Local: **Laboratório 1 ECT, 2 piso**"),
    ENSINO_MEDIO("\uD83E\uDD16 - Professores", "");

    private final String title, subtitle;

    WelcomeMessagePattern(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    public static WelcomeMessagePattern getFromCourse(String name) {
        return Arrays.stream(values()).filter(course -> course.name().equals(name)).findFirst().orElse(null);
    }
}
