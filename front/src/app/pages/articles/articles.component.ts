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

  constructor(private articleService: ArticleService) {}

  ngOnInit(): void {
    this.articleService.getAllArticles().subscribe({
      next: (data) => {
        this.articles = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Erreur chargement articles', err);
        this.errorMessage = 'Impossible de charger les articles.';
        this.isLoading = false;
      }
    });
  }
}