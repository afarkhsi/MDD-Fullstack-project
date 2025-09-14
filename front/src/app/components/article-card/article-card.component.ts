import { Component, Input } from '@angular/core';
import { ArticleResponse } from 'src/app/interfaces/articleResponse.interface';

@Component({
  selector: 'app-article-card',
  templateUrl: './article-card.component.html',
  styleUrls: ['./article-card.component.scss']
})
export class ArticleCardComponent {
  @Input() article!: ArticleResponse;
  constructor() { }
}
