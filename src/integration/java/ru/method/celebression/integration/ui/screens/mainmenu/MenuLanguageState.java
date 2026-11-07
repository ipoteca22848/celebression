package ru.method.celebression.integration.ui.screens.mainmenu;

import ru.method.celebression.integration.ui.screens.mainmenu.render.font.FontAtlas;

public final class MenuLanguageState
{
    public enum UiFamily
    {
        ENGLISH,
        RUSSIAN,
        KAZAKH,
        UKRAINIAN,
        POLISH,
        FRENCH,
        GERMAN,
        SPANISH,
        PORTUGUESE,
        DUTCH,
        ITALIAN,
        TURKISH,
        HINDI,
        INDONESIAN,
        VIETNAMESE,
        THAI,
        ARABIC,
        HEBREW,
        JAPANESE,
        CHINESE,
        KOREAN,
        SWAHILI
    }

    public enum Language
    {
        ENGLISH_UNITED_KINGDOM("english_uk", "English", "English", "EN", UiFamily.ENGLISH),
        ENGLISH_UNITED_STATES("english_us", "English", "English", "EN", UiFamily.ENGLISH),
        ENGLISH_CANADA("english_ca", "English", "English", "EN", UiFamily.ENGLISH),
        ENGLISH_AUSTRALIA("english_au", "English", "English", "EN", UiFamily.ENGLISH),
        ENGLISH_NEW_ZEALAND("english_nz", "English", "English", "EN", UiFamily.ENGLISH),
        ENGLISH_SOUTH_AFRICA("english_za", "English", "English", "EN", UiFamily.ENGLISH),
        ENGLISH_SINGAPORE("english_sg", "English", "English", "EN", UiFamily.ENGLISH),
        RUSSIAN_RUSSIA("russian_ru", "\u0420\u0443\u0441\u0441\u043a\u0438\u0439", "Russian", "RU", UiFamily.RUSSIAN),
        KAZAKH_KAZAKHSTAN("kazakh_kz", "\u049a\u0430\u0437\u0430\u049b\u0448\u0430", "Kazakh", "KZ", UiFamily.KAZAKH),
        UKRAINIAN_UKRAINE("ukrainian_ua", "\u0423\u043a\u0440\u0430\u0457\u043d\u0441\u044c\u043a\u0430", "Ukrainian", "UA", UiFamily.UKRAINIAN),
        POLISH_POLAND("polish_pl", "Polski", "Polish", "PL", UiFamily.POLISH),
        FRENCH_FRANCE("french_fr", "Fran\u00e7ais", "French", "FR", UiFamily.FRENCH),
        FRENCH_MOROCCO("french_ma", "Fran\u00e7ais", "French", "FR", UiFamily.FRENCH),
        FRENCH_CANADA("french_ca", "Fran\u00e7ais", "French", "FR", UiFamily.FRENCH),
        GERMAN_GERMANY("german_de", "Deutsch", "German", "DE", UiFamily.GERMAN),
        SPANISH_SPAIN("spanish_es", "Espa\u00f1ol", "Spanish", "ES", UiFamily.SPANISH),
        SPANISH_MEXICO("spanish_mx", "Espa\u00f1ol", "Spanish", "ES", UiFamily.SPANISH),
        SPANISH_ARGENTINA("spanish_ar", "Espa\u00f1ol", "Spanish", "ES", UiFamily.SPANISH),
        SPANISH_CHILE("spanish_cl", "Espa\u00f1ol", "Spanish", "ES", UiFamily.SPANISH),
        PORTUGUESE_PORTUGAL("portuguese_pt", "Portugu\u00eas", "Portuguese", "PT", UiFamily.PORTUGUESE),
        PORTUGUESE_BRAZIL("portuguese_br", "Portugu\u00eas", "Portuguese", "PT", UiFamily.PORTUGUESE),
        ARABIC_EGYPT("arabic_eg", "\u0627\u0644\u0639\u0631\u0628\u064a\u0629", "Arabic", "AR", UiFamily.ARABIC),
        ARABIC_SAUDI_ARABIA("arabic_sa", "\u0627\u0644\u0639\u0631\u0628\u064a\u0629", "Arabic", "AR", UiFamily.ARABIC),
        ARABIC_UAE("arabic_ae", "\u0627\u0644\u0639\u0631\u0628\u064a\u0629", "Arabic", "AR", UiFamily.ARABIC),
        JAPANESE_JAPAN("japanese_jp", "\u65e5\u672c\u8a9e", "Japanese", "JP", UiFamily.JAPANESE),
        CHINESE_SIMPLIFIED_CHINA("chinese_cn", "\u7b80\u4f53\u4e2d\u6587", "Simplified Chinese", "ZH", UiFamily.CHINESE),
        KOREAN_SOUTH_KOREA("korean_kr", "\ud55c\uad6d\uc5b4", "Korean", "KO", UiFamily.KOREAN),
        HINDI_INDIA("hindi_in", "\u0939\u093f\u0928\u094d\u0926\u0940", "Hindi", "HI", UiFamily.HINDI),
        TURKISH_TURKEY("turkish_tr", "T\u00fcrk\u00e7e", "Turkish", "TR", UiFamily.TURKISH),
        DUTCH_NETHERLANDS("dutch_nl", "Nederlands", "Dutch", "NL", UiFamily.DUTCH),
        ITALIAN_ITALY("italian_it", "Italiano", "Italian", "IT", UiFamily.ITALIAN),
        INDONESIAN_INDONESIA("indonesian_id", "Bahasa Indonesia", "Indonesian", "ID", UiFamily.INDONESIAN),
        VIETNAMESE_VIETNAM("vietnamese_vn", "Ti\u1ebfng Vi\u1ec7t", "Vietnamese", "VI", UiFamily.VIETNAMESE),
        THAI_THAILAND("thai_th", "\u0e44\u0e17\u0e22", "Thai", "TH", UiFamily.THAI),
        HEBREW_ISRAEL("hebrew_il", "\u05e2\u05d1\u05e8\u05d9\u05ea", "Hebrew", "HE", UiFamily.HEBREW),
        SWAHILI_KENYA("swahili_ke", "Kiswahili", "Swahili", "SW", UiFamily.SWAHILI);

        private final String code;
        private final String nativeName;
        private final String englishName;
        private final String shortCode;
        private final UiFamily uiFamily;

        Language(String code, String nativeName, String englishName, String shortCode, UiFamily uiFamily)
        {
            this.code = code;
            this.nativeName = nativeName;
            this.englishName = englishName;
            this.shortCode = shortCode;
            this.uiFamily = uiFamily;
        }

        public String code()
        {
            return code;
        }

        public String nativeName()
        {
            return nativeName;
        }

        public String displayNativeName(FontAtlas atlas)
        {
            if (atlas == null || supportsNativeName(atlas)) {
                return nativeName;
            }
            return englishName;
        }

        public boolean supportsNativeName(FontAtlas atlas)
        {
            if (atlas == null) {
                return true;
            }
            return nativeName.codePoints().allMatch(atlas::hasGlyph);
        }

        public String englishName()
        {
            return englishName;
        }

        public String shortCode()
        {
            return shortCode;
        }

        public UiFamily uiFamily()
        {
            return uiFamily;
        }

        public static Language byCode(String raw)
        {
            if (raw == null || raw.isBlank()) {
                return ENGLISH_UNITED_KINGDOM;
            }

            String normalized = raw.trim();
            if (normalized.equalsIgnoreCase("russian_kz")) {
                return KAZAKH_KAZAKHSTAN;
            }

            for (Language language : values())
            {
                if (language.code.equalsIgnoreCase(normalized)) {
                    return language;
                }
            }
            return byLegacyName(normalized);
        }

        private static Language byLegacyName(String raw)
        {
            return switch (raw.toUpperCase(java.util.Locale.ROOT)) {
                case "ENGLISH", "ENG" -> ENGLISH_UNITED_KINGDOM;
                case "RUSSIAN", "RUS" -> RUSSIAN_RUSSIA;
                case "POLISH", "PL" -> POLISH_POLAND;
                case "UKRAINIAN", "UKR" -> UKRAINIAN_UKRAINE;
                case "KAZAKH", "KZ" -> KAZAKH_KAZAKHSTAN;
                default -> {
                    try {
                        yield valueOf(raw);
                    } catch (IllegalArgumentException ignored) {
                        yield ENGLISH_UNITED_KINGDOM;
                    }
                }
            };
        }

        public Language nextSelectable()
        {
            Language[] options = SELECTABLE_LANGUAGES;
            for (int i = 0; i < options.length; i++) {
                if (options[i] == this) {
                    return options[(i + 1) % options.length];
                }
            }
            return options[0];
        }

        public Language previousSelectable()
        {
            Language[] options = SELECTABLE_LANGUAGES;
            for (int i = 0; i < options.length; i++) {
                if (options[i] == this) {
                    return options[(i - 1 + options.length) % options.length];
                }
            }
            return options[options.length - 1];
        }
    }

    public static final Language[] SELECTABLE_LANGUAGES = {
            Language.INDONESIAN_INDONESIA,
            Language.KAZAKH_KAZAKHSTAN,
            Language.ENGLISH_UNITED_KINGDOM,
            Language.FRENCH_FRANCE,
            Language.GERMAN_GERMANY,
            Language.ITALIAN_ITALY,
            Language.DUTCH_NETHERLANDS,
            Language.POLISH_POLAND,
            Language.PORTUGUESE_PORTUGAL,
            Language.SPANISH_SPAIN,
            Language.UKRAINIAN_UKRAINE,
            Language.RUSSIAN_RUSSIA
    };

    private static Language selected = Language.ENGLISH_UNITED_KINGDOM;

    private MenuLanguageState()
    {
    }

    public static Language selected()
    {
        return selected;
    }

    public static UiFamily uiFamily()
    {
        return selected.uiFamily();
    }

    public static void select(Language language)
    {
        selected = normalizeSelectable(language == null ? Language.ENGLISH_UNITED_KINGDOM : language);
    }

    public static void load(String raw)
    {
        selected = normalizeSelectable(Language.byCode(raw));
    }

    private static Language normalizeSelectable(Language language)
    {
        if (language == Language.ENGLISH_SINGAPORE) {
            return Language.ENGLISH_UNITED_KINGDOM;
        }
        return language;
    }

    public static String code()
    {
        return selected.code();
    }

    public static String text(String key)
    {
        return translate(selected.uiFamily(), key);
    }

    public static String format(String key, Object... args)
    {
        return String.format(java.util.Locale.ROOT, text(key), args);
    }

    private static String translate(UiFamily family, String key)
    {
        String value = switch (family)
        {
            case ENGLISH -> english(key);
            case RUSSIAN -> russian(key);
            case KAZAKH -> kazakh(key);
            case UKRAINIAN -> ukrainian(key);
            case POLISH -> polish(key);
            case FRENCH -> french(key);
            case GERMAN -> german(key);
            case SPANISH -> spanish(key);
            case PORTUGUESE -> portuguese(key);
            case DUTCH -> dutch(key);
            case ITALIAN -> italian(key);
            case TURKISH -> turkish(key);
            case HINDI -> hindi(key);
            case INDONESIAN -> indonesian(key);
            case VIETNAMESE -> vietnamese(key);
            case THAI -> thai(key);
            case ARABIC -> arabic(key);
            case HEBREW -> hebrew(key);
            case JAPANESE -> japanese(key);
            case CHINESE -> chinese(key);
            case KOREAN -> korean(key);
            case SWAHILI -> swahili(key);
        };
        if (value.equals(key) && family != UiFamily.ENGLISH)
        {
            return english(key);
        }
        return value;
    }

