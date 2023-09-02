package io.zero.medium.domain.article;

import java.util.Optional;

public interface ArticleFindService {
    Optional<Article> getArticleBySlug(String slug);
}
