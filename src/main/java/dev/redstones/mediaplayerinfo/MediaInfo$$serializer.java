package dev.redstones.mediaplayerinfo;

import kotlinx.serialization.KSerializer;
import kotlinx.serialization.SerializationException;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.*;
import kotlinx.serialization.internal.*;

/** Restores the generated serializer referenced by MediaInfo.Companion. */
public final class MediaInfo$$serializer implements GeneratedSerializer<MediaInfo> {
    public static final MediaInfo$$serializer INSTANCE = new MediaInfo$$serializer();
    private static final SerialDescriptor DESCRIPTOR;
    static {
        PluginGeneratedSerialDescriptor descriptor = new PluginGeneratedSerialDescriptor(
                "dev.redstones.mediaplayerinfo.MediaInfo", INSTANCE, 6);
        for (String field : new String[]{"title", "artist", "artworkPng", "position", "duration", "playing"}) {
            descriptor.addElement(field, false);
        }
        DESCRIPTOR = descriptor;
    }
    private MediaInfo$$serializer() {}
    public SerialDescriptor getDescriptor() { return DESCRIPTOR; }
    public KSerializer<?>[] childSerializers() {
        return new KSerializer<?>[]{StringSerializer.INSTANCE, StringSerializer.INSTANCE,
                ByteArraySerializer.INSTANCE, LongSerializer.INSTANCE, LongSerializer.INSTANCE, BooleanSerializer.INSTANCE};
    }
    public KSerializer<?>[] typeParametersSerializers() { return new KSerializer<?>[0]; }
    public void serialize(Encoder encoder, MediaInfo value) {
        CompositeEncoder output = encoder.beginStructure(DESCRIPTOR);
        MediaInfo.write$Self$MediaPlayerInfo(value, output, DESCRIPTOR);
        output.endStructure(DESCRIPTOR);
    }
    public MediaInfo deserialize(Decoder decoder) {
        CompositeDecoder input = decoder.beginStructure(DESCRIPTOR);
        String title = null, artist = null;
        byte[] artwork = null;
        long position = 0, duration = 0;
        boolean playing = false;
        int mask = 0;
        if (input.decodeSequentially()) {
            title = input.decodeStringElement(DESCRIPTOR, 0);
            artist = input.decodeStringElement(DESCRIPTOR, 1);
            artwork = input.decodeSerializableElement(DESCRIPTOR, 2, ByteArraySerializer.INSTANCE, null);
            position = input.decodeLongElement(DESCRIPTOR, 3);
            duration = input.decodeLongElement(DESCRIPTOR, 4);
            playing = input.decodeBooleanElement(DESCRIPTOR, 5);
            mask = 63;
        } else {
            while (true) {
                int index = input.decodeElementIndex(DESCRIPTOR);
                if (index == CompositeDecoder.DECODE_DONE) break;
                switch (index) {
                    case 0 -> { title = input.decodeStringElement(DESCRIPTOR, 0); mask |= 1; }
                    case 1 -> { artist = input.decodeStringElement(DESCRIPTOR, 1); mask |= 2; }
                    case 2 -> { artwork = input.decodeSerializableElement(DESCRIPTOR, 2, ByteArraySerializer.INSTANCE, artwork); mask |= 4; }
                    case 3 -> { position = input.decodeLongElement(DESCRIPTOR, 3); mask |= 8; }
                    case 4 -> { duration = input.decodeLongElement(DESCRIPTOR, 4); mask |= 16; }
                    case 5 -> { playing = input.decodeBooleanElement(DESCRIPTOR, 5); mask |= 32; }
                    default -> throw new SerializationException("Unknown MediaInfo field: " + index);
                }
            }
        }
        input.endStructure(DESCRIPTOR);
        return new MediaInfo(mask, title, artist, artwork, position, duration, playing, null);
    }
}