    private static String english(String key)
    {
        return switch (key)
        {
            case "language.title" -> "Select your language or region";
            case "language.subtitle" -> "Choose how celebression should appear for you.";
            case "language.hint" -> "Search or choose a language below.";
            case "language.current_selection" -> "Current selection: %s";
            case "language.search_placeholder" -> "Search for your language or region";
            case "language.empty_region" -> "No language options in this region yet.";
            case "language.no_results_title" -> "No languages found";
            case "language.no_results_subtitle" -> "Try another language, country, or region name.";
            case "region.africa" -> "Africa";
            case "region.americas" -> "Americas";
            case "region.asia" -> "Asia";
            case "region.australia_oceania" -> "Australia/Oceania";
            case "region.europe" -> "Europe";
            case "region.latin_america" -> "Latin America";
            case "region.middle_east" -> "Middle East";
            case "region.north_america" -> "North America";
            case "menu.username_placeholder" -> "Your username";
            case "menu.create_account" -> "Create account";
            case "menu.created_prefix" -> "Created";
            case "menu.selected_account" -> "Selected Account";
            case "menu.select_account" -> "Select Account";
            case "menu.select_account_bottom" -> "Select account";
            case "menu.settings" -> "Settings";
            case "menu.singleplayer" -> "Singleplayer";
            case "menu.multiplayer" -> "Multiplayer";
            case "menu.quit" -> "Quit";
            case "menu.footer_line1" -> "By logging into your account you agree to all of our policies,";
            case "menu.footer_line2" -> "including our Privacy Policy and Terms of Service";
            case "menu.privacy_policy" -> "Privacy Policy";
            case "menu.terms_of_service" -> "Terms of Service";
            default -> key;
        };
    }

    private static String russian(String key)
    {
        return switch (key)
        {
            case "language.title" -> "\u0412\u044b\u0431\u0435\u0440\u0438\u0442\u0435 \u044f\u0437\u044b\u043a \u0438\u043b\u0438 \u0440\u0435\u0433\u0438\u043e\u043d";
            case "language.subtitle" -> "\u0412\u044b\u0431\u0435\u0440\u0438\u0442\u0435, \u043a\u0430\u043a \u0438\u043c\u0435\u043d\u043d\u043e celebression \u0434\u043e\u043b\u0436\u0435\u043d \u0432\u044b\u0433\u043b\u044f\u0434\u0435\u0442\u044c \u0434\u043b\u044f \u0432\u0430\u0441.";
            case "language.hint" -> "\u041d\u0430\u0439\u0434\u0438\u0442\u0435 \u0438\u043b\u0438 \u0432\u044b\u0431\u0435\u0440\u0438\u0442\u0435 \u044f\u0437\u044b\u043a \u043d\u0438\u0436\u0435.";
            case "language.current_selection" -> "\u0422\u0435\u043a\u0443\u0449\u0438\u0439 \u0432\u044b\u0431\u043e\u0440: %s";
            case "language.search_placeholder" -> "\u041d\u0430\u0439\u0442\u0438 \u044f\u0437\u044b\u043a \u0438\u043b\u0438 \u0440\u0435\u0433\u0438\u043e\u043d";
            case "language.empty_region" -> "\u0412 \u044d\u0442\u043e\u043c \u0440\u0435\u0433\u0438\u043e\u043d\u0435 \u043f\u043e\u043a\u0430 \u043d\u0435\u0442 \u044f\u0437\u044b\u043a\u043e\u0432.";
            case "language.no_results_title" -> "\u041d\u0438\u0447\u0435\u0433\u043e \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u043e";
            case "language.no_results_subtitle" -> "\u041f\u043e\u043f\u0440\u043e\u0431\u0443\u0439\u0442\u0435 \u0434\u0440\u0443\u0433\u043e\u0439 \u044f\u0437\u044b\u043a, \u0441\u0442\u0440\u0430\u043d\u0443 \u0438\u043b\u0438 \u0440\u0435\u0433\u0438\u043e\u043d.";
            case "region.africa" -> "\u0410\u0444\u0440\u0438\u043a\u0430";
            case "region.americas" -> "\u0410\u043c\u0435\u0440\u0438\u043a\u0430";
            case "region.asia" -> "\u0410\u0437\u0438\u044f";
            case "region.australia_oceania" -> "\u0410\u0432\u0441\u0442\u0440\u0430\u043b\u0438\u044f/\u041e\u043a\u0435\u0430\u043d\u0438\u044f";
            case "region.europe" -> "\u0415\u0432\u0440\u043e\u043f\u0430";
            case "region.latin_america" -> "\u041b\u0430\u0442\u0438\u043d\u0441\u043a\u0430\u044f \u0410\u043c\u0435\u0440\u0438\u043a\u0430";
            case "region.middle_east" -> "\u0411\u043b\u0438\u0436\u043d\u0438\u0439 \u0412\u043e\u0441\u0442\u043e\u043a";
            case "region.north_america" -> "\u0421\u0435\u0432\u0435\u0440\u043d\u0430\u044f \u0410\u043c\u0435\u0440\u0438\u043a\u0430";
            case "menu.username_placeholder" -> "\u0412\u0430\u0448 \u043d\u0438\u043a\u043d\u0435\u0439\u043c";
            case "menu.create_account" -> "\u0421\u043e\u0437\u0434\u0430\u0442\u044c \u0430\u043a\u043a\u0430\u0443\u043d\u0442";
            case "menu.created_prefix" -> "\u0421\u043e\u0437\u0434\u0430\u043d";
            case "menu.selected_account" -> "\u0412\u044b\u0431\u0440\u0430\u043d\u043d\u044b\u0439 \u0430\u043a\u043a\u0430\u0443\u043d\u0442";
            case "menu.select_account" -> "\u0412\u044b\u0431\u0440\u0430\u0442\u044c \u0430\u043a\u043a\u0430\u0443\u043d\u0442";
            case "menu.select_account_bottom" -> "\u0412\u044b\u0431\u0440\u0430\u0442\u044c \u0430\u043a\u043a\u0430\u0443\u043d\u0442";
            case "menu.settings" -> "\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438";
            case "menu.singleplayer" -> "\u041e\u0434\u0438\u043d\u043e\u0447\u043d\u0430\u044f \u0438\u0433\u0440\u0430";
            case "menu.multiplayer" -> "\u0421\u0435\u0442\u0435\u0432\u0430\u044f \u0438\u0433\u0440\u0430";
            case "menu.quit" -> "\u0412\u044b\u0439\u0442\u0438";
            case "menu.footer_line1" -> "\u0412\u0445\u043e\u0434\u044f \u0432 \u0430\u043a\u043a\u0430\u0443\u043d\u0442, \u0432\u044b \u0441\u043e\u0433\u043b\u0430\u0448\u0430\u0435\u0442\u0435\u0441\u044c \u0441\u043e \u0432\u0441\u0435\u043c\u0438 \u043d\u0430\u0448\u0438\u043c\u0438 \u043f\u0440\u0430\u0432\u0438\u043b\u0430\u043c\u0438,";
            case "menu.footer_line2" -> "\u0432\u043a\u043b\u044e\u0447\u0430\u044f \u043d\u0430\u0448\u0443 \u041f\u043e\u043b\u0438\u0442\u0438\u043a\u0443 \u043a\u043e\u043d\u0444\u0438\u0434\u0435\u043d\u0446\u0438\u0430\u043b\u044c\u043d\u043e\u0441\u0442\u0438 \u0438 \u0423\u0441\u043b\u043e\u0432\u0438\u044f \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u044f";
            case "menu.privacy_policy" -> "\u041f\u043e\u043b\u0438\u0442\u0438\u043a\u0443 \u043a\u043e\u043d\u0444\u0438\u0434\u0435\u043d\u0446\u0438\u0430\u043b\u044c\u043d\u043e\u0441\u0442\u0438";
            case "menu.terms_of_service" -> "\u0423\u0441\u043b\u043e\u0432\u0438\u044f \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u044f";
            default -> key;
        };
    }

