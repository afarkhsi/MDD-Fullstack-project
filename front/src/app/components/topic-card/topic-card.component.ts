import { Component, Input  } from '@angular/core';
import { Topic } from 'src/app/interfaces/topic.interface';

@Component({
  selector: 'app-topic-card',
  templateUrl: './topic-card.component.html',
  styleUrls: ['./topic-card.component.scss']
})
export class TopicCardComponent {
  @Input() topic!: Topic;

  toggleSubscription(): void {
    if (this.topic) {
      this.topic.isSubscribed = !this.topic.isSubscribed;
    }
  }
}