package io.zero.medium.application.article;

import io.zero.medium.domain.article.Article;
import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Value
class MultipleArticleModel {

    List<ArticleModel.ArticleModelNested> articles;
    int articlesCount;

    static MultipleArticleModel fromArticles(Page<Article> articles) {
        final var articlesCollected = articles.map(ArticleModel.ArticleModelNested::fromArticle)
                .stream().collect(toList());
        return new MultipleArticleModel(articlesCollected, articlesCollected.size());
    }
}