    private static String kazakh(String key)
    {
        return switch (key)
        {
            case "language.title" -> "\u0422\u0456\u043b\u0434\u0456 \u043d\u0435\u043c\u0435\u0441\u0435 \u0430\u0439\u043c\u0430\u049b\u0442\u044b \u0442\u0430\u04a3\u0434\u0430\u04a3\u044b\u0437";
            case "language.subtitle" -> "celebression \u0441\u0456\u0437 \u04af\u0448\u0456\u043d \u049b\u0430\u043b\u0430\u0439 \u043a\u04e9\u0440\u0456\u043d\u0435\u0442\u0456\u043d\u0456 \u0442\u0430\u04a3\u0434\u0430\u04a3\u044b\u0437.";
            case "language.hint" -> "\u0422\u0456\u043b\u0434\u0456 \u0442\u04e9\u043c\u0435\u043d\u043d\u0435\u043d \u0456\u0437\u0434\u0435\u04a3 \u043d\u0435\u043c\u0435\u0441\u0435 \u0442\u0430\u04a3\u0434\u0430\u04a3\u044b\u0437.";
            case "language.current_selection" -> "\u049a\u0430\u0437\u0456\u0440\u0433\u0456 \u0442\u0430\u04a3\u0434\u0430\u0443: %s";
            case "language.search_placeholder" -> "\u0422\u0456\u043b\u0434\u0456 \u043d\u0435\u043c\u0435\u0441\u0435 \u0430\u0439\u043c\u0430\u049b\u0442\u044b \u0456\u0437\u0434\u0435\u04a3";
            case "language.empty_region" -> "\u0411\u04b1\u043b \u0430\u0439\u043c\u0430\u049b\u0442\u0430 \u04d9\u0437\u0456\u0440\u0433\u0435 \u0442\u0456\u043b\u0434\u0435\u0440 \u0436\u043e\u049b.";
            case "language.no_results_title" -> "\u0415\u0448\u0442\u0435\u04a3\u0435 \u0442\u0430\u0431\u044b\u043b\u043c\u0430\u0434\u044b";
            case "language.no_results_subtitle" -> "\u0411\u0430\u0441\u049b\u0430 \u0442\u0456\u043b, \u0435\u043b \u043d\u0435\u043c\u0435\u0441\u0435 \u0430\u0442\u0430\u0443\u044b\u043d \u043a\u04e9\u0440\u0456\u04a3\u0456\u0437.";
            case "region.africa" -> "\u0410\u0444\u0440\u0438\u043a\u0430";
            case "region.americas" -> "\u0410\u043c\u0435\u0440\u0438\u043a\u0430";
            case "region.asia" -> "\u0410\u0437\u0438\u044f";
            case "region.australia_oceania" -> "\u0410\u0432\u0441\u0442\u0440\u0430\u043b\u0438\u044f/\u041e\u043a\u0435\u0430\u043d\u0438\u044f";
            case "region.europe" -> "\u0415\u04b0\u0440\u043e\u043f\u0430";
            case "region.latin_america" -> "\u041b\u0430\u0442\u044b\u043d \u0410\u043c\u0435\u0440\u0438\u043a\u0430\u0441\u044b";
            case "region.middle_east" -> "\u0416\u0430\u0443\u044b\u043d \u0428\u044b\u0493\u044b\u0441";
            case "region.north_america" -> "\u0421\u043e\u043b\u04b1\u0442\u0456\u043a \u0410\u043c\u0435\u0440\u0438\u043a\u0430";
            case "menu.username_placeholder" -> "\u041f\u0430\u0439\u0434\u0430\u043b\u0430\u043d\u0443\u0448\u044b \u0430\u0442\u044b\u04a3\u044b\u0437";
            case "menu.create_account" -> "\u0410\u043a\u0430\u0443\u043d\u0442 \u0436\u0430\u0441\u0430\u0443";
            case "menu.created_prefix" -> "\u0416\u0430\u0441\u0430\u043b\u0493\u0430\u043d";
            case "menu.selected_account" -> "\u0422\u0430\u04a3\u0434\u0430\u043b\u0493\u0430\u043d \u0430\u043a\u0430\u0443\u043d\u0442";
            case "menu.select_account" -> "\u0410\u043a\u0430\u0443\u043d\u0442\u0442\u044b \u0442\u0430\u04a3\u0434\u0430\u0443";
            case "menu.select_account_bottom" -> "\u0410\u043a\u0430\u0443\u043d\u0442\u0442\u044b \u0442\u0430\u04a3\u0434\u0430\u0443";
            case "menu.settings" -> "\u0411\u0430\u043f\u0442\u0430\u0443\u043b\u0430\u0440";
            case "menu.singleplayer" -> "\u0416\u0430\u043b\u0493\u044b\u0437 \u043e\u0439\u044b\u043d";
            case "menu.multiplayer" -> "\u0416\u0435\u043b\u0456\u043b\u0456\u043a \u043e\u0439\u044b\u043d";
            case "menu.quit" -> "\u0428\u044b\u0493\u0443";
            case "menu.footer_line1" -> "\u0410\u043a\u0430\u0443\u043d\u0442\u044b\u04a3\u044b\u0437\u0493\u0430 \u043a\u0456\u0440\u0433\u0435\u043d\u0434\u0435 \u0431\u0430\u0440\u043b\u044b\u049b \u0435\u0440\u0435\u0436\u0435\u043b\u0435\u0440\u0456\u043c\u0456\u0437\u0433\u0435 \u043a\u0435\u043b\u0456\u0441\u0435\u0441\u0456\u0437,";
            case "menu.footer_line2" -> "\u043e\u0441\u044b \u0431\u0456\u0437\u0434\u0456\u043d \u049a\u0443\u043f\u0438\u044f\u043b\u044b\u049b \u0441\u0430\u044f\u0441\u0430\u0442\u044b\u043c\u044b\u0437\u0431\u0430 \u0436\u04d9\u043d\u0435 \u041f\u0430\u0439\u0434\u0430\u043b\u0430\u043d\u0443 \u0448\u0430\u0440\u0442\u0442\u0430\u0440\u044b\u043d\u0430 \u043a\u0435\u043b\u0456\u0441\u0435\u0441\u0456\u0437";
            case "menu.privacy_policy" -> "\u049a\u0443\u043f\u0438\u044f\u043b\u044b\u049b \u0441\u0430\u044f\u0441\u0430\u0442\u044b";
            case "menu.terms_of_service" -> "\u041f\u0430\u0439\u0434\u0430\u043b\u0430\u043d\u0443 \u0448\u0430\u0440\u0442\u0442\u0430\u0440\u044b";
            default -> key;
        };
    }

    private static String ukrainian(String key)
    {
        return switch (key)
        {
            case "language.title" -> "\u041e\u0431\u0435\u0440\u0456\u0442\u044c \u043c\u043e\u0432\u0443 \u0430\u0431\u043e \u0440\u0435\u0433\u0456\u043e\u043d";
            case "language.subtitle" -> "\u041e\u0431\u0435\u0440\u0456\u0442\u044c, \u044f\u043a \u0441\u0430\u043c\u0435 celebression \u043c\u0430\u0454 \u0432\u0438\u0433\u043b\u044f\u0434\u0430\u0442\u0438 \u0434\u043b\u044f \u0432\u0430\u0441.";
            case "language.hint" -> "\u0417\u043d\u0430\u0439\u0434\u0456\u0442\u044c \u0430\u0431\u043e \u043e\u0431\u0435\u0440\u0456\u0442\u044c \u043c\u043e\u0432\u0443 \u043d\u0438\u0436\u0447\u0435.";
            case "language.current_selection" -> "\u041f\u043e\u0442\u043e\u0447\u043d\u0438\u0439 \u0432\u0438\u0431\u0456\u0440: %s";
            case "language.search_placeholder" -> "\u0417\u043d\u0430\u0439\u0442\u0438 \u043c\u043e\u0432\u0443 \u0430\u0431\u043e \u0440\u0435\u0433\u0456\u043e\u043d";
            case "language.empty_region" -> "\u0423 \u0446\u044c\u043e\u043c\u0443 \u0440\u0435\u0433\u0456\u043e\u043d\u0456 \u043f\u043e\u043a\u0438 \u043d\u0435\u043c\u0430\u0454 \u043c\u043e\u0432.";
            case "language.no_results_title" -> "\u041d\u0456\u0447\u043e\u0433\u043e \u043d\u0435 \u0437\u043d\u0430\u0439\u0434\u0435\u043d\u043e";
            case "language.no_results_subtitle" -> "\u0421\u043f\u0440\u043e\u0431\u0443\u0439\u0442\u0435 \u0456\u043d\u0448\u0443 \u043c\u043e\u0432\u0443, \u043a\u0440\u0430\u0457\u043d\u0443 \u0430\u0431\u043e \u0440\u0435\u0433\u0456\u043e\u043d.";
            case "region.africa" -> "\u0410\u0444\u0440\u0438\u043a\u0430";
            case "region.americas" -> "\u0410\u043c\u0435\u0440\u0438\u043a\u0430";
            case "region.asia" -> "\u0410\u0437\u0456\u044f";
            case "region.australia_oceania" -> "\u0410\u0432\u0441\u0442\u0440\u0430\u043b\u0456\u044f/\u041e\u043a\u0435\u0430\u043d\u0456\u044f";
            case "region.europe" -> "\u0404\u0432\u0440\u043e\u043f\u0430";
            case "region.latin_america" -> "\u041b\u0430\u0442\u0438\u043d\u0441\u044c\u043a\u0430 \u0410\u043c\u0435\u0440\u0438\u043a\u0430";
            case "region.middle_east" -> "\u0411\u043b\u0438\u0437\u044c\u043a\u0438\u0439 \u0421\u0445\u0456\u0434";
            case "region.north_america" -> "\u041f\u0456\u0432\u043d\u0456\u0447\u043d\u0430 \u0410\u043c\u0435\u0440\u0438\u043a\u0430";
            case "menu.username_placeholder" -> "\u0412\u0430\u0448 \u043d\u0456\u043a\u043d\u0435\u0439\u043c";
            case "menu.create_account" -> "\u0421\u0442\u0432\u043e\u0440\u0438\u0442\u0438 \u0430\u043a\u0430\u0443\u043d\u0442";
            case "menu.created_prefix" -> "\u0421\u0442\u0432\u043e\u0440\u0435\u043d\u043e";
            case "menu.selected_account" -> "\u0412\u0438\u0431\u0440\u0430\u043d\u0438\u0439 \u0430\u043a\u0430\u0443\u043d\u0442";
            case "menu.select_account" -> "\u041e\u0431\u0440\u0430\u0442\u0438 \u0430\u043a\u0430\u0443\u043d\u0442";
            case "menu.select_account_bottom" -> "\u041e\u0431\u0440\u0430\u0442\u0438 \u0430\u043a\u0430\u0443\u043d\u0442";
            case "menu.settings" -> "\u041d\u0430\u043b\u0430\u0448\u0442\u0443\u0432\u0430\u043d\u043d\u044f";
            case "menu.singleplayer" -> "\u041e\u0434\u0438\u043d\u043e\u043a\u043e\u0432\u0430 \u0433\u0440\u0430";
            case "menu.multiplayer" -> "\u041c\u0435\u0440\u0435\u0436\u0435\u0432\u0430 \u0433\u0440\u0430";
            case "menu.quit" -> "\u0412\u0438\u0439\u0442\u0438";
            case "menu.footer_line1" -> "\u0412\u0445\u043e\u0434\u044f\u0447\u0438 \u0443 \u0441\u0432\u0456\u0439 \u0430\u043a\u0430\u0443\u043d\u0442, \u0432\u0438 \u043f\u043e\u0433\u043e\u0434\u0436\u0443\u0454\u0442\u0435\u0441\u044f \u0437 \u0443\u0441\u0456\u043c\u0430 \u043d\u0430\u0448\u0438\u043c\u0438 \u043f\u0440\u0430\u0432\u0438\u043b\u0430\u043c\u0438,";
            case "menu.footer_line2" -> "\u0432\u043a\u043b\u044e\u0447\u043d\u043e \u0437 \u043d\u0430\u0448\u043e\u044e \u041f\u043e\u043b\u0456\u0442\u0438\u043a\u043e\u044e \u043a\u043e\u043d\u0444\u0456\u0434\u0435\u043d\u0446\u0456\u0439\u043d\u043e\u0441\u0442\u0456 \u0442\u0430 \u0423\u043c\u043e\u0432\u0430\u043c\u0438 \u043a\u043e\u0440\u0438\u0441\u0442\u0443\u0432\u0430\u043d\u043d\u044f";
            case "menu.privacy_policy" -> "\u041f\u043e\u043b\u0456\u0442\u0438\u043a\u043e\u044e \u043a\u043e\u043d\u0444\u0456\u0434\u0435\u043d\u0446\u0456\u0439\u043d\u043e\u0441\u0442\u0456";
            case "menu.terms_of_service" -> "\u0423\u043c\u043e\u0432\u0430\u043c\u0438 \u043a\u043e\u0440\u0438\u0441\u0442\u0443\u0432\u0430\u043d\u043d\u044f";
            default -> key;
        };
    }

