package io.zero.medium.application.article;

import io.zero.medium.application.user.ProfileModel;
import io.zero.medium.domain.article.Article;
import io.zero.medium.domain.article.tag.Tag;
import lombok.Value;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

import static java.util.stream.Collectors.toSet;


@Value
class ArticleModel {

    ArticleModelNested article;

    static ArticleModel fromArticle(Article article) {
        return new ArticleModel(ArticleModelNested.fromArticle(article));
    }

    @Value
    static class ArticleModelNested {
        String slug;
        String title;
        String description;
        String body;
        Set<String> tagList;
        ZonedDateTime createdAt;
        ZonedDateTime updatedAt;
        boolean favorited;
        int favoritesCount;
        ProfileModel.ProfileModelNested author;

        static ArticleModelNested fromArticle(Article article) {
            final var contents = article.getContents();
            final var titleFromArticle = contents.getTitle();
            return new ArticleModelNested(
                    titleFromArticle.getSlug(), titleFromArticle.getTitle(),
                    contents.getDescription(), contents.getBody(),
                    contents.getTags().stream().map(Tag::toString).collect(toSet()),
                    article.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")),
                    article.getUpdatedAt().atZone(ZoneId.of("Asia/Seoul")),
                    article.isFavorited(), article.getFavoritedCount(),
                    ProfileModel.ProfileModelNested.fromProfile(article.getAuthor().getProfile())
            );
        }
    }
}

