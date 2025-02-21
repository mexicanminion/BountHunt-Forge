package net.mexicanminion.bountyhunt.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.mexicanminion.bountyhunt.managers.BountyDataManager;
import net.mexicanminion.bountyhunt.util.BountyData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class SetBountyCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(
            Commands.literal("setbounty")
                .requires(source -> source.hasPermission(0)) // Permission level 0
                .then(Commands.argument("player", EntityArgument.player())
                        .executes(context -> setBounty(context.getSource(), EntityArgument.getPlayer(context, "player")))
                )
                .executes(context -> {
                    context.getSource().sendSuccess(() -> Component.literal("Usage: /setbounty <player>"), false);
                    return 1;
                })
        );
    }

    // Sets the bounty on a player
    public static int setBounty(CommandSourceStack context, ServerPlayer target){
        // Gets the source of the command assigns it to a ServerPlayerEntity object
        final ServerPlayer sender = context.getPlayer();

        // Checks if the sender is null, is so its from console; disallow
        if(sender == null) {
            context.sendSystemMessage(Component.literal("You must be a player to use this command!"));
            return 0;
        }

        // Checks if the sender is the target, if so disallow
        if(sender.getUUID() == target.getUUID()) {
            context.sendFailure(Component.literal("You cannot set a bounty on yourself!"));
            //return 0;
        }

        // Checks if the target has an entry in the master list, if not it adds one
        if(BountyDataManager.getBountyData(target.getUUID()) == null) {
            BountyDataManager.setBountyData(new BountyData(target.getUUID(), false, false, 0, 0, target.getGameProfile(), target.getName().getString(), null));
        }

        // Checks if the player has an entry in the master list, if not it adds one
        if(BountyDataManager.getBountyData(sender.getUUID()) == null) {
            BountyDataManager.setBountyData(new BountyData(sender.getUUID(), false, false, 0, 0, sender.getGameProfile(), sender.getName().getString(), null));
        }

        // Checks if the target has a bounty, if so disallow
        if(BountyDataManager.getBountyData(target.getUUID()).getHasBounty()) {
            context.sendFailure(Component.literal("That player already has a bounty!"));
            return 0;
        }

        // Opens the SetBountyGUI
        sender.sendSystemMessage(Component.literal("Opening UI"), false);
        /*try {
            SetBountyGUI bountyGUI = new SetBountyGUI(sender, false, contextServer, target, false);
            bountyGUI.open();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        return 1;
    }
}
