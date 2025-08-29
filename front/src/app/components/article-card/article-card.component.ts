import { Component, OnInit, Input } from '@angular/core';
import { ArticleResponse } from 'src/app/interfaces/articleResponse.interface';

@Component({
  selector: 'app-article-card',
  templateUrl: './article-card.component.html',
  styleUrls: ['./article-card.component.scss']
})
export class ArticleCardComponent implements OnInit {
  @Input() article!: ArticleResponse;
  constructor() { }

  ngOnInit(): void {
  }
}
