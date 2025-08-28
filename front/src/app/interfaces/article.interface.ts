import { Topic } from "./topic.interface";
import { User } from "./user.interface";

export interface Article {
  id: number;
  title: string;
  description?: string; 
  authorUsername?: User;
  comments?: UserComment[];
  topic?: Topic;
  topic_id?: number;
  createdAt?: Date;
  updatedAt?: Date;
}

export type UserComment = {
  username: User;
  comment: string;
};
