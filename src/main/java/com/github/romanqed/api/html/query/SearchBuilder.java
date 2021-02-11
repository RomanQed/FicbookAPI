package com.github.romanqed.api.html.query;

import com.github.romanqed.api.AbstractQueryBuilder;
import com.github.romanqed.api.enums.*;
import com.github.romanqed.api.html.entites.Fandom;
import com.github.romanqed.api.html.entites.Pairing;
import com.github.romanqed.api.html.entites.Tag;
import com.github.romanqed.api.html.entites.User;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Pair;
import com.github.romanqed.api.util.ParseUtil;
import com.github.romanqed.api.util.Urls;

import java.util.ArrayList;
import java.util.Date;


public class SearchBuilder extends AbstractQueryBuilder {
    private static final String FANDOMS = "fandom_ids%5B%5D";
    private static final String EXCLUDE_FANDOMS = "fandom_exclude_ids%5B%5D";
    private static final String PAIRINGS = "pairings%5B%i%5D%5Bpairing%5D";
    private static final String EXCLUDE_PAIRINGS = "pairings_exclude%5B%i%5D%5Bpairing%5D";
    private static final String PAIRING_DELIMITER = "---";
    private static final String SIZES = "sizes%5B%5D";
    private static final String RATINGS = "ratings%5B%5D";
    private static final String STATUSES = "statuses%5B%5D";
    private static final String DIRECTIONS = "directions%5B%5D";
    private static final String TAGS = "tags_include%5B%5D";
    private static final String EXCLUDE_TAGS = "tags_exclude%5B%5D";

    private FandomFilter filter;
    private int lastPairingId = 0;
    private int lastExcludePairingId = 0;

    public SearchBuilder() {
        queries = new ArrayList<>();
        queries.add(new Pair<>("find", ""));
    }

    public SearchBuilder fandomFilter(FandomFilter filter) {
        this.filter = filter;
        queries.add(new Pair<>("fandom_filter", filter.value));
        return this;
    }

    public SearchBuilder fandomGroup(FandomGroup group) {
        if (filter == FandomFilter.GROUP) {
            queries.add(new Pair<>("fandom_group_id", Integer.toString(group.getId())));
        }
        return this;
    }

    public SearchBuilder preferredFandoms(boolean denyOther, Fandom... fandoms) {
        if (filter == FandomFilter.SPECIFICS) {
            if (denyOther) {
                queries.add(new Pair<>("deny_other", "1"));
            }
            for (Fandom fandom : fandoms) {
                queries.add(new Pair<>(FANDOMS, Integer.toString(fandom.getId())));
            }
        }
        return this;
    }

    public SearchBuilder avoidedFandoms(Fandom... fandoms) {
        if (filter != FandomFilter.ORIGINALS) {
            for (Fandom fandom : fandoms) {
                queries.add(new Pair<>(EXCLUDE_FANDOMS, Integer.toString(fandom.getId())));
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
        queries.add(new Pair<>("pages_min", Integer.toString(min)));
        queries.add(new Pair<>("pages_max", Integer.toString(max)));
        return this;
    }

    public SearchBuilder ratings(Rating... ratings) {
        for (Rating rating : ratings) {
            queries.add(new Pair<>(RATINGS, Integer.toString(rating.getId())));
        }
        return this;
    }

    public SearchBuilder isTranslate(Boolean isTranslate) {
        String key = "transl";
        if (isTranslate == null) {
            queries.add(new Pair<>(key, "1"));
        } else if (isTranslate) {
            queries.add(new Pair<>(key, "2"));
        } else {
            queries.add(new Pair<>(key, "3"));
        }
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
            queries.add(new Pair<>(TAGS, Integer.toString(tag.getId())));
        }
        return this;
    }

    public SearchBuilder avoidedTags(Tag... tags) {
        for (Tag tag : tags) {
            queries.add(new Pair<>(EXCLUDE_TAGS, Integer.toString(tag.getId())));
        }
        return this;
    }

    public SearchBuilder withAuthor(User user) {
        queries.add(new Pair<>("author", "1"));
        queries.add(new Pair<>("author_id", Integer.toString(user.getId())));
        return this;
    }

    public SearchBuilder likes(int min, int max) {
        Checks.requireCorrectValue(min, i -> i < max);
        queries.add(new Pair<>("likes_min", Integer.toString(min)));
        queries.add(new Pair<>("likes_max", Integer.toString(max)));
        return this;
    }

    public SearchBuilder createDate(Date older, Date newer) {
        Checks.requireCorrectValue(older, date -> date.before(newer));
        queries.add(new Pair<>("dateFilterCreate", "1"));
        queries.add(new Pair<>("date_create_min", ParseUtil.SEARCH_DATE_FORMAT.format(older)));
        queries.add(new Pair<>("date_create_max", ParseUtil.SEARCH_DATE_FORMAT.format(newer)));
        return this;
    }

    public SearchBuilder lastUpdateDate(Date older, Date newer) {
        Checks.requireCorrectValue(older, date -> date.before(newer));
        queries.add(new Pair<>("dateFilterUpdate", "1"));
        queries.add(new Pair<>("date_update_min", ParseUtil.SEARCH_DATE_FORMAT.format(older)));
        queries.add(new Pair<>("date_update_max", ParseUtil.SEARCH_DATE_FORMAT.format(newer)));
        return this;
    }

    public SearchBuilder withWordsInTitle(String words) {
        queries.add(new Pair<>("title", Urls.encodeUrl(words)));
        return this;
    }

    public SearchBuilder sortBy(Sort sort) {
        queries.add(new Pair<>("sort", Integer.toString(sort.value)));
        return this;
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
}