    private static String polish(String key)
    {
        return switch (key)
        {
            case "language.title" -> "Wybierz jezyk lub region";
            case "language.subtitle" -> "Wybierz, jak celebression ma wygladac dla ciebie.";
            case "language.hint" -> "Wyszukaj lub wybierz jezyk ponizej.";
            case "language.current_selection" -> "Aktualny wybor: %s";
            case "language.search_placeholder" -> "Wyszukaj jezyk lub region";
            case "language.empty_region" -> "W tym regionie nie ma jeszcze jezykow.";
            case "language.no_results_title" -> "Nic nie znaleziono";
            case "language.no_results_subtitle" -> "Sprobuj innego jezyka, kraju lub regionu.";
            case "region.africa" -> "Afryka";
            case "region.americas" -> "Ameryka";
            case "region.asia" -> "Azja";
            case "region.australia_oceania" -> "Australia/Oceania";
            case "region.europe" -> "Europa";
            case "region.latin_america" -> "Ameryka Lacinska";
            case "region.middle_east" -> "Bliski Wschod";
            case "region.north_america" -> "Ameryka Polnocna";
            case "menu.username_placeholder" -> "Twoj nick";
            case "menu.create_account" -> "Utworz konto";
            case "menu.created_prefix" -> "Utworzono";
            case "menu.selected_account" -> "Wybrane konto";
            case "menu.select_account" -> "Wybierz konto";
            case "menu.select_account_bottom" -> "Wybierz konto";
            case "menu.settings" -> "Ustawienia";
            case "menu.singleplayer" -> "Gra jednoosobowa";
            case "menu.multiplayer" -> "Gra sieciowa";
            case "menu.quit" -> "Wyjdz";
            case "menu.footer_line1" -> "Logujac sie na konto, akceptujesz wszystkie nasze zasady,";
            case "menu.footer_line2" -> "w tym nasza Polityke prywatnosci i Warunki korzystania";
            case "menu.privacy_policy" -> "Polityke prywatnosci";
            case "menu.terms_of_service" -> "Warunki korzystania";
            default -> key;
        };
    }

    private static String french(String key)
    {
        return switch (key)
        {
            case "language.title" -> "Choisissez votre langue ou region";
            case "language.subtitle" -> "Choisissez comment celebression doit apparaitre pour vous.";
            case "language.hint" -> "Recherchez ou choisissez une langue ci-dessous.";
            case "language.current_selection" -> "Selection actuelle : %s";
            case "language.search_placeholder" -> "Rechercher une langue ou une region";
            case "language.empty_region" -> "Aucune langue disponible dans cette region pour le moment.";
            case "language.no_results_title" -> "Aucune langue trouvee";
            case "language.no_results_subtitle" -> "Essayez une autre langue, un pays ou une region.";
            case "region.africa" -> "Afrique";
            case "region.americas" -> "Ameriques";
            case "region.asia" -> "Asie";
            case "region.australia_oceania" -> "Australie/Oceanie";
            case "region.europe" -> "Europe";
            case "region.latin_america" -> "Amerique latine";
            case "region.middle_east" -> "Moyen-Orient";
            case "region.north_america" -> "Amerique du Nord";
            case "menu.username_placeholder" -> "Votre pseudo";
            case "menu.create_account" -> "Creer un compte";
            case "menu.created_prefix" -> "Cree";
            case "menu.selected_account" -> "Compte selectionne";
            case "menu.select_account" -> "Selectionner le compte";
            case "menu.select_account_bottom" -> "Selectionner le compte";
            case "menu.settings" -> "Parametres";
            case "menu.singleplayer" -> "Solo";
            case "menu.multiplayer" -> "Multijoueur";
            case "menu.quit" -> "Quitter";
            case "menu.footer_line1" -> "En vous connectant, vous acceptez toutes nos regles,";
            case "menu.footer_line2" -> "y compris notre Politique de confidentialite et nos Conditions d'utilisation";
            case "menu.privacy_policy" -> "Politique de confidentialite";
            case "menu.terms_of_service" -> "Conditions d'utilisation";
            default -> key;
        };
    }

    private static String german(String key)
    {
        return switch (key)
        {
            case "language.title" -> "Sprache oder Region wahlen";
            case "language.subtitle" -> "Wahlen Sie, wie celebression fur Sie aussehen soll.";
            case "language.hint" -> "Suchen oder wahlen Sie unten eine Sprache.";
            case "language.current_selection" -> "Aktuelle Auswahl: %s";
            case "language.search_placeholder" -> "Sprache oder Region suchen";
            case "language.empty_region" -> "In dieser Region gibt es noch keine Sprachen.";
            case "language.no_results_title" -> "Keine Sprachen gefunden";
            case "language.no_results_subtitle" -> "Versuchen Sie eine andere Sprache, ein Land oder eine Region.";
            case "region.africa" -> "Afrika";
            case "region.americas" -> "Amerika";
            case "region.asia" -> "Asien";
            case "region.australia_oceania" -> "Australien/Ozeanien";
            case "region.europe" -> "Europa";
            case "region.latin_america" -> "Lateinamerika";
            case "region.middle_east" -> "Naher Osten";
            case "region.north_america" -> "Nordamerika";
            case "menu.username_placeholder" -> "Dein Benutzername";
            case "menu.create_account" -> "Konto erstellen";
            case "menu.created_prefix" -> "Erstellt";
            case "menu.selected_account" -> "Ausgewahltes Konto";
            case "menu.select_account" -> "Konto auswahlen";
            case "menu.select_account_bottom" -> "Konto auswahlen";
            case "menu.settings" -> "Einstellungen";
            case "menu.singleplayer" -> "Einzelspieler";
            case "menu.multiplayer" -> "Mehrspieler";
            case "menu.quit" -> "Beenden";
            case "menu.footer_line1" -> "Mit der Anmeldung stimmst du allen unseren Richtlinien zu,";
            case "menu.footer_line2" -> "einschliesslich Datenschutz und Nutzungsbedingungen";
            case "menu.privacy_policy" -> "Datenschutz";
            case "menu.terms_of_service" -> "Nutzungsbedingungen";
            default -> key;
        };
    }

    private static String spanish(String key)
    {
        return switch (key)
        {
            case "language.title" -> "Elige tu idioma o region";
            case "language.subtitle" -> "Elige como debe mostrarse celebression para ti.";
            case "language.hint" -> "Busca o elige un idioma abajo.";
            case "language.current_selection" -> "Seleccion actual: %s";
            case "language.search_placeholder" -> "Buscar idioma o region";
            case "language.empty_region" -> "Aun no hay idiomas en esta region.";
            case "language.no_results_title" -> "No se encontraron idiomas";
            case "language.no_results_subtitle" -> "Prueba otro idioma, pais o region.";
            case "region.africa" -> "Africa";
            case "region.americas" -> "America";
            case "region.asia" -> "Asia";
            case "region.australia_oceania" -> "Australia/Oceania";
            case "region.europe" -> "Europa";
            case "region.latin_america" -> "America Latina";
            case "region.middle_east" -> "Oriente Medio";
            case "region.north_america" -> "America del Norte";
            case "menu.username_placeholder" -> "Tu nombre de usuario";
            case "menu.create_account" -> "Crear cuenta";
            case "menu.created_prefix" -> "Creado";
            case "menu.selected_account" -> "Cuenta seleccionada";
            case "menu.select_account" -> "Seleccionar cuenta";
            case "menu.select_account_bottom" -> "Seleccionar cuenta";
            case "menu.settings" -> "Ajustes";
            case "menu.singleplayer" -> "Un jugador";
            case "menu.multiplayer" -> "Multijugador";
            case "menu.quit" -> "Salir";
            case "menu.footer_line1" -> "Al iniciar sesion aceptas todas nuestras politicas,";
            case "menu.footer_line2" -> "incluida la Politica de privacidad y los Terminos de servicio";
            case "menu.privacy_policy" -> "Politica de privacidad";
            case "menu.terms_of_service" -> "Terminos de servicio";
            default -> key;
        };
    }

    private static String portuguese(String key)
    {
        return switch (key)
        {
            case "language.title" -> "Escolha seu idioma ou regiao";
            case "language.subtitle" -> "Escolha como o celebression deve aparecer para voce.";
            case "language.hint" -> "Pesquise ou escolha um idioma abaixo.";
            case "language.current_selection" -> "Selecao atual: %s";
            case "language.search_placeholder" -> "Pesquisar idioma ou regiao";
            case "language.empty_region" -> "Ainda nao ha idiomas nesta regiao.";
            case "language.no_results_title" -> "Nenhum idioma encontrado";
            case "language.no_results_subtitle" -> "Tente outro idioma, pais ou regiao.";
            case "region.africa" -> "Africa";
            case "region.americas" -> "Americas";
            case "region.asia" -> "Asia";
            case "region.australia_oceania" -> "Australia/Oceania";
            case "region.europe" -> "Europa";
            case "region.latin_america" -> "America Latina";
            case "region.middle_east" -> "Oriente Medio";
            case "region.north_america" -> "America do Norte";
            case "menu.username_placeholder" -> "Seu nome de usuario";
            case "menu.create_account" -> "Criar conta";
            case "menu.created_prefix" -> "Criado";
            case "menu.selected_account" -> "Conta selecionada";
            case "menu.select_account" -> "Selecionar conta";
            case "menu.select_account_bottom" -> "Selecionar conta";
            case "menu.settings" -> "Configuracoes";
            case "menu.singleplayer" -> "Um jogador";
            case "menu.multiplayer" -> "Multijogador";
            case "menu.quit" -> "Sair";
            case "menu.footer_line1" -> "Ao entrar na conta voce concorda com todas as nossas politicas,";
            case "menu.footer_line2" -> "incluindo Politica de Privacidade e Termos de Servico";
            case "menu.privacy_policy" -> "Politica de Privacidade";
            case "menu.terms_of_service" -> "Termos de Servico";
            default -> key;
        };
    }

    private static String dutch(String key)
    {
        return switch (key)
        {
            case "language.title" -> "Kies je taal of regio";
            case "language.subtitle" -> "Kies hoe celebression voor jou moet verschijnen.";
            case "language.hint" -> "Zoek of kies hieronder een taal.";
            case "language.current_selection" -> "Huidige selectie: %s";
            case "language.search_placeholder" -> "Zoek taal of regio";
            case "language.empty_region" -> "Er zijn nog geen talen in deze regio.";
            case "language.no_results_title" -> "Geen talen gevonden";
            case "language.no_results_subtitle" -> "Probeer een andere taal, land of regio.";
            case "region.africa" -> "Afrika";
            case "region.americas" -> "Amerika";
            case "region.asia" -> "Azie";
            case "region.australia_oceania" -> "Australie/Oceanie";
            case "region.europe" -> "Europa";
            case "region.latin_america" -> "Latijns-Amerika";
            case "region.middle_east" -> "Midden-Oosten";
            case "region.north_america" -> "Noord-Amerika";
            case "menu.username_placeholder" -> "Je gebruikersnaam";
            case "menu.create_account" -> "Account maken";
            case "menu.created_prefix" -> "Gemaakt";
            case "menu.selected_account" -> "Geselecteerd account";
            case "menu.select_account" -> "Account selecteren";
            case "menu.select_account_bottom" -> "Account selecteren";
            case "menu.settings" -> "Instellingen";
            case "menu.singleplayer" -> "Singleplayer";
            case "menu.multiplayer" -> "Multiplayer";
            case "menu.quit" -> "Afsluiten";
            case "menu.footer_line1" -> "Door in te loggen ga je akkoord met al onze regels,";
            case "menu.footer_line2" -> "inclusief Privacybeleid en Gebruiksvoorwaarden";
            case "menu.privacy_policy" -> "Privacybeleid";
            case "menu.terms_of_service" -> "Gebruiksvoorwaarden";
            default -> key;
        };
    }

