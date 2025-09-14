import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { RegisterRequest } from 'src/app/interfaces/registerRequest.interface';
import { SessionInformation } from 'src/app/interfaces/sessionInfo.interface';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';
import { User } from 'src/app/interfaces/user.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private authUrl = `${environment.baseUrl}/auth`;
  private authStatus = new BehaviorSubject<boolean>(this.hasToken());

  authStatus$ = this.authStatus.asObservable();

  constructor(
    private httpClient: HttpClient,
    private router: Router
  ) {}

  private hasToken(): boolean {
    return !!localStorage.getItem('token');
  }

  register(registerRequest: RegisterRequest): Observable<SessionInformation> {
    return this.httpClient.post<SessionInformation>(`${this.authUrl}/register`, registerRequest);
  }

  login(loginRequest: { emailOrUsername: string; password: string }): Observable<SessionInformation> {
    return this.httpClient.post<SessionInformation>(`${this.authUrl}/login`, loginRequest).pipe(
      tap(response => {
        localStorage.setItem('token', response.token);
        this.authStatus.next(true); 
      })
    );
  }

  logout(): void {
    localStorage.removeItem('token');
    this.authStatus.next(false);
    this.router.navigate(['/home']);
  }
}