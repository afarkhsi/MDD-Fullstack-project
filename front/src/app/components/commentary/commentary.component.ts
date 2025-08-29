import { Component, Input, OnInit } from '@angular/core';
import { UserComment } from 'src/app/interfaces/article.interface';
import { User } from 'src/app/interfaces/user.interface';
import { ArticleService } from 'src/app/services/article/article.service';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-commentary',
  templateUrl: './commentary.component.html',
  styleUrls: ['./commentary.component.scss']
})
export class CommentaryComponent implements OnInit {
  @Input() articleId!: number;
  comments: UserComment[] = [];
  newComment: string = '';

  constructor(private articleService: ArticleService) {}

  ngOnInit(): void {
    this.loadComments();
  }

  loadComments(): void {
    this.articleService.getComments(this.articleId).subscribe({
      next: (data) => this.comments = data,
      error: () => console.error('Erreur chargement commentaires')
    });
  }

sendComment(): void {
  if (this.newComment.trim()) {
    this.articleService.createComment(this.articleId, this.newComment).subscribe({
      next: (createdComment) => {
        this.comments.push(createdComment);
        this.loadComments();
        this.newComment = '';
      },
      error: () => console.error('Erreur publication commentaire')
    });
  }
}

}