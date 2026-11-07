package ru.method.celebression.integration.ui.manager.fontManager;

import java.util.Locale;

public final class RankTextGradient {

    @FunctionalInterface
    public interface CharColorEmitter {
        void emit(char c, float r, float g, float b);
    }

    private RankTextGradient() {
    }

    public static int tryProcess(String text, String upperText, int length, int i, CharColorEmitter emitter) {
        if (upperText.contains("PLAYER") && i + 6 <= length && upperText.substring(i, i + 6).equals("PLAYER")) {
            return emitWord(text, i, length, 6, 4.0f, 0.45f, 0.45f, 0.45f, emitter);
        }
        if (upperText.contains("BRAVO") && i + 5 <= length && upperText.substring(i, i + 5).equals("BRAVO")) {
            return emitWord(text, i, length, 5, 3.0f, 0.7f, 0.1f, 0.1f, emitter);
        }
        if (upperText.contains("SPONSOR") && i + 7 <= length && upperText.substring(i, i + 7).equals("SPONSOR")) {
            return emitWord(text, i, length, 7, 5.0f, 0.9f, 0.7f, 0.0f, emitter);
        }
        if (upperText.contains("PEGAS") && i + 5 <= length && upperText.substring(i, i + 5).equals("PEGAS")) {
            return emitWord(text, i, length, 5, 5.0f, 0.9f, 0.7f, 0.1f, emitter);
        }
        if (upperText.contains("КОШКА") && i + 5 <= length && upperText.substring(i, i + 5).equals("КОШКА")) {
            return emitWord(text, i, length, 5, 3.0f, 40 / 255.0f, 130 / 255.0f, 215 / 255.0f, emitter);
        }
        if (upperText.contains("GOD") && i + 3 <= length && upperText.substring(i, i + 3).equals("GOD")) {
            return emitWord(text, i, length, 3, 6.0f, 0.95f, 0.85f, 0.5f, emitter);
        }
        if (upperText.contains("HERO") && i + 4 <= length && upperText.substring(i, i + 4).equals("HERO")) {
            return emitWord(text, i, length, 4, 2.0f, 34 / 255.0f, 60 / 255.0f, 227 / 255.0f, emitter);
        }
        if (upperText.contains("BULL") && i + 4 <= length && upperText.substring(i, i + 4).equals("BULL")) {
            return emitWord(text, i, length, 4, 2.0f, 0.7f, 0.15f, 0.7f, emitter);
        }
        if (upperText.contains("IMPERATOR") && i + 9 <= length && upperText.substring(i, i + 9).equals("IMPERATOR")) {
            return emitWord(text, i, length, 9, 7.0f, 1.0f, 0.3f, 0.3f, emitter);
        }
        if (upperText.contains("DRACULA") && i + 7 <= length && upperText.substring(i, i + 7).equals("DRACULA")) {
            return emitWord(text, i, length, 7, 5.0f, 0.549f, 0.1f, 0.1f, emitter);
        }
        if (upperText.contains("VAMPIRE") && i + 7 <= length && upperText.substring(i, i + 7).equals("VAMPIRE")) {
            return emitWord(text, i, length, 7, 5.0f, 0.549f, 0.1f, 0.1f, emitter);
        }
        if (upperText.contains("DRAGON") && i + 6 <= length && upperText.substring(i, i + 6).equals("DRAGON")) {
            return emitWord(text, i, length, 6, 4.0f, 1.0f, 0.4f, 1.0f, emitter);
        }
        if (upperText.contains("TITAN") && i + 5 <= length && upperText.substring(i, i + 5).equals("TITAN")) {
            return emitWord(text, i, length, 5, 3.0f, 1.0f, 1.0f, 0.0f, emitter);
        }
        if (upperText.contains("AVENGER") && i + 7 <= length && upperText.substring(i, i + 7).equals("AVENGER")) {
            return emitWord(text, i, length, 7, 5.0f, 0.15f, 1.0f, 0.15f, emitter);
        }
        if (upperText.contains("OVERLORD") && i + 8 <= length && upperText.substring(i, i + 8).equals("OVERLORD")) {
            return emitWord(text, i, length, 8, 6.0f, 0.0f, 1.0f, 1.0f, emitter);
        }
        if (upperText.contains("MAGISTER") && i + 8 <= length && upperText.substring(i, i + 8).equals("MAGISTER")) {
            return emitWord(text, i, length, 8, 6.0f, 0.95f, 0.6f, 0.0f, emitter);
        }
        if (upperText.contains("COBRA") && i + 5 <= length && upperText.substring(i, i + 5).equals("COBRA")) {
            return emitWord(text, i, length, 5, 3.0f, 0.15f, 1.0f, 0.15f, emitter);
        }
        if (upperText.contains("HYDRA") && i + 5 <= length && upperText.substring(i, i + 5).equals("HYDRA")) {
            return emitWord(text, i, length, 5, 3.0f, 0.156f, 0.365f, 0.012f, emitter);
        }
        if (upperText.contains("RABBIT") && i + 6 <= length && upperText.substring(i, i + 6).equals("RABBIT")) {
            return emitWord(text, i, length, 6, 4.0f, 0.8f, 0.8f, 0.8f, emitter);
        }
        if (upperText.contains("D.HELPER") && i + 8 <= length && upperText.substring(i, i + 8).equals("D.HELPER")) {
            return emitWord(text, i, length, 8, 6.0f, 0.95f, 0.6f, 0.0f, emitter);
        }
        if (upperText.contains("HELPER") && i + 6 <= length && upperText.substring(i, i + 6).equals("HELPER")) {
            return emitWord(text, i, length, 6, 4.0f, 0.95f, 0.6f, 0.0f, emitter);
        }
        if (upperText.contains("TIGER") && i + 5 <= length && upperText.substring(i, i + 5).equals("TIGER")) {
            return emitWord(text, i, length, 5, 3.0f, 0.9f, 0.6f, 0.0f, emitter);
        }
        if (upperText.contains("BUNNY") && i + 5 <= length && upperText.substring(i, i + 5).equals("BUNNY")) {
            return emitWord(text, i, length, 5, 3.0f, 0.2f, 0.2f, 0.2f, emitter);
        }
        if (upperText.contains("MODER+") && i + 6 <= length && upperText.substring(i, i + 6).equals("MODER+")) {
            return emitWord(text, i, length, 6, 4.0f, 0.275f, 0.235f, 0.569f, emitter);
        }
        if (upperText.contains("ML.MODER") && i + 8 <= length && upperText.substring(i, i + 8).equals("ML.MODER")) {
            return emitWord(text, i, length, 8, 6.0f, 44 / 255.0f, 58 / 255.0f, 227 / 255.0f, emitter);
        }
        if (upperText.contains("D.ST.MODER") && i + 10 <= length && upperText.substring(i, i + 10).equals("D.ST.MODER")) {
            return emitWord(text, i, length, 10, 6.0f, 44 / 255.0f, 58 / 255.0f, 227 / 255.0f, emitter);
        }
        if (upperText.contains("ST.MODER") && i + 8 <= length && upperText.substring(i, i + 8).equals("ST.MODER")) {
            return emitWord(text, i, length, 8, 6.0f, 44 / 255.0f, 58 / 255.0f, 227 / 255.0f, emitter);
        }
        if (upperText.contains("MODER") && i + 5 <= length && upperText.substring(i, i + 5).equals("MODER")) {
            return emitWord(text, i, length, 5, 3.0f, 44 / 255.0f, 58 / 255.0f, 227 / 255.0f, emitter);
        }
        if (upperText.contains("GL.MODER") && i + 8 <= length && upperText.substring(i, i + 8).equals("GL.MODER")) {
            return emitWord(text, i, length, 8, 6.0f, 0.275f, 0.235f, 0.569f, emitter);
        }
        if (upperText.contains("YT") && i + 2 <= length && upperText.substring(i, i + 2).equals("YT")) {
            emitter.emit(text.charAt(i), 0.722f, 0.027f, 0.086f);
            if (i + 1 < length) {
                emitter.emit(text.charAt(i + 1), 1.0f, 1.0f, 1.0f);
            }
            return i + 1;
        }
        if (upperText.contains("MEDIA") && i + 5 <= length && upperText.substring(i, i + 5).equals("MEDIA")) {
            return emitWord(text, i, length, 5, 3.0f, 0.404f, 0.141f, 0.749f, emitter);
        }
        if (upperText.contains("ADMIN") && i + 5 <= length && upperText.substring(i, i + 5).equals("ADMIN")) {
            return emitWord(text, i, length, 5, 3.0f, 0.549f, 0.110f, 0.110f, emitter);
        }
        if (upperText.contains("ML.ADMIN") && i + 8 <= length && upperText.substring(i, i + 8).equals("ML.ADMIN")) {
            return emitWord(text, i, length, 8, 6.0f, 0.098f, 0.698f, 0.831f, emitter);
        }
        return -1;
    }

    private static int emitWord(String text, int i, int length, int wordLen, float tDivisor,
                                float endRed, float endGreen, float endBlue, CharColorEmitter emitter) {
        float startRed = endRed * 0.85f;
        float startGreen = endGreen * 0.85f;
        float startBlue = endBlue * 0.85f;
        for (int j = 0; j < wordLen && i + j < length; j++) {
            float t = j < wordLen - 1 ? (float) j / tDivisor : 1.0f;
            float r = startRed + t * (endRed - startRed);
            float g = startGreen + t * (endGreen - startGreen);
            float b = startBlue + t * (endBlue - startBlue);
            emitter.emit(text.charAt(i + j), r, g, b);
        }
        return i + wordLen - 1;
    }

    public static String upperCase(String text) {
        return text.toUpperCase(Locale.ENGLISH);
    }
}
