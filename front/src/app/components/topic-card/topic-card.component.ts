import { Component, EventEmitter, Input, Output  } from '@angular/core';
import { TopicResponse } from 'src/app/interfaces/topicResponse';
import { SubscribeService } from 'src/app/services/subscribe/subscribe.service';

@Component({
  selector: 'app-topic-card',
  templateUrl: './topic-card.component.html',
  styleUrls: ['./topic-card.component.scss']
})
export class TopicCardComponent {
  @Input() topic!: TopicResponse;
  @Input() mode: 'subscribe-only' | 'unsubscribe-only' | 'both' = 'both';
  @Output() unsubscribed = new EventEmitter<number>();

  constructor(private subscribeService: SubscribeService) {}

  toggleSubscription(): void {
    if (!this.topic) return;

    if (this.topic.isSubscribed) {
      if (this.mode === 'subscribe-only') return; 
      this.unsubscribed.emit(this.topic.id);
    } else {
      if (this.mode === 'unsubscribe-only') return;
      this.subscribeService.subscribeTopic(this.topic.id).subscribe({
        next: (res) => {
          this.topic.isSubscribed = res.topic.isSubscribed;
        },
        error: (err) => console.error('Erreur abonnement', err)
      });
    }
  }

}