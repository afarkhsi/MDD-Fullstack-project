import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Topic } from 'src/app/interfaces/topic.interface';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TopicService {
  private topicUrl = `${environment.baseUrl}/topics`;

  constructor(private httpClient: HttpClient) {}

  getAllTopics(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(`${this.topicUrl}`);
  }
}