import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ArticleRequest } from 'src/app/interfaces/articleRequest.interface';
import { ArticleResponse } from 'src/app/interfaces/articleResponse.interface';
import { environment } from 'src/environments/environment';
import { Article, UserComment } from 'src/app/interfaces/article.interface';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {
  private articleUrl = `${environment.baseUrl}/articles`;

  constructor(private httpClient: HttpClient) {}

  createArticle(article: ArticleRequest): Observable<any> {
    return this.httpClient.post(`${this.articleUrl}`, article);
  }

  getAllArticles(): Observable<ArticleResponse[]> {
    return this.httpClient.get<ArticleResponse[]>(`${this.articleUrl}`);
  }

  getArticleById(id: number): Observable<Article> {
  return this.httpClient.get<Article>(`${this.articleUrl}/${id}`);  
  }

  createComment(articleId: number, comment: string): Observable<UserComment> {
    return this.httpClient.post<UserComment>(`${this.articleUrl}/comment`, {
      articleId,
      comment
    });
  }

  getComments(articleId: number): Observable<UserComment[]> {
    return this.httpClient.get<UserComment[]>(`${this.articleUrl}/${articleId}/comments`);
  }
}
