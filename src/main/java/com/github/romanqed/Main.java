package com.github.romanqed;

import com.github.romanqed.api.html.entites.Pairing;
import com.github.romanqed.api.util.ParseUtil;

public class Main {
    public static void main(String[] args) {
//        System.out.println(Urls.decodeUrl("pairings_exclude%5B0%5D%5Bpairing%5D=%D0%90%D0%B1%D1%80%D0%B0%D0%BA%D1%81%D0%B0%D1%81---%D0%B0%D0%BB%D1%8C%D1%82%21%D0%9A%D0%B0%D0%BB%D0%BB%D0%B8%D0%BE%D0%BF%D0%B0\n" +
//                "pairings_exclude%5B1%5D%5Bpairing%5D=%D0%90%D1%80%D0%B0%D0%B4%D0%B8%D1%8F%D0%B1%D0%BE%D1%82"));
        Pairing t = Pairing.fromCharacters("ОМП", "ОЖП", "dark!Агата Хармон");
        System.out.println(ParseUtil.pairingToSearchFormat(t, "---"));
    }
}