import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RegisterRequest } from 'src/app/interfaces/registerRequest.interface';
import { SessionInformation } from 'src/app/interfaces/sessionInfo.interface';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private authUrl = environment.baseUrl;

  constructor(private httpClient: HttpClient) {}

  register(registerRequest: RegisterRequest): Observable<SessionInformation> {
    return this.httpClient.post<SessionInformation>(`${this.authUrl}/auth/register`, registerRequest);
  }

  login(loginRequest: { emailOrUsername: string; password: string }): Observable<SessionInformation> {
    return this.httpClient.post<SessionInformation>(`${this.authUrl}/auth/login`, loginRequest);
  }
}