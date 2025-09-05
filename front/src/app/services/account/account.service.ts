import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Topic } from 'src/app/interfaces/topic.interface';
import { TopicResponse } from 'src/app/interfaces/topicResponse';

export interface UserAccount {
  username: string;
  email: string;
  password?: string;
  subscribedTopics: TopicResponse[]; 
}

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private baseUrl = `${environment.baseUrl}/user/me`;

  constructor(private http: HttpClient) {}

  getAccount(): Observable<UserAccount> {
    return this.http.get<UserAccount>(this.baseUrl);
  }

  updateAccount(data: UserAccount): Observable<UserAccount> {
    return this.http.put<UserAccount>(this.baseUrl, data);
  }

  getSubscriptions(): Observable<Topic[]> {
    return this.http.get<Topic[]>(`${this.baseUrl}/subscriptions`);
  }

  unsubscribe(topicId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/subscriptions/${topicId}`);
  }
}