    private static String italian(String key)
    {
        return switch (key)
        {
            case "language.title" -> "Scegli lingua o regione";
            case "language.subtitle" -> "Scegli come celebression deve apparire per te.";
            case "language.hint" -> "Cerca o scegli una lingua qui sotto.";
            case "language.current_selection" -> "Selezione attuale: %s";
            case "language.search_placeholder" -> "Cerca lingua o regione";
            case "language.empty_region" -> "Non ci sono ancora lingue in questa regione.";
            case "language.no_results_title" -> "Nessuna lingua trovata";
            case "language.no_results_subtitle" -> "Prova un'altra lingua, paese o regione.";
            case "region.africa" -> "Africa";
            case "region.americas" -> "Americhe";
            case "region.asia" -> "Asia";
            case "region.australia_oceania" -> "Australia/Oceania";
            case "region.europe" -> "Europa";
            case "region.latin_america" -> "America Latina";
            case "region.middle_east" -> "Medio Oriente";
            case "region.north_america" -> "Nord America";
            case "menu.username_placeholder" -> "Il tuo username";
            case "menu.create_account" -> "Crea account";
            case "menu.created_prefix" -> "Creato";
            case "menu.selected_account" -> "Account selezionato";
            case "menu.select_account" -> "Seleziona account";
            case "menu.select_account_bottom" -> "Seleziona account";
            case "menu.settings" -> "Impostazioni";
            case "menu.singleplayer" -> "Giocatore singolo";
            case "menu.multiplayer" -> "Multigiocatore";
            case "menu.quit" -> "Esci";
            case "menu.footer_line1" -> "Accedendo accetti tutte le nostre policy,";
            case "menu.footer_line2" -> "inclusa Privacy Policy e Termini di servizio";
            case "menu.privacy_policy" -> "Privacy Policy";
            case "menu.terms_of_service" -> "Termini di servizio";
            default -> key;
        };
    }

    private static String turkish(String key)
    {
        return switch (key)
        {
            case "language.title" -> "Dil veya bolge secin";
            case "language.subtitle" -> "celebression'in sizin icin nasil gorunecegini secin.";
            case "language.hint" -> "Asagidan dil arayin veya secin.";
            case "language.current_selection" -> "Gecerli secim: %s";
            case "language.search_placeholder" -> "Dil veya bolge ara";
            case "language.empty_region" -> "Bu bolgede henuz dil yok.";
            case "language.no_results_title" -> "Dil bulunamadi";
            case "language.no_results_subtitle" -> "Baska bir dil, ulke veya bolge deneyin.";
            case "region.africa" -> "Afrika";
            case "region.americas" -> "Amerika";
            case "region.asia" -> "Asya";
            case "region.australia_oceania" -> "Avustralya/Okyanusya";
            case "region.europe" -> "Avrupa";
            case "region.latin_america" -> "Latin Amerika";
            case "region.middle_east" -> "Orta Dogu";
            case "region.north_america" -> "Kuzey Amerika";
            case "menu.username_placeholder" -> "Kullanici adin";
            case "menu.create_account" -> "Hesap olustur";
            case "menu.created_prefix" -> "Olusturuldu";
            case "menu.selected_account" -> "Secili hesap";
            case "menu.select_account" -> "Hesap sec";
            case "menu.select_account_bottom" -> "Hesap sec";
            case "menu.settings" -> "Ayarlar";
            case "menu.singleplayer" -> "Tek oyunculu";
            case "menu.multiplayer" -> "Cok oyunculu";
            case "menu.quit" -> "Cik";
            case "menu.footer_line1" -> "Giris yaparak tum kurallarimizi kabul edersin,";
            case "menu.footer_line2" -> "Gizlilik Politikasi ve Kullanim Sartlari dahil";
            case "menu.privacy_policy" -> "Gizlilik Politikasi";
            case "menu.terms_of_service" -> "Kullanim Sartlari";
            default -> key;
        };
    }

    private static String hindi(String key)
    {
        return switch (key)
        {
            case "language.title" -> "\u0905\u092a\u0928\u0940 \u092d\u093e\u0937\u093e \u092f\u093e \u0915\u094d\u0937\u0947\u0924\u094d\u0930 \u091a\u0941\u0928\u0947\u0902";
            case "language.subtitle" -> "\u091a\u0941\u0928\u0947\u0902 \u0915\u093f celebression \u0906\u092a\u0915\u0947 \u0932\u093f\u090f \u0915\u0948\u0938\u093e \u0926\u093f\u0916\u0947\u0964";
            case "language.hint" -> "\u0928\u0940\u091a\u0947 \u0916\u094b\u091c\u0947\u0902 \u092f\u093e \u092d\u093e\u0937\u093e \u091a\u0941\u0928\u0947\u0902\u0964";
            case "language.current_selection" -> "\u0935\u0930\u094d\u0924\u092e\u093e\u0928 \u091a\u092f\u0928: %s";
            case "language.search_placeholder" -> "\u092d\u093e\u0937\u093e \u092f\u093e \u0915\u094d\u0937\u0947\u0924\u094d\u0930 \u0916\u094b\u091c\u0947\u0902";
            case "language.empty_region" -> "\u0907\u0938 \u0915\u094d\u0937\u0947\u0924\u094d\u0930 \u092e\u0947\u0902 \u0905\u092d\u0940 \u0915\u094b\u0908 \u092d\u093e\u0937\u093e \u0928\u0939\u0940\u0902 \u0939\u0948\u0964";
            case "language.no_results_title" -> "\u0915\u094b\u0908 \u092d\u093e\u0937\u093e \u0928\u0939\u0940\u0902 \u092e\u093f\u0932\u0940";
            case "language.no_results_subtitle" -> "\u0915\u094b\u0908 \u0905\u0928\u094d\u092f \u092d\u093e\u0937\u093e, \u0926\u0947\u0936 \u092f\u093e \u0915\u094d\u0937\u0947\u0924\u094d\u0930 \u0906\u091c\u093c\u092e\u093e\u090f\u0902\u0964";
            case "region.africa" -> "\u0905\u092b\u094d\u0930\u0940\u0915\u093e";
            case "region.americas" -> "\u0905\u092e\u0947\u0930\u093f\u0915\u093e";
            case "region.asia" -> "\u090f\u0936\u093f\u092f\u093e";
            case "region.australia_oceania" -> "\u0911\u0938\u094d\u091f\u094d\u0930\u0947\u0932\u093f\u092f\u093e/\u092e\u0939\u093e\u0938\u093e\u0917\u0930";
            case "region.europe" -> "\u092f\u0942\u0930\u094b\u092a";
            case "region.latin_america" -> "\u0932\u0948\u091f\u093f\u0928 \u0905\u092e\u0947\u0930\u093f\u0915\u093e";
            case "region.middle_east" -> "\u092e\u0927\u094d\u092f \u092a\u0942\u0930\u094d\u0935";
            case "region.north_america" -> "\u0909\u0924\u094d\u0924\u0930 \u0905\u092e\u0947\u0930\u093f\u0915\u093e";
            case "menu.username_placeholder" -> "\u0906\u092a\u0915\u093e \u0909\u092a\u092f\u094b\u0917\u0915\u0930\u094d\u0924\u093e \u0928\u093e\u092e";
            case "menu.create_account" -> "\u0916\u093e\u0924\u093e \u092c\u0928\u093e\u090f\u0902";
            case "menu.created_prefix" -> "\u092c\u0928\u093e\u092f\u093e \u0917\u092f\u093e";
            case "menu.selected_account" -> "\u091a\u092f\u0928\u093f\u0924 \u0916\u093e\u0924\u093e";
            case "menu.select_account" -> "\u0916\u093e\u0924\u093e \u091a\u0941\u0928\u0947\u0902";
            case "menu.select_account_bottom" -> "\u0916\u093e\u0924\u093e \u091a\u0941\u0928\u0947\u0902";
            case "menu.settings" -> "\u0938\u0947\u091f\u093f\u0902\u0917\u094d\u0938";
            case "menu.singleplayer" -> "\u090f\u0915\u0932\u094c\u0930\u094d\u0924\u0940";
            case "menu.multiplayer" -> "\u092e\u0932\u094d\u091f\u0940\u092a\u094d\u0932\u0947\u092f\u0930";
            case "menu.quit" -> "\u092c\u093e\u0939\u0930";
            case "menu.footer_line1" -> "\u0932\u0949\u0917 \u0907\u0928 \u0915\u0930\u0915\u0947 \u0906\u092a \u0939\u092e\u093e\u0930\u0940 \u0938\u092d\u0940 \u0928\u0940\u0924\u093f\u092f\u094b\u0902 \u0938\u0947 \u0938\u0939\u092e\u0924 \u0939\u0948\u0902,";
            case "menu.footer_line2" -> "\u0917\u094b\u092a\u0928\u0940\u092f\u0924\u093e \u0928\u0940\u0924\u093f \u0914\u0930 \u0938\u0947\u0935\u093e \u0915\u0940 \u0936\u0930\u094d\u0924\u0947\u0902 \u0938\u0939\u093f\u0924";
            case "menu.privacy_policy" -> "\u0917\u094b\u092a\u0928\u0940\u092f\u0924\u093e \u0928\u0940\u0924\u093f";
            case "menu.terms_of_service" -> "\u0938\u0947\u0935\u093e \u0915\u0940 \u0936\u0930\u094d\u0924\u0947\u0902";
            default -> key;
        };
    }

    private static String indonesian(String key)
    {
        return switch (key)
        {
            case "language.title" -> "Pilih bahasa atau wilayah";
            case "language.subtitle" -> "Pilih tampilan celebression untuk Anda.";
            case "language.hint" -> "Cari atau pilih bahasa di bawah.";
            case "language.current_selection" -> "Pilihan saat ini: %s";
            case "language.search_placeholder" -> "Cari bahasa atau wilayah";
            case "language.empty_region" -> "Belum ada bahasa di wilayah ini.";
            case "language.no_results_title" -> "Bahasa tidak ditemukan";
            case "language.no_results_subtitle" -> "Coba bahasa, negara, atau wilayah lain.";
            case "region.africa" -> "Afrika";
            case "region.americas" -> "Amerika";
            case "region.asia" -> "Asia";
            case "region.australia_oceania" -> "Australia/Oceania";
            case "region.europe" -> "Eropa";
            case "region.latin_america" -> "Amerika Latin";
            case "region.middle_east" -> "Timur Tengah";
            case "region.north_america" -> "Amerika Utara";
            case "menu.username_placeholder" -> "Nama pengguna";
            case "menu.create_account" -> "Buat akun";
            case "menu.created_prefix" -> "Dibuat";
            case "menu.selected_account" -> "Akun terpilih";
            case "menu.select_account" -> "Pilih akun";
            case "menu.select_account_bottom" -> "Pilih akun";
            case "menu.settings" -> "Pengaturan";
            case "menu.singleplayer" -> "Bermain sendiri";
            case "menu.multiplayer" -> "Multipemain";
            case "menu.quit" -> "Keluar";
            case "menu.footer_line1" -> "Dengan masuk, Anda menyetujui semua kebijakan kami,";
            case "menu.footer_line2" -> "termasuk Kebijakan Privasi dan Ketentuan Layanan";
            case "menu.privacy_policy" -> "Kebijakan Privasi";
            case "menu.terms_of_service" -> "Ketentuan Layanan";
            default -> key;
        };
    }

