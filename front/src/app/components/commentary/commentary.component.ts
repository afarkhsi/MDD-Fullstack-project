import { Component, Input, OnInit } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { UserComment } from 'src/app/interfaces/article.interface';
import { ArticleService } from 'src/app/services/article/article.service';

@Component({
  selector: 'app-commentary',
  templateUrl: './commentary.component.html',
  styleUrls: ['./commentary.component.scss']
})
export class CommentaryComponent implements OnInit {
  @Input() articleId!: number;
  comments: UserComment[] = [];
  newComment: string = '';

  private destroy$ = new Subject<void>();

  constructor(private articleService: ArticleService) {}

  ngOnInit(): void {
    this.loadComments();
  }

  loadComments(): void {
    this.articleService.getComments(this.articleId)
    .pipe(takeUntil(this.destroy$))
    .subscribe({
      next: (data) => this.comments = data,
      error: () => console.error('Erreur chargement commentaires')
    });
  }

  sendComment(): void {
    if (this.newComment.trim()) {
      this.articleService.createComment(this.articleId, this.newComment)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (createdComment) => {
          this.comments.push(createdComment);
          this.loadComments();
          this.newComment = '';
        },
        error: () => console.error('Erreur publication commentaire')
      });
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}