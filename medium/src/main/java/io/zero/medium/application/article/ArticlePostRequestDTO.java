package io.zero.medium.application.article;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.zero.medium.domain.article.ArticleContents;
import io.zero.medium.domain.article.ArticleTitle;
import io.zero.medium.domain.article.tag.Tag;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeName("article")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@Value
public class ArticlePostRequestDTO {
    @NotBlank
    String title;
    @NotBlank
    String description;
    @NotBlank
    String body;
    @NotNull
    Set<Tag> tagList;

    ArticleContents toArticleContents() {
        return new ArticleContents(description, ArticleTitle.of(title), body, tagList);
    }
}
