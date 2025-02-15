package net.mexicanminion.bountyhunt.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;


public class HelpBounty {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
       dispatcher.register(
               Commands.literal("helpbounty")
                       .executes(HelpBounty::helpBounty)
       );
    }

    // Method to claim the bounty
    public static int helpBounty(CommandContext<CommandSourceStack> context) {
        // Gets the source of the command and assigns to a ServerPlayerEntity object
        final CommandSourceStack source = context.getSource();
        final ServerPlayer sender = source.getPlayer();

        source.sendSuccess(()-> Component.literal("/setbounty {player}: Set a bounty on a online player"), false);
        source.sendSuccess(()-> Component.literal("/adjustbounty: See the bounties you can increase"), false);
        source.sendSuccess(()-> Component.literal("/claimbounty: Claim your bounty"), false);
        source.sendSuccess(()-> Component.literal("/bountyboard: See all bounties"), false);
        source.sendSuccess(()-> Component.literal("/helpbounty: See list of Bounty Hunt commands"), false);

        assert sender != null;
        if(sender.hasPermissions(3)){
            source.sendSuccess(()-> Component.literal("/bountyitemtype {item}: Set the item type for bounties"), false);
            source.sendSuccess(()-> Component.literal("/bountyItemType custom {ingot}: Set custom item type for bounties, ingot only"), false);
            source.sendSuccess(()-> Component.literal("/bountyItemType custom <ingot> <block> <ingotToBlockAmount>: Set custom item type for bounties"), false);
            source.sendSuccess(()-> Component.literal("/bountyItemType confirm: confirm currency change if needed"), false);
            source.sendSuccess(()-> Component.literal("/bountyannouncement {amount}: Set the announcement amount for bounties"), false);
        }

        return 0;
    }
}
