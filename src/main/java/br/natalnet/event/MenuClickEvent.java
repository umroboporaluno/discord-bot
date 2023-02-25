package br.natalnet.event;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.GenericSelectMenuInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.AttachedFile;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Random;

public class MenuClickEvent extends ListenerAdapter {

    @Override
    public void onGenericSelectMenuInteraction(GenericSelectMenuInteractionEvent event) {

        if (!event.isFromGuild())
            return;

        if (event.getGuild() == null)
            return;

        Guild guild = event.getGuild();

        if (event.getValues().get(0).equals("menu-reopen")) {

            EnumSet<Permission> permissions = EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND);

            TextChannel textChannel = (TextChannel) event.getChannel();

            String id = textChannel.getTopic();

            if (id == null)
                return;

            guild.retrieveMemberById(id).queue(member -> textChannel.upsertPermissionOverride(member).setAllowed(permissions).queue());

            event.reply("Você reabriu o ticket!").setEphemeral(true).queue();

            event.getChannel().sendMessage("O ticket foi aberto novamente!").queue();

        } else if (event.getValues().get(0).equals("menu-delete")) {

            event.reply("Você encerrou o ticket!").setEphemeral(true).queue();

            event.getChannel().sendMessage("O ticket foi encerrado!").queue();

            ArrayList<String> arrayList = new ArrayList<>();

            event.getChannel().getHistory().retrievePast(99).queue(messages -> {

                messages.forEach(c -> arrayList.add("[LOG] " + c.getAuthor().getName() + ": "
                        + c.getContentRaw() + "\n"));

                Collections.reverse(arrayList);

                try {

                    File file = new File("ticket-" + new Random().nextInt(Integer.MAX_VALUE) + ".txt");

                    FileWriter writer = new FileWriter(file);

                    for (String msgs : arrayList) {
                        writer.append(msgs);
                    }

                    writer.close();

                    TextChannel textChannel = event.getGuild().getTextChannelById(1078863579299708999L);

                    if (textChannel == null)
                        return;

                    textChannel.sendFiles(AttachedFile.fromData(file)).queue(success -> {
                        event.getChannel().delete().queue();
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
