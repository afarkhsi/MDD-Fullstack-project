import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Subscribe } from 'src/app/interfaces/subscribe.interface';
import { environment } from 'src/environments/environment';
import { Topic } from 'src/app/interfaces/topic.interface';

@Injectable({
  providedIn: 'root'
})
export class SubscribeService {
  private pathService = `${environment.baseUrl}/subscription`;

  constructor(private httpClient: HttpClient) {}

  subscribeTopic(topicId: number): Observable<Subscribe> {
    return this.httpClient.post<Subscribe>(`${this.pathService}/${topicId}/subscribe`, {});
  }

  unsubscribeTopic(topicId: number): Observable<Subscribe> {
    return this.httpClient.post<Subscribe>(`${this.pathService}/${topicId}/unsubscribe`, {});
  }

  getUserSubscriptions(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(`${this.pathService}/user-subscriptions`);
  }
}