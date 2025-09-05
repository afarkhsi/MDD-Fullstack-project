import { Component, OnInit } from '@angular/core';
import { Topic } from 'src/app/interfaces/topic.interface';
import { TopicResponse } from 'src/app/interfaces/topicResponse';
import { TopicService } from 'src/app/services/topics/topics.service';


@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss']
})
export class TopicsComponent implements OnInit {
  topics: TopicResponse[] = [];

  constructor(private topicsService: TopicService) {}

  ngOnInit(): void {
    this.topicsService.getAllTopics().subscribe({
      next: (data) => this.topics = data,
      error: (err) => console.error('Erreur lors du chargement des topics', err) 
    });
  }
}