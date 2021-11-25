package com.jab125.limeappleboat.bruh;

import com.jab125.limeappleboat.bruh.packet.PacketHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ContactInformation;
import net.fabricmc.loader.api.metadata.Person;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;

import java.util.ArrayList;
import java.util.Collection;

public class Bruh implements ModInitializer {
    public static final ImmutableRegistry<String> MODID = new ImmutableRegistry<>("bruh");
    @Override
    public void onInitialize() {
        ServerPlayNetworking.registerGlobalReceiver(PacketHandler.SEND_MODS_PACKET_ID, (minecraftServer, serverPlayerEntity, serverPlayNetworkHandler, packetByteBuf, packetSender) -> {
            ImmutableRegistry<String> serializedList = new ImmutableRegistry<String>(packetByteBuf.readString());
            Collection<String> list = new ArrayList<>();
            for (int i = 0; i < serializedList.get().split("!").length; i++) {
                list.add(serializedList.get().split("!")[i]);
                System.out.println("CLIENT RECEIVER");
                System.out.println(serializedList.get().split("!")[i]);
            }

            Collection<String> serverList = new ArrayList<>();
            for (var i : FabricLoader.getInstance().getAllMods()) {
                System.out.println("SERVER");
                serverList.add(i.getMetadata().getId());
                System.out.println(i.getMetadata().getId());
            }
            int verifiedMods = 0;
            for (var a : list) {
                for (var b : serverList) {
                    if (b.equals(a)) {
                        ++verifiedMods;
                        break;
                    }
                }
            }
            System.out.println(verifiedMods + " " + FabricLoader.getInstance().getAllMods().size());
            int finalVerifiedMods = verifiedMods;
            minecraftServer.execute(() -> {
                if (finalVerifiedMods != FabricLoader.getInstance().getAllMods().size()) {
                    //minecraftServer.kickNonWhitelistedPlayers();
                    serverPlayerEntity.networkHandler.disconnect(new LiteralText("Found " + finalVerifiedMods + " verified mods, but "+ FabricLoader.getInstance().getAllMods().size() + " total mods."));
                    //System.out.println("THEY are THE SAME SIZE");

                }
            });
            //gif (verifiedMods == FabricLoader.getInstance().getAllMods().size()) ;
        });
    }
}
