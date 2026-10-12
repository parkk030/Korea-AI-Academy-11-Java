package com.calc.ui;

import java.awt.Color;

/**
 * 다크/라이트 테마 컬러 팔레트를 정의하는 Java 21 record
 */
public record ThemePalette(
        String name,
        boolean isDark,
        Color windowBg,
        Color displayBg,
        Color panelBg,
        Color borderColor,
        Color primaryText,
        Color secondaryText,
        Color errorText,
        Color numberBtnBg,
        Color numberBtnHover,
        Color numberBtnText,
        Color funcBtnBg,
        Color funcBtnHover,
        Color funcBtnText,
        Color opBtnBg,
        Color opBtnHover,
        Color opBtnText,
        Color accentBtnBg,
        Color accentBtnHover,
        Color accentBtnText,
        Color badgeBg,
        Color badgeText
) {
    /**
     * 모던 다크 테마 (기본)
     */
    public static final ThemePalette DARK = new ThemePalette(
            "Dark",
            true,
            new Color(22, 24, 30),
            new Color(32, 35, 44),
            new Color(28, 31, 39),
            new Color(52, 57, 70),
            new Color(245, 247, 250),
            new Color(148, 158, 178),
            new Color(255, 107, 107),
            new Color(45, 49, 61),
            new Color(58, 63, 78),
            new Color(245, 247, 250),
            new Color(36, 40, 50),
            new Color(48, 53, 66),
            new Color(200, 208, 224),
            new Color(55, 65, 92),
            new Color(72, 85, 118),
            new Color(130, 177, 255),
            new Color(76, 110, 245),
            new Color(92, 124, 250),
            Color.WHITE,
            new Color(59, 91, 219),
            Color.WHITE
    );

    /**
     * 클린 라이트 테마
     */
    public static final ThemePalette LIGHT = new ThemePalette(
            "Light",
            false,
            new Color(241, 244, 249),
            new Color(255, 255, 255),
            new Color(248, 250, 252),
            new Color(218, 224, 235),
            new Color(26, 32, 44),
            new Color(100, 116, 139),
            new Color(220, 38, 38),
            new Color(255, 255, 255),
            new Color(238, 242, 248),
            new Color(26, 32, 44),
            new Color(228, 234, 244),
            new Color(214, 222, 235),
            new Color(51, 65, 85),
            new Color(219, 234, 254),
            new Color(191, 219, 254),
            new Color(29, 78, 216),
            new Color(59, 130, 246),
            new Color(37, 99, 235),
            Color.WHITE,
            new Color(37, 99, 235),
            Color.WHITE
    );
}