    private static String vietnamese(String key)
    {
        return switch (key)
        {
            case "language.title" -> "Chon ngon ngu hoac khu vuc";
            case "language.subtitle" -> "Chon cach celebression hien thi cho ban.";
            case "language.hint" -> "Tim hoac chon ngon ngu ben duoi.";
            case "language.current_selection" -> "Lua chon hien tai: %s";
            case "language.search_placeholder" -> "Tim ngon ngu hoac khu vuc";
            case "language.empty_region" -> "Chua co ngon ngu nao trong khu vuc nay.";
            case "language.no_results_title" -> "Khong tim thay ngon ngu";
            case "language.no_results_subtitle" -> "Thu ngon ngu, quoc gia hoac khu vuc khac.";
            case "region.africa" -> "Chau Phi";
            case "region.americas" -> "Chau My";
            case "region.asia" -> "Chau A";
            case "region.australia_oceania" -> "Chau Uc/Dai Duong";
            case "region.europe" -> "Chau Au";
            case "region.latin_america" -> "My Latin";
            case "region.middle_east" -> "Trung Dong";
            case "region.north_america" -> "Bac My";
            case "menu.username_placeholder" -> "Ten nguoi dung";
            case "menu.create_account" -> "Tao tai khoan";
            case "menu.created_prefix" -> "Da tao";
            case "menu.selected_account" -> "Tai khoan da chon";
            case "menu.select_account" -> "Chon tai khoan";
            case "menu.select_account_bottom" -> "Chon tai khoan";
            case "menu.settings" -> "Cai dat";
            case "menu.singleplayer" -> "Mot nguoi choi";
            case "menu.multiplayer" -> "Nhieu nguoi choi";
            case "menu.quit" -> "Thoat";
            case "menu.footer_line1" -> "Dang nhap dong nghia voi viec ban dong y tat ca chinh sach,";
            case "menu.footer_line2" -> "bao gom Chinh sach quyen rieng tu va Dieu khoan dich vu";
            case "menu.privacy_policy" -> "Chinh sach quyen rieng tu";
            case "menu.terms_of_service" -> "Dieu khoan dich vu";
            default -> key;
        };
    }

    private static String thai(String key)
    {
        return switch (key)
        {
            case "language.title" -> "\u0e40\u0e25\u0e37\u0e2d\u0e01\u0e20\u0e32\u0e29\u0e32\u0e2b\u0e23\u0e37\u0e2d\u0e1b\u0e23\u0e30\u0e40\u0e17\u0e28";
            case "language.subtitle" -> "\u0e40\u0e25\u0e37\u0e2d\u0e01\u0e27\u0e34\u0e18\u0e35\u0e01\u0e32\u0e23\u0e41\u0e2a\u0e14\u0e07\u0e1c\u0e25 celebression \u0e2a\u0e33\u0e2b\u0e23\u0e31\u0e1a\u0e04\u0e38\u0e13";
            case "language.hint" -> "\u0e04\u0e49\u0e19\u0e2b\u0e32\u0e2b\u0e23\u0e37\u0e2d\u0e40\u0e25\u0e37\u0e2d\u0e01\u0e20\u0e32\u0e29\u0e32\u0e14\u0e49\u0e32\u0e19\u0e25\u0e48\u0e32\u0e07";
            case "language.current_selection" -> "\u0e01\u0e32\u0e23\u0e40\u0e25\u0e37\u0e2d\u0e01\u0e1b\u0e31\u0e08\u0e08\u0e38\u0e1a\u0e31\u0e19: %s";
            case "language.search_placeholder" -> "\u0e04\u0e49\u0e19\u0e2b\u0e32\u0e20\u0e32\u0e29\u0e32\u0e2b\u0e23\u0e37\u0e2d\u0e1b\u0e23\u0e30\u0e40\u0e17\u0e28";
            case "language.empty_region" -> "\u0e22\u0e31\u0e07\u0e44\u0e21\u0e48\u0e21\u0e35\u0e20\u0e32\u0e29\u0e32\u0e43\u0e19\u0e1b\u0e23\u0e30\u0e40\u0e17\u0e28\u0e19\u0e35\u0e49";
            case "language.no_results_title" -> "\u0e44\u0e21\u0e48\u0e1e\u0e1a\u0e20\u0e32\u0e29\u0e32";
            case "language.no_results_subtitle" -> "\u0e25\u0e2d\u0e01\u0e43\u0e0a\u0e49\u0e20\u0e32\u0e29\u0e32 \u0e1b\u0e23\u0e30\u0e40\u0e17\u0e28 \u0e2b\u0e23\u0e37\u0e2d\u0e1b\u0e23\u0e30\u0e40\u0e17\u0e28\u0e2d\u0e37\u0e48\u0e19";
            case "region.africa" -> "\u0e41\u0e2d\u0e1f\u0e23\u0e34\u0e01\u0e32";
            case "region.americas" -> "\u0e2d\u0e40\u0e21\u0e23\u0e34\u0e01\u0e32";
            case "region.asia" -> "\u0e40\u0e2d\u0e40\u0e0b\u0e35\u0e22";
            case "region.australia_oceania" -> "\u0e2d\u0e2d\u0e2a\u0e40\u0e15\u0e23\u0e40\u0e25\u0e35\u0e22/\u0e21\u0e2b\u0e32\u0e2a\u0e21\u0e38\u0e17\u0e23";
            case "region.europe" -> "\u0e22\u0e38\u0e42\u0e23\u0e1b";
            case "region.latin_america" -> "\u0e25\u0e32\u0e15\u0e34\u0e19\u0e2d\u0e40\u0e21\u0e23\u0e34\u0e01\u0e32";
            case "region.middle_east" -> "\u0e15\u0e30\u0e27\u0e31\u0e15\u0e30\u0e2d\u0e40\u0e27\u0e25\u0e48\u0e2d";
            case "region.north_america" -> "\u0e2d\u0e40\u0e21\u0e23\u0e34\u0e01\u0e32\u0e40\u0e2b\u0e19\u0e37\u0e2d";
            case "menu.username_placeholder" -> "\u0e0a\u0e37\u0e48\u0e2d\u0e1c\u0e39\u0e49\u0e43\u0e0a\u0e49";
            case "menu.create_account" -> "\u0e2a\u0e23\u0e49\u0e32\u0e07\u0e1a\u0e31\u0e0d\u0e0a\u0e35";
            case "menu.created_prefix" -> "\u0e2a\u0e23\u0e49\u0e32\u0e07\u0e41\u0e25\u0e49\u0e27";
            case "menu.selected_account" -> "\u0e1a\u0e31\u0e0d\u0e0a\u0e35\u0e17\u0e35\u0e48\u0e40\u0e25\u0e37\u0e2d\u0e01";
            case "menu.select_account" -> "\u0e40\u0e25\u0e37\u0e2d\u0e01\u0e1a\u0e31\u0e0d\u0e0a\u0e35";
            case "menu.select_account_bottom" -> "\u0e40\u0e25\u0e37\u0e2d\u0e01\u0e1a\u0e31\u0e0d\u0e0a\u0e35";
            case "menu.settings" -> "\u0e01\u0e32\u0e23\u0e15\u0e31\u0e49\u0e07\u0e04\u0e48\u0e32";
            case "menu.singleplayer" -> "\u0e40\u0e25\u0e48\u0e19\u0e04\u0e40\u0e14\u0e35\u0e22\u0e27";
            case "menu.multiplayer" -> "\u0e40\u0e25\u0e48\u0e19\u0e2b\u0e25\u0e31\u0e22";
            case "menu.quit" -> "\u0e2d\u0e2d\u0e01";
            case "menu.footer_line1" -> "\u0e01\u0e32\u0e23\u0e40\u0e02\u0e49\u0e32\u0e2a\u0e39\u0e48\u0e23\u0e30\u0e1a\u0e1a\u0e2b\u0e21\u0e32\u0e22\u0e16\u0e36\u0e07\u0e04\u0e38\u0e13\u0e15\u0e49\u0e2d\u0e07\u0e42\u0e2b\u0e21\u0e19\u0e49\u0e32\u0e04\u0e27\u0e32\u0e21\u0e02\u0e2d\u0e07\u0e40\u0e23\u0e32";
            case "menu.footer_line2" -> "\u0e23\u0e27\u0e21\u0e16\u0e36\u0e07\u0e19\u0e42\u0e22\u0e1a\u0e32\u0e22\u0e35\u0e48\u0e2a\u0e48\u0e27\u0e19\u0e15\u0e31\u0e27\u0e41\u0e25\u0e30\u0e02\u0e49\u0e2d\u0e01\u0e33\u0e2b\u0e19\u0e14\u0e01\u0e32\u0e23\u0e43\u0e0a\u0e49\u0e07\u0e32\u0e19";
            case "menu.privacy_policy" -> "\u0e19\u0e42\u0e22\u0e1a\u0e32\u0e22\u0e35\u0e48\u0e2a\u0e48\u0e27\u0e19\u0e15\u0e31\u0e27";
            case "menu.terms_of_service" -> "\u0e02\u0e49\u0e2d\u0e01\u0e33\u0e2b\u0e19\u0e14\u0e01\u0e32\u0e23\u0e43\u0e0a\u0e49\u0e07\u0e32\u0e19";
            default -> key;
        };
    }

