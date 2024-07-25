export interface Article {
	id: number,
	author_id: number,
	theme_id: number,
	title: string,
	content: string,
	created_at: Date,
	updated_at: Date,
}