import { Component, Input  } from '@angular/core';
import { Topic } from 'src/app/interfaces/topic.interface';
import { TopicResponse } from 'src/app/interfaces/topicResponse';

@Component({
  selector: 'app-topic-card',
  templateUrl: './topic-card.component.html',
  styleUrls: ['./topic-card.component.scss']
})
export class TopicCardComponent {
  @Input() topic!: TopicResponse;

  toggleSubscription(): void {
    if (this.topic) {
      console.log(this.topic);
      this.topic.isSubscribed = !this.topic.isSubscribed;
    }
  }
}