    private static String arabic(String key)
    {
        return switch (key)
        {
            case "language.title" -> "\u0627\u062e\u062a\u0631 \u0644\u063a\u062a\u0643 \u0623\u0648 \u0645\u0646\u0637\u0642\u062a\u0643";
            case "language.subtitle" -> "\u0627\u062e\u062a\u0631 \u0643\u064a\u0641\u064a\u0629 \u0638\u0647\u0648\u0631 celebression \u0644\u0643.";
            case "language.hint" -> "\u0627\u0628\u062d\u062b \u0623\u0648 \u0627\u062e\u062a\u0631 \u0644\u063a\u0629 \u0623\u062f\u0646\u0627\u0647.";
            case "language.current_selection" -> "\u0627\u0644\u0627\u062e\u062a\u064a\u0627\u0631 \u0627\u0644\u062d\u0627\u0644\u064a: %s";
            case "language.search_placeholder" -> "\u0627\u0628\u062d\u062b \u0639\u0646 \u0644\u063a\u0629 \u0623\u0648 \u0645\u0646\u0637\u0642\u0629";
            case "language.empty_region" -> "\u0644\u0627 \u062a\u0648\u062c\u062f \u0644\u063a\u0627\u062a \u0641\u064a \u0647\u0630\u0647 \u0627\u0644\u0645\u0646\u0637\u0642\u0629 \u0628\u0639\u062f.";
            case "language.no_results_title" -> "\u0644\u0645 \u064a\u062a\u0645 \u0627\u0644\u0639\u062b\u0648\u0631 \u0639\u0644\u0649 \u0644\u063a\u0627\u062a";
            case "language.no_results_subtitle" -> "\u062c\u0631\u0651\u0628 \u0644\u063a\u0629 \u0623\u0648 \u0628\u0644\u062f \u0623\u0648 \u0645\u0646\u0637\u0642\u0629 \u0623\u062e\u0631\u0649.";
            case "region.africa" -> "\u0623\u0641\u0631\u064a\u0642\u064a\u0627";
            case "region.americas" -> "\u0627\u0644\u0623\u0645\u0631\u064a\u0643\u062a\u0627\u0646";
            case "region.asia" -> "\u0627\u0644\u0622\u0633\u064a\u0627";
            case "region.australia_oceania" -> "\u0623\u0633\u062a\u0631\u0627\u0644\u064a\u0627/\u0623\u0648\u0642\u064a\u0627\u0646\u0648\u0633\u064a\u0627";
            case "region.europe" -> "\u0623\u0648\u0631\u0648\u0628\u0627";
            case "region.latin_america" -> "\u0623\u0645\u0631\u064a\u0643\u0627 \u0627\u0644\u0644\u0627\u062a\u064a\u0646\u064a\u0629";
            case "region.middle_east" -> "\u0627\u0644\u0634\u0631\u0642 \u0627\u0644\u0623\u0648\u0633\u0637";
            case "region.north_america" -> "\u0623\u0645\u0631\u064a\u0643\u0627 \u0627\u0644\u0634\u0645\u0627\u0644\u064a\u0629";
            case "menu.username_placeholder" -> "\u0627\u0633\u0645 \u0627\u0644\u0645\u0633\u062a\u062e\u062f\u0645";
            case "menu.create_account" -> "\u0625\u0646\u0634\u0627\u0621 \u062d\u0633\u0627\u0628";
            case "menu.created_prefix" -> "\u062a\u0645 \u0627\u0644\u0625\u0646\u0634\u0627\u0621";
            case "menu.selected_account" -> "\u0627\u0644\u062d\u0633\u0627\u0628 \u0627\u0644\u0645\u062e\u062a\u0627\u0631";
            case "menu.select_account" -> "\u0627\u062e\u062a\u0631 \u062d\u0633\u0627\u0628\u0627";
            case "menu.select_account_bottom" -> "\u0627\u062e\u062a\u0631 \u062d\u0633\u0627\u0628\u0627";
            case "menu.settings" -> "\u0627\u0644\u0625\u0639\u062f\u0627\u062f\u0627\u062a";
            case "menu.singleplayer" -> "\u0644\u0627\u0639\u0628 \u0641\u0631\u062f\u064a";
            case "menu.multiplayer" -> "\u0644\u0627\u0639\u0628 \u062c\u0645\u0627\u0639\u064a";
            case "menu.quit" -> "\u062e\u0631\u0648\u062c";
            case "menu.footer_line1" -> "\u0628\u062a\u0633\u062c\u064a\u0644 \u0627\u0644\u062f\u062e\u0648\u0644\u060c \u0641\u0625\u0646\u0643 \u062a\u0648\u0627\u0641\u0642 \u0639\u0644\u0649 \u062c\u0645\u064a\u0639 \u0633\u064a\u0627\u0633\u0627\u062a\u0646\u0627\u060c";
            case "menu.footer_line2" -> "\u0628\u0645\u0627 \u0641\u064a \u0630\u0644\u0643 \u0633\u064a\u0627\u0633\u0629 \u0627\u0644\u062e\u0635\u0648\u0635\u064a\u0629 \u0648\u0634\u0631\u0648\u0637 \u0627\u0644\u062e\u062f\u0645\u0629";
            case "menu.privacy_policy" -> "\u0633\u064a\u0627\u0633\u0629 \u0627\u0644\u062e\u0635\u0648\u0635\u064a\u0629";
            case "menu.terms_of_service" -> "\u0634\u0631\u0648\u0637 \u0627\u0644\u062e\u062f\u0645\u0629";
            default -> key;
        };
    }

    private static String hebrew(String key)
    {
        return switch (key)
        {
            case "language.title" -> "\u05d1\u05d7\u05e8\u05d5 \u05e9\u05e4\u05d4 \u05d0\u05d5 \u05d0\u05d6\u05d5\u05e8";
            case "language.subtitle" -> "\u05d1\u05d7\u05e8\u05d5 \u05d0\u05d9\u05da celebression \u05d9\u05d5\u05e4\u05d9\u05e2 \u05e2\u05d1\u05d5\u05e8\u05da.";
            case "language.hint" -> "\u05d7\u05e4\u05e9 \u05d0\u05d5 \u05d1\u05d7\u05e8 \u05e9\u05e4\u05d4 \u05dc\u05de\u05d8\u05d4.";
            case "language.current_selection" -> "\u05d4\u05d1\u05d7\u05d9\u05e8\u05d4 \u05e0\u05d5\u05db\u05d7\u05d9\u05ea: %s";
            case "language.search_placeholder" -> "\u05d7\u05e4\u05e9 \u05e9\u05e4\u05d4 \u05d0\u05d5 \u05d0\u05d6\u05d5\u05e8";
            case "language.empty_region" -> "\u05d0\u05d9\u05df \u05e2\u05d3\u05d9\u05d9\u05df \u05e9\u05e4\u05d5\u05ea \u05d1\u05d0\u05d6\u05d5\u05e8 \u05d6\u05d4.";
            case "language.no_results_title" -> "\u05dc\u05d0 \u05e0\u05de\u05e6\u05d0\u05d5 \u05e9\u05e4\u05d5\u05ea";
            case "language.no_results_subtitle" -> "\u05e0\u05e1\u05d4 \u05e9\u05e4\u05d4, \u05de\u05d3\u05d9\u05e0\u05d4 \u05d0\u05d5 \u05d0\u05d6\u05d5\u05e8 \u05d0\u05d7\u05e8.";
            case "region.africa" -> "\u05d0\u05e4\u05e8\u05d9\u05e7\u05d4";
            case "region.americas" -> "\u05d0\u05de\u05e8\u05d9\u05e7\u05d4";
            case "region.asia" -> "\u05d0\u05e1\u05d9\u05d4";
            case "region.australia_oceania" -> "\u05d0\u05d5\u05e1\u05d8\u05e8\u05dc\u05d9\u05d4/\u05d0\u05d5\u05e7\u05d9\u05e0\u05d9\u05d4";
            case "region.europe" -> "\u05d0\u05d9\u05e8\u05d5\u05e4\u05d4";
            case "region.latin_america" -> "\u05d0\u05de\u05e8\u05d9\u05e7\u05d4 \u05d4\u05dc\u05d0\u05d8\u05d9\u05e0\u05d9\u05ea";
            case "region.middle_east" -> "\u05d4\u05de\u05d6\u05e8\u05d7 \u05d4\u05ea\u05d9\u05db\u05d5\u05df";
            case "region.north_america" -> "\u05e6\u05e4\u05d5\u05df \u05d0\u05de\u05e8\u05d9\u05e7\u05d4";
            case "menu.username_placeholder" -> "\u05e9\u05dd \u05d4\u05de\u05e9\u05ea\u05de\u05e9";
            case "menu.create_account" -> "\u05e6\u05d5\u05e8 \u05d7\u05e9\u05d1\u05d5\u05df";
            case "menu.created_prefix" -> "\u05e0\u05d5\u05e6\u05e8";
            case "menu.selected_account" -> "\u05d7\u05e9\u05d1\u05d5\u05df \u05e0\u05d1\u05d7\u05e8";
            case "menu.select_account" -> "\u05d1\u05d7\u05e8 \u05d7\u05e9\u05d1\u05d5\u05df";
            case "menu.select_account_bottom" -> "\u05d1\u05d7\u05e8 \u05d7\u05e9\u05d1\u05d5\u05df";
            case "menu.settings" -> "\u05d4\u05d2\u05d3\u05e8\u05d5\u05ea";
            case "menu.singleplayer" -> "\u05e9\u05d7\u05e7 \u05e2\u05e6\u05de\u05d9";
            case "menu.multiplayer" -> "\u05e8\u05d1 \u05de\u05e9\u05ea\u05ea\u05e4\u05d9\u05dd";
            case "menu.quit" -> "\u05d9\u05e6\u05d9\u05d0\u05d4";
            case "menu.footer_line1" -> "\u05d1\u05db\u05e0\u05e1\u05ea\u05da \u05dc\u05d7\u05e9\u05d1\u05d5\u05df \u05d0\u05ea\u05d4 \u05de\u05e1\u05db\u05d9\u05dd \u05dc\u05db\u05dc \u05d4\u05de\u05d3\u05d9\u05e0\u05d9\u05d5\u05ea \u05e9\u05dc\u05e0\u05d5,";
            case "menu.footer_line2" -> "\u05db\u05d5\u05dc\u05dc \u05de\u05d3\u05d9\u05e0\u05d9\u05d5\u05ea \u05d4\u05e4\u05e8\u05d8\u05d9\u05d5\u05ea \u05d5\u05ea\u05e0\u05d0\u05d9 \u05d4\u05e9\u05d9\u05e8\u05d5\u05ea";
            case "menu.privacy_policy" -> "\u05de\u05d3\u05d9\u05e0\u05d9\u05d5\u05ea \u05d4\u05e4\u05e8\u05d8\u05d9\u05d5\u05ea";
            case "menu.terms_of_service" -> "\u05ea\u05e0\u05d0\u05d9 \u05d4\u05e9\u05d9\u05e8\u05d5\u05ea";
            default -> key;
        };
    }

