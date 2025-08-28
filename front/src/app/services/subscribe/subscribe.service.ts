import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Subscribe } from 'src/app/interfaces/subscribe.interface';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {
  private pathService = `${environment.baseUrl}/subscription`;

  constructor(private httpClient: HttpClient) {}

  subscribe(topicId: number): Observable<Subscribe> {
    return this.httpClient.post<Subscribe>(`${this.pathService}/${topicId}/subscribe`, {});
  }

  unsubscribe(topicId: number): Observable<Subscribe> {
    return this.httpClient.post<Subscribe>(`${this.pathService}/${topicId}/unsubscribe`, {});
  }
}