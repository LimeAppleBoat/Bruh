package com.jab125.limeappleboat.bruh.packet;

import com.jab125.limeappleboat.bruh.Bruh;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.ArrayList;


public class PacketHandler {
    public static Identifier SEND_MODS_PACKET_ID = new Identifier(Bruh.MODID.get(), "send_mods_packet");

    public static void sendMod() {
        ArrayList<String> mods = new ArrayList<>();
        for (var mod : FabricLoader.getInstance().getAllMods()) {
            mods.add(mod.getMetadata().getId());
        }
        String modString = "";
        var a =  mods.iterator();
        while (a.hasNext()) {
            modString = modString + a.next();
            if (a.hasNext()) modString = modString + "!";
        }

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(modString);
       ClientPlayNetworking.send(SEND_MODS_PACKET_ID, buf);
        //return TypedActionResult.success(Util.getEXP(playerToSend.getUuid()));
    }
}