    private static String japanese(String key)
    {
        return switch (key)
        {
            case "language.title" -> "\u8a00\u8a9e\u307e\u305f\u306f\u5730\u57df\u3092\u9078\u629e";
            case "language.subtitle" -> "celebression\u306e\u8868\u793a\u65b9\u6cd5\u3092\u9078\u629e\u3057\u3066\u304f\u3060\u3055\u3044\u3002";
            case "language.hint" -> "\u4e0b\u3067\u8a00\u8a9e\u3092\u691c\u7d22\u307e\u305f\u306f\u9078\u629e\u3057\u307e\u3059\u3002";
            case "language.current_selection" -> "\u73fe\u5728\u306e\u9078\u629e: %s";
            case "language.search_placeholder" -> "\u8a00\u8a9e\u307e\u305f\u306f\u5730\u57df\u3092\u691c\u7d22";
            case "language.empty_region" -> "\u3053\u306e\u5730\u57df\u306b\u306f\u307e\u3060\u8a00\u8a9e\u304c\u3042\u308a\u307e\u305b\u3093\u3002";
            case "language.no_results_title" -> "\u8a00\u8a9e\u304c\u898b\u3064\u304b\u308a\u307e\u305b\u3093";
            case "language.no_results_subtitle" -> "\u5225\u306e\u8a00\u8a9e\u3001\u56fd\u3001\u5730\u57df\u3092\u8a66\u3057\u3066\u304f\u3060\u3055\u3044\u3002";
            case "region.africa" -> "\u30a2\u30d5\u30ea\u30ab";
            case "region.americas" -> "\u30a2\u30e1\u30ea\u30ab";
            case "region.asia" -> "\u30a2\u30b8\u30a2";
            case "region.australia_oceania" -> "\u30aa\u30bb\u30a2\u30cb\u30a2";
            case "region.europe" -> "\u30e8\u30fc\u30ed\u30c3\u30d1";
            case "region.latin_america" -> "\u30e9\u30c6\u30f3\u30a2\u30e1\u30ea\u30ab";
            case "region.middle_east" -> "\u4e2d\u6771";
            case "region.north_america" -> "\u5317\u30a2\u30e1\u30ea\u30ab";
            case "menu.username_placeholder" -> "\u30e6\u30fc\u30b6\u30fc\u540d";
            case "menu.create_account" -> "\u30a2\u30ab\u30a6\u30f3\u30c8\u4f5c\u6210";
            case "menu.created_prefix" -> "\u4f5c\u6210\u6e08\u307f";
            case "menu.selected_account" -> "\u9078\u629e\u4e2d\u306e\u30a2\u30ab\u30a6\u30f3\u30c8";
            case "menu.select_account" -> "\u30a2\u30ab\u30a6\u30f3\u30c8\u9078\u629e";
            case "menu.select_account_bottom" -> "\u30a2\u30ab\u30a6\u30f3\u30c8\u9078\u629e";
            case "menu.settings" -> "\u8a2d\u5b9a";
            case "menu.singleplayer" -> "\u30b7\u30f3\u30b0\u30eb\u30d7\u30ec\u30a4";
            case "menu.multiplayer" -> "\u30de\u30eb\u30c1\u30d7\u30ec\u30a4";
            case "menu.quit" -> "\u7d42\u4e86";
            case "menu.footer_line1" -> "\u30ed\u30b0\u30a4\u30f3\u3059\u308b\u3068\u3001\u3059\u3079\u3066\u306e\u653f\u7b56\u306b\u540c\u610f\u3057\u307e\u3059\u3002";
            case "menu.footer_line2" -> "\u30d7\u30e9\u30a4\u30d0\u30b7\u30fc\u30dd\u30ea\u30b7\u30fc\u3068\u5229\u7528\u898f\u7d04\u3092\u542b\u307f\u307e\u3059\u3002";
            case "menu.privacy_policy" -> "\u30d7\u30e9\u30a4\u30d0\u30b7\u30fc\u30dd\u30ea\u30b7\u30fc";
            case "menu.terms_of_service" -> "\u5229\u7528\u898f\u7d04";
            default -> key;
        };
    }

    private static String chinese(String key)
    {
        return switch (key)
        {
            case "language.title" -> "\u9009\u62e9\u8bed\u8a00\u6216\u5730\u533a";
            case "language.subtitle" -> "\u9009\u62e9 celebression \u7684\u663e\u793a\u65b9\u5f0f\u3002";
            case "language.hint" -> "\u5728\u4e0b\u65b9\u641c\u7d22\u6216\u9009\u62e9\u8bed\u8a00\u3002";
            case "language.current_selection" -> "\u5f53\u524d\u9009\u62e9\uff1a%s";
            case "language.search_placeholder" -> "\u641c\u7d22\u8bed\u8a00\u6216\u5730\u533a";
            case "language.empty_region" -> "\u8be5\u5730\u533a\u6682\u65e0\u8bed\u8a00\u3002";
            case "language.no_results_title" -> "\u672a\u627e\u5230\u8bed\u8a00";
            case "language.no_results_subtitle" -> "\u8bf7\u5c1d\u8bd5\u5176\u4ed6\u8bed\u8a00\u3001\u56fd\u5bb6\u6216\u5730\u533a\u3002";
            case "region.africa" -> "\u975e\u6d32";
            case "region.americas" -> "\u7f8e\u6d32";
            case "region.asia" -> "\u4e9a\u6d32";
            case "region.australia_oceania" -> "\u5927\u6d0b\u6d32";
            case "region.europe" -> "\u6b27\u6d32";
            case "region.latin_america" -> "\u62c9\u7f8e";
            case "region.middle_east" -> "\u4e2d\u4e1c";
            case "region.north_america" -> "\u5317\u7f8e";
            case "menu.username_placeholder" -> "\u7528\u6237\u540d";
            case "menu.create_account" -> "\u521b\u5efa\u8d26\u6237";
            case "menu.created_prefix" -> "\u5df2\u521b\u5efa";
            case "menu.selected_account" -> "\u5df2\u9009\u8d26\u6237";
            case "menu.select_account" -> "\u9009\u62e9\u8d26\u6237";
            case "menu.select_account_bottom" -> "\u9009\u62e9\u8d26\u6237";
            case "menu.settings" -> "\u8bbe\u7f6e";
            case "menu.singleplayer" -> "\u5355\u4eba\u6e38\u620f";
            case "menu.multiplayer" -> "\u591a\u4eba\u6e38\u620f";
            case "menu.quit" -> "\u9000\u51fa";
            case "menu.footer_line1" -> "\u767b\u5f55\u5373\u8868\u793a\u540c\u610f\u6240\u6709\u653f\u7b56\uff0c";
            case "menu.footer_line2" -> "\u5305\u62ec\u9690\u79c1\u653f\u7b56\u4e0e\u670d\u52a1\u6761\u6b3e";
            case "menu.privacy_policy" -> "\u9690\u79c1\u653f\u7b56";
            case "menu.terms_of_service" -> "\u670d\u52a1\u6761\u6b3e";
            default -> key;
        };
    }

    private static String korean(String key)
    {
        return switch (key)
        {
            case "language.title" -> "\uc5b8\uc5b4 \ub610\ub294 \uc9c0\uc5ed \uc120\ud0dd";
            case "language.subtitle" -> "celebression \ud45c\uc2dc \ubc29\uc2dd\uc744 \uc120\ud0dd\ud558\uc138\uc694.";
            case "language.hint" -> "\uc544\ub798\uc11c \uc5b8\uc5b4\ub97c \uac80\uc0c9\ud558\uac70\ub098 \uc120\ud0dd\ud558\uc138\uc694.";
            case "language.current_selection" -> "\ud604\uc7ac \uc120\ud0dd: %s";
            case "language.search_placeholder" -> "\uc5b8\uc5b4 \ub610\ub294 \uc9c0\uc5ed \uac80\uc0c9";
            case "language.empty_region" -> "\uc774 \uc9c0\uc5ed\uc5d0\ub294 \uc544\uc9c1 \uc5b8\uc5b4\uac00 \uc5c6\uc2b5\ub2c8\ub2e4.";
            case "language.no_results_title" -> "\uc5b8\uc5b4\ub97c \ucc3e\uc9c0 \ubabb\ud588\uc2b5\ub2c8\ub2e4";
            case "language.no_results_subtitle" -> "\ub2e4\ub978 \uc5b8\uc5b4, \uad6d\uac00 \ub610\ub294 \uc9c0\uc5ed\uc744 \uc2dc\ub3c4\ud574 \ubcf4\uc138\uc694.";
            case "region.africa" -> "\uc544\ud504\ub9ac\uce74";
            case "region.americas" -> "\uc544\uba54\ub9ac\uce74";
            case "region.asia" -> "\uc544\uc2dc\uc544";
            case "region.australia_oceania" -> "\uc624\uc2a4\ud2b8\ub808\uc774\ub9ac\uc544";
            case "region.europe" -> "\uc720\ub7fd";
            case "region.latin_america" -> "\ub77c\ud2f0\uc544\uba54\ub9ac\uce74";
            case "region.middle_east" -> "\uc911\ub3d9";
            case "region.north_america" -> "\ubd81\uc544\uba54\ub9ac\uce74";
            case "menu.username_placeholder" -> "\uc0ac\uc6a9\uc790 \uc774\ub984";
            case "menu.create_account" -> "\uacc4\uc815 \ub9cc\ub4e4\uae30";
            case "menu.created_prefix" -> "\uc0dd\uc131\ub428";
            case "menu.selected_account" -> "\uc120\ud0dd\ub41c \uacc4\uc815";
            case "menu.select_account" -> "\uacc4\uc815 \uc120\ud0dd";
            case "menu.select_account_bottom" -> "\uacc4\uc815 \uc120\ud0dd";
            case "menu.settings" -> "\uc124\uc815";
            case "menu.singleplayer" -> "\uc2f1\uae00 \ud50c\ub808\uc774";
            case "menu.multiplayer" -> "\uba40\ud2f0 \ud50c\ub808\uc774";
            case "menu.quit" -> "\uc885\ub8cc";
            case "menu.footer_line1" -> "\ub85c\uadf8\uc778\ud558\uba74 \ubaa8\ub4e0 \uc815\ucc45\uc5d0 \ub3d9\uc758\ud569\ub2c8\ub2e4,";
            case "menu.footer_line2" -> "\uac1c\uc778\uc815\ubcf4 \ucc98\ub9ac\ubc29\uce68 \ubc0f \uc774\uc6a9\uc57d\uad00 \ud3ec\ud568";
            case "menu.privacy_policy" -> "\uac1c\uc778\uc815\ubcf4 \ucc98\ub9ac\ubc29\uce68";
            case "menu.terms_of_service" -> "\uc774\uc6a9\uc57d\uad00";
            default -> key;
        };
    }

    private static String swahili(String key)
    {
        return switch (key)
        {
            case "language.title" -> "Chagua lugha au eneo";
            case "language.subtitle" -> "Chagua jinsi celebression inapaswa kuonekana kwako.";
            case "language.hint" -> "Tafuta au chagua lugha hapa chini.";
            case "language.current_selection" -> "Chaguo la sasa: %s";
            case "language.search_placeholder" -> "Tafuta lugha au eneo";
            case "language.empty_region" -> "Bado hakuna lugha katika eneo hili.";
            case "language.no_results_title" -> "Hakuna lugha zilizopatikana";
            case "language.no_results_subtitle" -> "Jaribu lugha, nchi au eneo lingine.";
            case "region.africa" -> "Afrika";
            case "region.americas" -> "Amerika";
            case "region.asia" -> "Asia";
            case "region.australia_oceania" -> "Australia/Oceania";
            case "region.europe" -> "Ulaya";
            case "region.latin_america" -> "Amerika ya Kusini";
            case "region.middle_east" -> "Mashariki ya Kati";
            case "region.north_america" -> "Amerika Kaskazini";
            case "menu.username_placeholder" -> "Jina lako la mtumiaji";
            case "menu.create_account" -> "Unda akaunti";
            case "menu.created_prefix" -> "Imeundwa";
            case "menu.selected_account" -> "Akaunti iliyochaguliwa";
            case "menu.select_account" -> "Chagua akaunti";
            case "menu.select_account_bottom" -> "Chagua akaunti";
            case "menu.settings" -> "Mipangilio";
            case "menu.singleplayer" -> "Mchezaji mmoja";
            case "menu.multiplayer" -> "Wachezaji wengi";
            case "menu.quit" -> "Ondoka";
            case "menu.footer_line1" -> "Kwa kuingia unakubali sera zetu zote,";
            case "menu.footer_line2" -> "ikiwa ni pamoja na Sera ya Faragha na Masharti ya Huduma";
            case "menu.privacy_policy" -> "Sera ya Faragha";
            case "menu.terms_of_service" -> "Masharti ya Huduma";
            default -> key;
        };
    }
}
