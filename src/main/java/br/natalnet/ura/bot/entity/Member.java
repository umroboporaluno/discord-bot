package br.natalnet.ura.bot.entity;

import lombok.Data;

import java.util.UUID;

@Data
public class Member {

    private final UUID uuid;

    private final String name, role, rfid;
}
