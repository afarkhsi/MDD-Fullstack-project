import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subject, takeUntil } from 'rxjs';
import { Topic } from 'src/app/interfaces/topic.interface';
import { AccountService } from 'src/app/services/account/account.service';
import { SubscribeService } from 'src/app/services/subscribe/subscribe.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss']
})
export class AccountComponent implements OnInit {
  accountForm!: FormGroup;
  subscriptions: Topic[] = [];
  isLoading = true;
  message: string | null = null;

  private destroy$ = new Subject<void>();

  constructor(
    private fb: FormBuilder,
    private accountService: AccountService,
    private subscribeService: SubscribeService
  ) {}

  ngOnInit(): void {
    this.loadAccount();
    this.loadSubscriptions();
  }

 loadAccount(): void {
  this.accountService.getAccount()
    .pipe(takeUntil(this.destroy$))
    .subscribe({
      next: (user) => {
        this.accountForm = this.fb.group({
          username: [user.username, Validators.required],
          email: [user.email, [Validators.required, Validators.email]],
          password: ['']
        });
        this.subscriptions = user.subscribedTopics || [];
        this.isLoading = false;
      },
      error: () => {
        this.message = 'Erreur lors du chargement du compte';
        this.isLoading = false;
      }
    }
  );
}

  loadSubscriptions(): void {
    this.subscribeService.getUserSubscriptions()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (topics) => this.subscriptions = topics
      });
  }

  onSave(): void {
    if (this.accountForm.invalid) return;

    this.accountService.updateAccount(this.accountForm.value)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => this.message = 'Compte mis à jour avec succès',
        error: () => this.message = 'Erreur lors de la mise à jour du compte'
      });
  }

  onUnsubscribe(topicId: number): void {
    console.log('unsubscribe', topicId)
    this.subscribeService.unsubscribeTopic(topicId)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => {
          this.subscriptions = this.subscriptions.filter(t => t.id !== topicId);
        },
        error: () => this.message = 'Erreur lors du désabonnement'
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
