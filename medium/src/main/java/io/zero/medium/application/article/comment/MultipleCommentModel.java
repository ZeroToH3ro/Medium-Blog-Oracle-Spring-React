package io.zero.medium.application.article.comment;

import io.zero.medium.domain.article.comment.Comment;
import lombok.Value;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Value
class MultipleCommentModel {

    List<CommentModel.CommentModelNested> comments;

    static MultipleCommentModel fromComments(Set<Comment> comments) {
        final var commentsCollected = comments.stream().map(CommentModel.CommentModelNested::fromComment)
                .collect(toList());
        return new MultipleCommentModel(commentsCollected);
    }
}

