import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginRequest } from 'src/app/interfaces/loginRequest.interface';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  isLoading = false;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private auth: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // on crée le form group avec validation requise
    this.loginForm = this.fb.group({
      emailOrUsername:  ['', [Validators.required]],
      password: ['', [Validators.required]],
    });
  }

  onLogin(): void {
    // stop si invalide
    if (this.loginForm.invalid) {
      return;
    }

    this.isLoading = true;
    this.errorMessage = null;

    const payload: LoginRequest = {
      emailOrUsername: this.loginForm.value.emailOrUsername,
      password: this.loginForm.value.password,
    };

    this.auth.login(payload).subscribe({
      next: (res) => {
        // on stocke le JWT dans localStorage
        // on suppose que la réponse contient { token: string, ... }
        localStorage.setItem('jwtToken', res.token);
        console.log('Token stocké :', res.token);
        console.log('Login réussi redirection vers /home', payload);
        // redirige vers la page home
        this.router.navigate(['/home']);
      },
      error: (err) => {
        // affiche un message d’erreur
        this.errorMessage =
          err.error?.message || 'Erreur lors de la connexion.';
        this.isLoading = false;
      },
    });
  }
}