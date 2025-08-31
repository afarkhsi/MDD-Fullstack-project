import { Component, Input  } from '@angular/core';
import { Topic } from 'src/app/interfaces/topic.interface';
import { TopicResponse } from 'src/app/interfaces/topicResponse';
import { SubscribeService } from 'src/app/services/subscribe/subscribe.service';

@Component({
  selector: 'app-topic-card',
  templateUrl: './topic-card.component.html',
  styleUrls: ['./topic-card.component.scss']
})
export class TopicCardComponent {
  @Input() topic!: TopicResponse;

  constructor(private subscribeService: SubscribeService) {}

  toggleSubscription(): void {
    if (!this.topic) return;

    if (this.topic.isSubscribed) {
      // Désabonnement
      this.subscribeService.unsubscribeTopic(this.topic.id).subscribe({
        next: () => {
          this.topic.isSubscribed = false;
        },
        error: (err) => {
          console.error('Erreur désabonnement', err);
        }
      });
    } else {
      // Abonnement
      this.subscribeService.subscribeTopic(this.topic.id).subscribe({
        next: (res) => {
          this.topic.isSubscribed = res.topic.isSubscribed;
        }
      });
    }
  }
}