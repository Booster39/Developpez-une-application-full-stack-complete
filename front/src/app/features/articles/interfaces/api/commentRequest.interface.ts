export interface CommentRequest {
    author_id: number,
    article_id: number,
    content: string,
    created_at: Date,
}