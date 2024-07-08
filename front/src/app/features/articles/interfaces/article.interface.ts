export interface Article {
	id: number,
	author_id: number,
	theme_id: string,
	title: string,
	content: string,
	owner_id: number,
	created_at: Date,
	updated_at: Date
}