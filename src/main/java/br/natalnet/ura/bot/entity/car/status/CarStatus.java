package br.natalnet.ura.bot.entity.car.status;

import lombok.Getter;

@Getter
public enum CarStatus {

    CONNECTED("Conectado"),
    DISCONNECTED("Desconectado"),
    UNKNOWN("Desconhecido");

    public final String name;

    CarStatus(String name) {
        this.name = name;
    }
}
