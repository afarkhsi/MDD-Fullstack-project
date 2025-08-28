export interface ArticleResponse {
  id: number;
  title: string;
  description: string;
  createdAt: string;
  updatedAt: string;
  authorUsername: {
    id: number;
    name: string;
    email: string;
  };
  topic: {
    id: number;
    name: string;
  };
}
