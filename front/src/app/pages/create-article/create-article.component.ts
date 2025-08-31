import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Topic } from 'src/app/interfaces/topic.interface';
import { ArticleService } from 'src/app/services/article/article.service';
import { TopicService } from 'src/app/services/topics/topics.service';

@Component({
  selector: 'app-create-article',
  templateUrl: './create-article.component.html',
  styleUrls: ['./create-article.component.scss']
})
export class CreateArticleComponent implements OnInit {
  articleForm!: FormGroup;
  topics: Topic[] = [];

  constructor(
    private fb: FormBuilder,
    private articleService: ArticleService,
    private topicService: TopicService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.articleForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      topicId: [null, Validators.required]
    });

    this.topicService.getAllTopics().subscribe(data => this.topics = data);
  }

  onSubmit(): void {
    if (this.articleForm.invalid) return;

    this.articleService.createArticle(this.articleForm.value).subscribe({
      next: () => {
        console.log('Article créé');
        // Naviguer seulement après succès
        this.router.navigate(['/articles']);
      },
      error: err => console.error('Erreur création', err)
    });
  }
}

