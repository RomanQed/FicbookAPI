package com.github.romanqed.api.html.query;

import com.github.romanqed.api.AbstractQueryBuilder;
import com.github.romanqed.api.enums.*;
import com.github.romanqed.api.html.entities.Fandom;
import com.github.romanqed.api.html.entities.Pairing;
import com.github.romanqed.api.html.entities.Tag;
import com.github.romanqed.api.html.entities.User;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Pair;
import com.github.romanqed.api.util.ParseUtil;
import com.github.romanqed.api.util.Urls;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SearchBuilder extends AbstractQueryBuilder {
    private static final String FANDOMS = "fandom_ids[]";
    private static final String EXCLUDE_FANDOMS = "fandom_exclude_ids[]";
    private static final String PAIRINGS = "pairings[%i][pairing]";
    private static final String EXCLUDE_PAIRINGS = "pairings_exclude[%i][pairing]";
    private static final String PAIRING_DELIMITER = "---";
    private static final String SIZES = "sizes[]";
    private static final String RATINGS = "ratings[]";
    private static final String STATUSES = "statuses[]";
    private static final String DIRECTIONS = "directions[]";
    private static final String TAGS = "tags_include[]";
    private static final String EXCLUDE_TAGS = "tags_exclude[]";

    private FandomFilter filter;
    private int pagesMin;
    private int pagesMax;
    private int likesMin;
    private int likesMax;
    private int fandomGroupId;
    private int translate;
    private int lastPairingId;
    private int lastExcludePairingId;
    private String title;
    private boolean dateCreate;
    private Date dateCreateMin;
    private Date dateCreateMax;
    private boolean dateUpdate;
    private Date dateUpdateMin;
    private Date dateUpdateMax;
    private int sort;
    private int authorId;

    public SearchBuilder() {
        queries = new ArrayList<>();
        filter = FandomFilter.ANY;
        fandomGroupId = 1;
        lastPairingId = 0;
        lastExcludePairingId = 0;
        pagesMin = -1;
        pagesMax = -1;
        likesMin = -1;
        likesMax = -1;
        translate = 1;
        title = "";
        dateCreateMin = new Date();
        dateCreateMax = new Date();
        dateUpdateMin = new Date();
        dateUpdateMax = new Date();
        sort = 1;
        authorId = 0;
    }

    public SearchBuilder fandomFilter(FandomFilter filter) {
        this.filter = filter;
        return this;
    }

    public SearchBuilder fandomGroup(FandomGroup group) {
        if (filter == FandomFilter.GROUP) {
            fandomGroupId = group.getId();
        }
        return this;
    }

    public SearchBuilder preferredFandoms(boolean denyOther, Fandom... fandoms) {
        if (filter == FandomFilter.SPECIFICS) {
            if (denyOther) {
                queries.add(new Pair<>("deny_other", "1"));
            }
            for (Fandom fandom : fandoms) {
                queries.add(new Pair<>(FANDOMS, fandom.getId()));
            }
        }
        return this;
    }

    public SearchBuilder preferredFandoms(Fandom... fandoms) {
        return preferredFandoms(false, fandoms);
    }

    public SearchBuilder avoidedFandoms(Fandom... fandoms) {
        if (filter != FandomFilter.ORIGINALS) {
            for (Fandom fandom : fandoms) {
                queries.add(new Pair<>(EXCLUDE_FANDOMS, fandom.getId()));
            }
        }
        return this;
    }

    public SearchBuilder preferredPairings(boolean inOrder, Pairing... pairings) {
        if (filter == FandomFilter.SPECIFICS) {
            if (inOrder) {
                queries.add(new Pair<>("pairings_order", "1"));
            }
            for (int i = 0; i < pairings.length; ++i) {
                queries.add(new Pair<>(
                        PAIRINGS.replace("%i", Integer.toString(lastPairingId + i)),
                        ParseUtil.pairingToSearchFormat(pairings[i], PAIRING_DELIMITER)
                ));
            }
            lastPairingId = pairings.length - 1;
        }
        return this;
    }

    public SearchBuilder avoidedPairings(boolean inOrder, Pairing... pairings) {
        if (filter == FandomFilter.SPECIFICS) {
            if (inOrder) {
                queries.add(new Pair<>("pairings_exclude_order", "1"));
            }
            for (int i = 0; i < pairings.length; ++i) {
                queries.add(new Pair<>(
                        EXCLUDE_PAIRINGS.replace("%i", Integer.toString(lastExcludePairingId + i)),
                        ParseUtil.pairingToSearchFormat(pairings[i], PAIRING_DELIMITER)
                ));
            }
            lastExcludePairingId = pairings.length - 1;
        }
        return this;
    }

    public SearchBuilder sizes(Size... sizes) {
        for (Size size : sizes) {
            queries.add(new Pair<>(SIZES, Integer.toString(size.getId())));
        }
        return this;
    }

    public SearchBuilder pages(int min, int max) {
        Checks.requireCorrectValue(min, i -> i < max);
        pagesMin = min;
        pagesMax = max;
        return this;
    }

    public SearchBuilder ratings(Rating... ratings) {
        for (Rating rating : ratings) {
            queries.add(new Pair<>(RATINGS, Integer.toString(rating.getId())));
        }
        return this;
    }

    public SearchBuilder isTranslate(Translate translate) {
        this.translate = translate.value;
        return this;
    }

    public SearchBuilder statuses(Status... statuses) {
        for (Status status : statuses) {
            queries.add(new Pair<>(STATUSES, Integer.toString(status.getId())));
        }
        return this;
    }

    public SearchBuilder directions(Direction... directions) {
        for (Direction direction : directions) {
            queries.add(new Pair<>(DIRECTIONS, Integer.toString(direction.getId())));
        }
        return this;
    }

    public SearchBuilder onlyPremium(boolean only) {
        if (only) {
            queries.add(new Pair<>("only_premium", "1"));
        }
        return this;
    }

    public SearchBuilder preferredTags(Tag... tags) {
        for (Tag tag : tags) {
            queries.add(new Pair<>(TAGS, tag.getId()));
        }
        return this;
    }

    public SearchBuilder avoidedTags(Tag... tags) {
        for (Tag tag : tags) {
            queries.add(new Pair<>(EXCLUDE_TAGS, tag.getId()));
        }
        return this;
    }

    public SearchBuilder withAuthor(User user) {
        authorId = ParseUtil.parseInt(user.getId());
        return this;
    }

    public SearchBuilder likes(int min, int max) {
        Checks.requireCorrectValue(min, i -> i < max);
        likesMin = min;
        likesMax = max;
        return this;
    }

    public SearchBuilder createDate(Date older, Date newer) {
        Checks.requireCorrectValue(older, date -> date.before(newer));
        dateCreate = true;
        dateCreateMin = older;
        dateCreateMax = newer;
        return this;
    }

    public SearchBuilder lastUpdateDate(Date older, Date newer) {
        Checks.requireCorrectValue(older, date -> date.before(newer));
        dateUpdate = true;
        dateUpdateMin = older;
        dateUpdateMax = newer;
        return this;
    }

    public SearchBuilder withWordsInTitle(String words) {
        this.title = Urls.encodeUrl(words);
        return this;
    }

    public SearchBuilder sortBy(Sort sort) {
        this.sort = sort.value;
        return this;
    }

    @Override
    public List<Pair<String, String>> build() {
        queries.add(new Pair<>("fandom_filter", filter.value));
        queries.add(new Pair<>("fandom_group_id", Integer.toString(fandomGroupId)));
        queries.add(new Pair<>("pages_min", pagesMin == -1 ? "" : Integer.toString(pagesMin)));
        queries.add(new Pair<>("pages_max", pagesMin == -1 ? "" : Integer.toString(pagesMax)));
        queries.add(new Pair<>("transl", Integer.toString(translate)));
        queries.add(new Pair<>("likes_min", likesMin == -1 ? "" : Integer.toString(likesMin)));
        queries.add(new Pair<>("likes_max", likesMax == -1 ? "" : Integer.toString(likesMax)));
        if (dateCreate) {
            queries.add(new Pair<>("dateFilterCreate", "1"));
        }
        if (dateUpdate) {
            queries.add(new Pair<>("dateFilterUpdate", "1"));
        }
        queries.add(new Pair<>("date_create_min", ParseUtil.SEARCH_DATE_FORMAT.format(dateCreateMin)));
        queries.add(new Pair<>("date_create_max", ParseUtil.SEARCH_DATE_FORMAT.format(dateCreateMax)));
        queries.add(new Pair<>("date_update_min", ParseUtil.SEARCH_DATE_FORMAT.format(dateUpdateMin)));
        queries.add(new Pair<>("date_update_max", ParseUtil.SEARCH_DATE_FORMAT.format(dateUpdateMax)));
        queries.add(new Pair<>("title", title));
        queries.add(new Pair<>("sort", Integer.toString(sort)));
        if (authorId != 0) {
            queries.add(new Pair<>("author", "1"));
            queries.add(new Pair<>("author_id", Integer.toString(authorId)));
        }
        queries.add(new Pair<>("find", "Найти!"));
        return super.build();
    }

    public enum Sort {
        BY_LIKES(1),
        BY_COMMENTS(2),
        BY_DATE(3),
        BY_PAGES(4),
        RANDOM_20(5);

        final int value;

        Sort(int value) {
            this.value = value;
        }
    }

    public enum FandomFilter {
        ANY("any"),
        ORIGINALS("originals"),
        GROUP("group"),
        SPECIFICS("fandom");

        final String value;

        FandomFilter(String value) {
            this.value = value;
        }
    }

    public enum Translate {
        ANY(1),
        TRANSLATION(2),
        ORIGINAL(3);

        final int value;

        Translate(int value) {
            this.value = value;
        }
    }
}
