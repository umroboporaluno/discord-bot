package br.natalnet.ura.bot;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;

@Getter
@AllArgsConstructor
public abstract class BotProperties {

    public abstract void load(File file);

    public abstract void unload(File file);
}
