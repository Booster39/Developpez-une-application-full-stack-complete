import { Article } from "../../articles/interfaces/article.interface"

export interface Theme {
	id: number,
	title: string,
	description: string,
	owner_id: number,
	articles: Article[],
	subscribed: boolean
	created_at: Date,
	updated_at: Date
}