import { Component, OnInit } from '@angular/core';
import { mockArticles } from 'src/app/components/mockArticles';
import { Article } from 'src/app/interfaces/article.interface';

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.scss']
})
export class ArticlesComponent implements OnInit {
  articles: Article[] = mockArticles;
  constructor() { }

  ngOnInit(): void {
  }

}
