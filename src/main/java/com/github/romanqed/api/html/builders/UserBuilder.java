package com.github.romanqed.api.html.builders;

import com.github.romanqed.api.html.entities.BetaForm;
import com.github.romanqed.api.html.entities.User;
import com.github.romanqed.api.interfaces.HtmlPageBuilder;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.ParseUtil;
import com.github.romanqed.api.util.Queries;
import com.github.romanqed.api.util.Urls;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;

public class UserBuilder implements HtmlPageBuilder<User> {
    @Override
    public User build(URL url, Document page) {
        User ret = new User(Urls.sliceUrlLastPath(url));
        ret.name = page.selectFirst("div.author-hat h1").text();
        ret.avatar = Urls.parseUrl(page.selectFirst("img[alt=" + ret.name + "]").attr("src"));
        ret.about = Checks.requireNonExcept(
                () -> page.selectFirst("article.profile-container div.urlize").wholeText(),
                ""
        );
        ret.favourites = Checks.requireNonExcept(
                () -> ParseUtil.checkedMixedNum(page.selectFirst("span.add-favourite-author-text a").text()),
                0
        );
        ret.bankDetails = Checks.requireNonExcept(
                () -> page.selectFirst("section.mb-30:contains(Поддержать) div.urlize").text(),
                ""
        );
        ret.lastOnline = ParseUtil.parseNativeDate(Checks.requireNonExcept(
                () -> page.selectFirst("section.mb-30 p span").attr("title"),
                ""
        ));
        Element betaForm = page.selectFirst(Queries.BETA_FORM_QUERY);
        if (betaForm != null) {
            ret.betaForm = BetaForm.BUILDER.build(betaForm);
        }
        return ret;
    }

    @Override
    public User build(Element node) {
        User ret = new User(ParseUtil.sliceLastPath(node.attr("href")));
        ret.name = node.text();
        return ret;
    }
}
