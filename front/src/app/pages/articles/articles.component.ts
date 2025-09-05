import { Component, OnInit } from '@angular/core';
import { ArticleResponse } from 'src/app/interfaces/articleResponse.interface';
import { ArticleService } from 'src/app/services/article/article.service';

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.scss']
})
export class ArticlesComponent implements OnInit {
  articles: ArticleResponse[] = [];
  isLoading = true;
  errorMessage: string | null = null;
  sortOrder: 'asc' | 'desc' = 'desc';

  constructor(private articleService: ArticleService) {}

  ngOnInit(): void {
    this.loadArticles();
  }

  loadArticles(): void {
    this.isLoading = true;
    this.articleService.getArticlesBySubscribedTopics().subscribe({
      next: (data) => {
        this.articles = this.sortArticles(data, this.sortOrder);
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Erreur chargement articles', err);
        this.errorMessage = 'Impossible de charger les articles.';
        this.isLoading = false;
      }
    });
  }

  toggleSortOrder(): void {
    this.sortOrder = this.sortOrder === 'desc' ? 'asc' : 'desc';
    this.articles = this.sortArticles(this.articles, this.sortOrder);
  }

  private sortArticles(articles: ArticleResponse[], order: 'asc' | 'desc'): ArticleResponse[] {
    return [...articles].sort((a, b) => {
      const dateA = new Date(a.createdAt).getTime();
      const dateB = new Date(b.createdAt).getTime();
      return order === 'desc' ? dateB - dateA : dateA - dateB;
    });
  }
}