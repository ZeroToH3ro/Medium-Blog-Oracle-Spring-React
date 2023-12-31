package io.zero.medium.domain.article;

import io.zero.medium.domain.article.comment.Comment;
import io.zero.medium.domain.user.User;
import javax.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.Instant;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;


import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Table(name = "articles")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article {
    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = EAGER)
    private User author;

    @Embedded
    private ArticleContents contents;

    @Column(name = "created_at")
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Instant updatedAt;

    @JoinTable(name = "article_favorites",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false))
    @ManyToMany(fetch = EAGER, cascade = PERSIST)
    private Set<User> userFavorited = new HashSet<>();

    @OneToMany(mappedBy = "article", cascade = {PERSIST, REMOVE})
    private Set<Comment> comments = new HashSet<>();

    @Transient
    private boolean favorited = false;

    public Article(User author, ArticleContents contents) {
        this.author = author;
        this.contents = contents;
    }

    protected Article() {
    }

    public Article afterUserFavoritesArticle(User user) {
        userFavorited.add(user);
        return updateFavoriteByUser(user);
    }

    public Article afterUserUnFavoritesArticle(User user) {
        userFavorited.remove(user);
        return updateFavoriteByUser(user);
    }

    public Comment addComment(User author, String body) {
        final var commentToAdd = new Comment(this, author, body);
        comments.add(commentToAdd);
        return commentToAdd;
    }

    public void removeCommentByUser(User user, long commentId) {
        final var commentsToDelete = comments.stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
        if (!user.equals(author) || !user.equals(commentsToDelete.getAuthor())) {
            throw new IllegalAccessError("Not authorized to delete comment");
        }

        try {
            System.out.println("ID COMMENT DELETE:" + commentsToDelete.getId());
            comments.remove(commentsToDelete);
            System.out.println("Delete successful");
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }

    public void updateArticle(ArticleUpdateRequest updateRequest) {
        contents.updateArticleContentsIfPresent(updateRequest);
    }

    public Article updateFavoriteByUser(User user) {
        favorited = userFavorited.contains(user);
        return this;
    }

    public User getAuthor() {
        return author;
    }

    public ArticleContents getContents() {
        return contents;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public int getFavoritedCount() {
        return userFavorited.size();
    }

    public boolean isFavorited() {
        return favorited;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var article = (Article) o;
        return author.equals(article.author) && contents.getTitle().equals(article.contents.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, contents.getTitle());
    }
}

