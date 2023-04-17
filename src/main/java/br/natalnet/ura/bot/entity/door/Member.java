package br.natalnet.ura.bot.entity.door;

import lombok.Data;

import java.util.UUID;

@Data
public class Member {

    private final UUID uuid;

    private final String name, rfid;
}
