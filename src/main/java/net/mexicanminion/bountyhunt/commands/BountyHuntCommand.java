package net.mexicanminion.bountyhunt.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.mexicanminion.bountyhunt.BountyHuntMod;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BountyHuntMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BountyHuntCommand {

    public BountyHuntCommand(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        /*dispatcher.register(
                LiteralArgumentBuilder.<CommandSourceStack>literal("bountyhunt")
                        .then(TPSCommand.register())
                        .then(TrackCommand.register())
                        .then(EntityCommand.register())
                        .then(GenerateCommand.register())
                        .then(DimensionsCommand.register())
                        .then(ModListCommand.register())
                        .then(TagsCommand.register())
        );*/
    }
}
