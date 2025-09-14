import { Component, OnInit } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { TopicResponse } from 'src/app/interfaces/topicResponse';
import { TopicService } from 'src/app/services/topics/topics.service';


@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss']
})
export class TopicsComponent implements OnInit {
  topics: TopicResponse[] = [];

  private destroy$ = new Subject<void>();

  constructor(private topicsService: TopicService) {}

  ngOnInit(): void {
    this.topicsService.getAllTopics()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (data) => this.topics = data,
        error: (err) => console.error('Erreur lors du chargement des topics', err) 
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}