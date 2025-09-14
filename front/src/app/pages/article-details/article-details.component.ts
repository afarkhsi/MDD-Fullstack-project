import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { Article } from 'src/app/interfaces/article.interface';
import { ArticleService } from 'src/app/services/article/article.service';


@Component({
  selector: 'app-article-detail',
  templateUrl: './article-details.component.html',
  styleUrls: ['./article-details.component.scss']
})

export class ArticleDetailsComponent implements OnInit {
  article!: Article;
  isLoading = true;
  errorMessage: string | null = null;

  private destroy$ = new Subject<void>();

  constructor(
    private route: ActivatedRoute,
    private articleService: ArticleService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    console.log('Article ID from route:', id);
    this.articleService.getArticleById(id)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (data) => {
          this.article = data;
          this.isLoading = false;
        },
        error: (err) => {
          console.error('Erreur chargement article', err);
          this.errorMessage = 'Article introuvable.';
          this.isLoading = false;
        }
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
